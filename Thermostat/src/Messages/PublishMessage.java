package Messages;

import java.io.Serializable;
import java.net.InetAddress;


public class PublishMessage extends UDPMessage {

    private static int counter = 0;
    private static final long serialVersionUID = 7526472295622776147L;

    private InetAddress subscriber;
    private String messageType;
    private int messageID;
    private TempConfigData configData;
    private EventData eventData;
    
    public boolean configDataisNull() {
    	return configData == null;
    }
    
    public boolean eventDataisNull() {
    	return eventData == null;
    }
    public PublishMessage(){
    	
    }


    public int getMessageID() {
        return messageID;
    }
    
    public void setMessageID(int messageID) {
    	this.messageID = messageID;
    }
    
    public InetAddress getSubscriber() {
		return subscriber;
	}

    
	public void setSubscriber(InetAddress subscriber) {
		this.subscriber = subscriber;
	}

    public TempConfigData getConfigData() {
		return configData;
	}


	public void setConfigData(TempConfigData configData) {
		this.configData = configData;
	}


	public EventData getEventData() {
		return eventData;
	}


	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}


	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	@Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Message ID: " + getMessageID());
        System.out.println("Subscriber: " + getSubscriber());
        
    }
}