package Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import coen445.server.Broker;


public class EventMessage extends  UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;

    private EventData eventData;

    public EventMessage(){
       
    }


    public EventData getEventData() {
		return eventData;
	}


	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}


	@Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("EventID: " + getEventData().getEventID());
        System.out.println("Name: " + getEventData().getName());

    }
}
