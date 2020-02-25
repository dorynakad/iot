package Messages;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Ahmed on 15-11-16.
 */
public class MessageFactory {

    public UDPMessage createMessage(BufferedReader br) {

        UDPMessage newMessage = null;
        String type = null;

        while (newMessage == null) {

            try {
                System.out.println("Please enter message type");
                
                type = "Messages." + br.readLine() + "Message";
                newMessage = (UDPMessage) Class.forName(type).newInstance();
                return newMessage;

            }catch (IOException e) {

                System.out.println("Could not read input");

            }catch (ClassNotFoundException e) {

                System.out.println("This is not a correct message type");

            } catch (InstantiationException e) {
                System.out.println("The User is not allowed to send this type of message");

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return newMessage;

    }
}

