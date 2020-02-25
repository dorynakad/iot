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
    
    public PublishMessage(InetAddress subscriber, int deviceType, UDPMessage receivedMessage){
    	this.subscriber = subscriber;

    	if(receivedMessage.getType().equalsIgnoreCase("addconfigdata")) { //from app
    		AddConfigDataMessage configMessage = (AddConfigDataMessage) receivedMessage;
    		messageType = configMessage.getType();
    		configData = new TempConfigData(configMessage.getName(),configMessage.getTemp());
    		eventData = null;
    	}
    	
    	if(receivedMessage.getType().equalsIgnoreCase("event")) { //From door
    		EventMessage eventMessage = (EventMessage) receivedMessage;
    		messageType = eventMessage.getType();
    		eventData = eventMessage.getEventData();
    		configData = null;
    	}
    	
        setType("Publish");
        counter++;
        setMessageID(counter);
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