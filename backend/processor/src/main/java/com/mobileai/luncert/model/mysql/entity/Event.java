package com.mobileai.luncert.model.mysql.entity;

import java.util.Date;

import com.mobileai.luncert.utils.Convert;
import com.mobileai.luncert.utils.IPUtil;

import net.sf.json.JSONObject;

/**
 * mysql entity
 */

public class Event {

    private int id;

    private Date time;

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

    public Event() {}

    public Event(Date date, int source, int target, String srcPos, String tarPos, String name, int eventId, String eventType, boolean beScene, boolean attackSuccess, String attackType) {
        this.time = date;
        this.source = source;
        this.target = target;
        this.srcPos = srcPos;
        this.tarPos = tarPos;
        this.name = name;
        this.eventId = eventId;
        this.eventType = eventType;
        this.beScene = beScene;
        this.attackSuccess = attackSuccess;
        this.attackType = attackType;
    }

    public String toJSONString() {
        JSONObject ret = new JSONObject();
        ret.put("time", Convert.dateCastString(time));
        ret.put("source", source);
        ret.put("target", target);
        ret.put("srcPos", srcPos);
        ret.put("tarPos", tarPos);
        ret.put("name", name);
        ret.put("eventId", eventId);
        ret.put("eventType", eventType);
        ret.put("beScene", beScene);
        ret.put("attackSuccess", attackSuccess);
        ret.put("attackType", attackType);
        return ret.toString();
    }

    // setter & getter

    public void setId(int id) { this.id = id; }

    public void setTime(Date time) { this.time = time; }

    public void setSource(int source) { this.source = source; }

    public void setTarget(int target) { this.target = target; }

    public void setName(String name) { this.name = name; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public void setSrcPos(String srcPos) { this.srcPos = srcPos; }

    public void setTarPos(String tarPos) { this.tarPos = tarPos; }

    public void setBeScene(boolean beScene) { this.beScene = beScene; }

    public void setAttackSuccess(boolean attackSuccess) { this.attackSuccess = attackSuccess; }

    public void setAttackType(String attckType) { this.attackType = attckType; }

    public int getId() { return id; }

    public Date getTime() { return time; }

    public int getSource() { return source; }

    public String getSourceString() { return IPUtil.ipToString(source); }

    public int getTarget() { return target; }

    public String getTargetString() { return IPUtil.ipToString(target); }

    public String getName() { return name; }

    public int getEventId() { return eventId; }

    public String getEventType() { return eventType; }

    public String getSrcPos() { return srcPos; }

    public String getTarPos() { return tarPos; }

    public boolean getBeScene() { return beScene; }

    public boolean getAttackSuccess() { return attackSuccess; }

    public String getAttackType() { return attackType; }
    
}