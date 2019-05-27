package com.mobileai.luncert.model.neo4j.node;

import com.mobileai.luncert.model.normal.NormalEvent;

import org.neo4j.driver.v1.Value;

public class Event extends NormalEvent {

	private static final long serialVersionUID = 4226780650949444326L;
	
	private boolean beScene;

    public Event(Value value) {
        eventId = value.get("eventId", 0);
        name = value.get("name", "");
		eventType = value.get("eventType", "");
		beScene = value.get("beScene", false);
	}
	
    public boolean beScene() { return beScene; }

	public void setBeScene(boolean beScene) { this.beScene = beScene; }

	public boolean getBeScene() { return beScene; }
    
}