package com.ryan.dmtool.document.service;

import com.ryan.dmtool.common.Config;
import com.ryan.dmtool.document.dao.DocumentDao;
import com.ryan.dmtool.document.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DocumentService {
    private DocumentDao dao = new DocumentDao();

    public void fix() {
        Long start = System.currentTimeMillis();
        List<Document> docs = dao.findFixDocuments();

        log.info("Found " + docs.size() + " documents.");
        int skipCount = 0;
        int moveCount = 0;
        List<String> notExists = new ArrayList<>();

        String BASE_PATH = Config.cfg.getString("basePath");
        for (Document document : docs) {
            String path = this.getPath(document.getPhysicalPath());
            File dest = new File(path);
            if (dest.exists()) {
                document.setRecordSize(dest.length()+"");
                document.setIsExists(1);
                skipCount++;
                continue;
            }

            String filename = StringUtils.substringAfterLast(path, "/");
            String currentPath = this.searchFileByName(filename, BASE_PATH);
            if (StringUtils.isNotEmpty(currentPath)) {
                File src = new File(currentPath);
                try {
                    FileUtils.moveFile(src, dest);
                    log.info(src.getAbsolutePath() + " -> " + dest.getAbsolutePath() + ", size="+dest.length());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                document.setRecordSize(src.length() + "");
                document.setIsExists(1);
                moveCount++;
            } else {
                document.setIsExists(0);
                document.setRecordSize("0");
                notExists.add(filename);
            }
        }
        log.info("Sync to database.");
        dao.saveDocuments(docs);
        Long end = System.currentTimeMillis();
        log.info("Process " + docs.size() + " documents in " + (end - start) + "ms . ");
        log.info("Not exists file list: ");
        for (String fn : notExists){
            log.info("  "+fn);
        }
        log.info("Summary: " + moveCount + " moved, " + skipCount + " skipped, " + notExists.size() + " not exsits.");

    }

    private String getPath(String physicalPath) {
        String key = StringUtils.substringBefore(physicalPath, "/");
        String suffix = StringUtils.substringAfter(physicalPath, "/");
        return Config.cfg.getString("link." + key) +"/" + suffix;
    }

    private String searchFileByName(String filename, String basePath) {
        String path = "";
        try {
            String[] cmd = {"/bin/sh", "-c", "find " + basePath + " -iname " + filename};
            Runtime run = Runtime.getRuntime();
            Process p = run.exec(cmd);
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null) {
                path = line;
            }
        } catch (IOException e) {
            return null;
        }
        return path;
    }

}
