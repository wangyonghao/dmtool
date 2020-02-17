package com.ryan.dmtool.document.dao;

import com.ryan.dmtool.common.Config;
import com.ryan.dmtool.common.DBUtil;
import com.ryan.dmtool.document.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentDao {

    public List<Document> findFixDocuments() {
        Connection connection = DBUtil.getConnection();
        QueryRunner qr = new QueryRunner();
        BeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        try {
            return qr.query(connection, Config.cfg.getString("findDocumentSQL"), new BeanListHandler<Document>(Document.class, processor));
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    private void saveDocument(Connection connection, Document document) {
        QueryRunner qr = new QueryRunner();
        try {
            qr.update(connection,"update DM_DOCUMENT t set t.RECORD_SIZE=? where t.SYS_GUID =?",document.getRecordSize(),document.getSysGuid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveDocumentCheck(Connection connection, Document document) {
        QueryRunner qr = new QueryRunner();
        try {
            BigDecimal count = qr.query(connection,"select count(*) from DM_DOCUMENT_CHECK t where t.DOCUMENT_SYSGUID='"+document.getSysGuid()+"'", new ScalarHandler<BigDecimal>());
            if(count.compareTo(BigDecimal.ZERO)>0) {
                qr.update(connection, "update DM_DOCUMENT_CHECK t set t.IS_EXISTS=? where t.DOCUMENT_SYSGUID =?", document.getIsExists(), document.getSysGuid());
            }else{
                qr.update(connection,"insert into DM_DOCUMENT_CHECK(DOCUMENT_SYSGUID, IS_EXISTS) values(?,?)",document.getSysGuid(),document.getIsExists());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDocuments(List<Document> docs) {
        Connection connection = DBUtil.getConnection();
        try {
            for (Document document : docs) {
                this.saveDocument(connection, document);
                this.saveDocumentCheck(connection, document);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
