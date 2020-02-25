package coen445.server;



import Messages.*;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-03.
 */
public class ResponseThread implements Runnable {

    byte[] sendData;
    InetAddress IPAddress;
    int port;
    UDPMessage message;
    DatagramSocket socket;
    BaseResponder Responder;

    public ResponseThread(UDPMessage message, InetAddress IPAddress, int port, DatagramSocket socket){

        sendData = new byte[Broker.BUFFER_SIZE];
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
        this.message = message;
        Responder = ResponderFactory.createResponder(message.getType());
    }

    @Override
    public void run() {
        Responder.setup( message,  IPAddress,  port,  socket);
        Responder.respond();

    }
}

