package Messages;

import coen445.server.Broker;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Ahmed on 15-11-29.
 */
public class RegisterUpdateMessage extends UDPMessage {

    private ArrayList<InetAddress> updatedListOfParticipants;
    private static final long serialVersionUID = 7526472295622776147L;

    public RegisterUpdateMessage(){
        updatedListOfParticipants = new ArrayList<InetAddress>();
        setType("RegisterUpdate");
        Set<InetAddress> mySet = Broker.ipToDevice.keySet();
        for(InetAddress address : mySet){
            if(!updatedListOfParticipants.contains(address)){
                updatedListOfParticipants.add(address);
            }

        }
    }

    public ArrayList<InetAddress> getUpdatedListOfParticipants() {
        return updatedListOfParticipants;
    }
    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println(getType());
        System.out.println("Displaying updated participant list that will be sent");
        for( InetAddress address : updatedListOfParticipants){
            System.out.println(address);
        }

    }
}
