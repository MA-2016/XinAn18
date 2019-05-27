package com.mobileai.luncert.model.mysql.entity;

import java.util.Date;

import com.mobileai.luncert.utils.IPUtil;

/**
 * mysql Event 实体类
 */
public class Event {

    private int id;

    private Date time;

    private int source;

    private int target;

    private String srcPos;

    private String tarPos;

    private int eventId;

    private String eventType;

    private boolean beScene;

    public Event() {}

    public Event(Date time, int source, int target, int eventId, boolean beScene) {
        this.time = time;
        this.source = source;
        this.target = target;
        this.eventId = eventId;
        this.beScene = beScene;
    }

    public String getSourceString() { return IPUtil.ipToString(source); }

    public String getTargetString() { return IPUtil.ipToString(target); }

    public String toString() {
        return new StringBuilder()
                .append("\n\ttime: ").append(time)
                .append("\n\tsource: ").append(source)
                .append("\n\ttarget: ").append(target)
                .append("\n\tsource position: ").append(srcPos)
                .append("\n\ttarget position: ").append(tarPos)
                .append("\n\tevent id: ").append(eventId)
                .append("\n\tevent type: ").append(eventType)
                .toString();
    }
    // setter & getter

    public void setId(int id) { this.id = id; }

    public void setTime(Date time) { this.time = time; }

    public void setSource(int source) { this.source = source; }

    public void setTarget(int target) { this.target = target; }

    public void setSrcPos(String srcPos) { this.srcPos = srcPos; }

    public void setTarPos(String tarPos) { this.tarPos = tarPos; }
    
    public void setEventId(int eventId) { this.eventId = eventId; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public void setScene(boolean beScene) { this.beScene = beScene; }

    public int getId() { return id; }

    public Date getTime() { return time; }

    public int getSource() { return source; }

    public int getTarget() { return target; }

    public String getSrcPos() { return srcPos; }

    public String getTarPos() { return tarPos; }

    public int getEventId() { return eventId; }

    public String getEventType() { return eventType; }

    public boolean getBeScene() { return beScene; }
    
}