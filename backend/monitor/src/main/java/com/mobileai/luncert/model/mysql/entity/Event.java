package com.mobileai.luncert.model.mysql.entity;

import com.mobileai.luncert.utils.IPUtil;

import lombok.Data;

/**
 * mysql Event 实体类
 */
@Data
public class Event {

    private int id;

    private long timestamp;

    private int source;

    private int target;

    private String srcPos;

    private String tarPos;

    private String name;

    private int eventId;

    private String eventType;

    private boolean beScene;

    private boolean attackSuccess;

    private String attackType;

    public String getSourceString() { return IPUtil.ipToString(source); }

    public String getTargetString() { return IPUtil.ipToString(target); }
   
}