package com.ryan.dmtool.document.service;

import com.google.common.collect.Lists;
import com.ryan.dmtool.common.ConnectionUtil;
import com.ryan.dmtool.document.entity.Document;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DocumentService {

    public List<Document> findDocuments(){
        Connection connection = ConnectionUtil.getConnection();
        QueryRunner qr = new QueryRunner();
        BeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);

        try {
           return qr.query(connection,"select * from dm_document t where t.record_status=1 and t.record_size=0",new BeanListHandler<Document>(Document.class,processor));
        } catch (SQLException e) {
            return Lists.newArrayList();
        }
    }
}
