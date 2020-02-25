package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;

/**
 * Created by Ahmed on 15-12-05.
 */
public class QuitResponder extends BaseResponder {

    QuitMessage quitMessage;

    @Override
    public void respond() {
//        super.respond();
//        System.out.println("this is the QuitResponder respond method");
//
//        quitMessage = (QuitMessage) message;
//
//        sendMessage(quitMessage,IPAddress,port);
        super.respond();
        System.out.println("Quitting");
        Broker.BACKUP.deleteOnExit();
        System.exit(0);
    }


    private void sendMessage(UDPMessage udpMessage, InetAddress address, int port) {
        try {
            sendData = Broker.getBytes(udpMessage);
        } catch (IOException e) {
            System.out.println("error in getBytes");
            e.printStackTrace();
        }
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,address ,port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            System.out.println("error in sendPacket");

            e.printStackTrace();
        }
    }
}




