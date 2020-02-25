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

                type = "coen445.server." + br.readLine();
                newMessage = (UDPMessage) Class.forName(type).newInstance();
                return newMessage;

            }catch (IOException e) {

                System.out.println("Could not read input");

            }catch (ClassNotFoundException e) {

                System.out.println("This is not a correct message type");

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return newMessage;

    }
}

