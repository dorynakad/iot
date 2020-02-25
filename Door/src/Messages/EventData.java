package Messages;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

import coen446.door.Client;

public class EventData implements Serializable {
	private static final long serialVersionUID = 7526472295622776147L;
	private static int counter = 0;
	
	private int eventID;
	private String name;
	//private boolean entering;
	
	public EventData(String name) {
		/*entering = true;
		if(Client.inBuilding.contains(name)) {
			entering = false;
			Client.inBuilding.remove(name);
		} else {
			Client.inBuilding.add(name);
		}
		*/
		this.name = name;
		eventID = counter;
		counter++;
		
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
