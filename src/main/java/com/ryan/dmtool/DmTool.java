package com.ryan.dmtool;

import com.ryan.dmtool.document.service.DocumentService;

import java.sql.SQLException;

public class DmTool {
    public static void main(String[] args){
        DocumentService service = new DocumentService();
        service.fix();
    }
}
