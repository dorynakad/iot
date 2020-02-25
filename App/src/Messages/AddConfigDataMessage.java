package Messages;

import java.io.*;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

import coen446.app.Client;


/**
 * Created by Ahmed on 15-11-16.
 */

public class AddConfigDataMessage extends UDPMessage {

    private static int counter = 0;
    private static final long serialVersionUID = 7526472295622776147L;

    private int requestNumber;
    private TempConfigData configData;

    public AddConfigDataMessage() {

//        requestParticipantList();

        setType("AddConfigData");
        counter++;
        setRequestNumber(counter);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (!configDataReady(br)) ;
        Client.IDtoConfigData.put(requestNumber, configData);

        System.out.println("Config Message Created");

    }

    
    private boolean configDataReady(BufferedReader br) {
        try {
            System.out.println("Please enter a name ");
            setName(br.readLine());
            
            System.out.println("Please enter a temperature to assign to " + getName());
            setTemp(Integer.parseInt(br.readLine()));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


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
