package com.mobileai.luncert.model.core;

import java.io.Serializable;

import com.mobileai.luncert.model.normal.NormalEvent;

public class SecurityEvent extends NormalEvent implements Serializable {

    private static final long serialVersionUID = -3130668957997446147L;

    private int id;

	// 源ip
    private int source;

    // 目的ip
    private int target;

    public SecurityEvent(int id, int source, int target, String name, int eventId, String eventType) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.name = name;
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public void setId(int id) { this.id = id; }
    
    public void setSource(int source) { this.source = source; }

    public void setTarget(int target) { this.target = target; }

    public int getId() { return id; }

    public int getSource() { return source; }

    public int getTarget() { return target; }

}