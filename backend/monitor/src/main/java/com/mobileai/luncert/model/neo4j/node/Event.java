package com.mobileai.luncert.model.neo4j.node;

import org.neo4j.driver.v1.Value;

public class Event {

    protected int eventId;

    protected String eventType;

    private String name;

	private boolean beScene;

    public Event(Value value) {
        eventId = value.get("eventId", 0);
        name = value.get("name", "");
		eventType = value.get("eventType", "");
		beScene = value.get("beScene", false);
	}
	
	public boolean beScene() { return beScene; }
	
	public String toString() { return "[" + eventId + "]" + name + " : " + eventType; }

    // setter & getter

	public void setName(String name) { this.name = name; }

	public String getName() { return name; }

	public void setBeScene(boolean beScene) { this.beScene = beScene; }

	public boolean getBeScene() { return beScene; }

	public void setEventId(int eventId) { this.eventId = eventId; }

	public int getEventId() { return eventId; }

	public void setType(String eventType) { this.eventType = eventType; }

    public String getType() { return eventType; }
    
}