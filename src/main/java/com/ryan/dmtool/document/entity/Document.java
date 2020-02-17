package com.ryan.dmtool.document.entity;

import lombok.Data;

@Data
public class Document {
    private String sysCreateDate;
    private String sysCreateUser;
    private String sysUpdateDate;
    private String sysUpdateUser;
    private String physicalPath;
    private String recordSecurity;
    private String recordType;
    private String recordSize;
    private String recordSubject;
    private String recordVersion;
    private String recordIshistory;
    private String recordIcon;
    private String recordStatus;
    private String sysRecycle;
    private String description;
    private String unitId;
    private String sysGuid;
    private String documentId;
    private String documentOrder;
    private String checkstatus;
    private String checkuser;
    private String recordGuid;
    private String documentTag;
    private Integer isExists; // 1 exists
}
