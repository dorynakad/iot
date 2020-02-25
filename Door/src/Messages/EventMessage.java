package Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import coen446.door.Client;


public class EventMessage extends  UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;
    
    private EventData eventData;

    public EventMessage(){
        setType("Event");
        String name;
        System.out.println("Enter a name:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try {
			name = br.readLine();
			eventData = new EventData(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }


    @Override
    public void displayMessage() {
        super.displayMessage();
        

    }
}
