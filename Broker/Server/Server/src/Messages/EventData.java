package Messages;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventData implements Serializable {
	private static final long serialVersionUID = 7526472295622776147L;
	
	private int eventID;
	private String name;
	
	public EventData() {
		
	}
	
	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
