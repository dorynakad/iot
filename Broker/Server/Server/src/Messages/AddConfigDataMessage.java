package Messages;


//import coen445.server.RequestParticipantListMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;




public class AddConfigDataMessage extends UDPMessage {

    private static int counter = 0;
    private static final long serialVersionUID = 7526472295622776147L;

    private int requestNumber;
    private TempConfigData configData;

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getName() {
        return configData.getName();
    }

    public void setName(String name) {
        configData.setName(name); 
    }
    
    public int getTemp() {
    	return configData.getTemp();
    }
    
    public void setTemp(int temp) {
    	configData.setTemp(temp);
    }

    private byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("From Client, creating message object:");
        System.out.println(message.toString());
        return outputStream.toByteArray();
    }

    public void displayMessage(){

        System.out.println("Message type: " + getType());
        System.out.println("Request Number: " + getRequestNumber());

        System.out.println("Name: " + getName());
        System.out.println("Temp: " + getTemp());

    }

}
