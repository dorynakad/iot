package coen445.server;

import Messages.*;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Ahmed on 15-11-28.
 */
public class BaseResponder implements IResponder{

    byte[] sendData;
    InetAddress IPAddress;
    int port;
    UDPMessage message;
    DatagramSocket socket;

    public void setup(UDPMessage message, InetAddress IPAddress, int port, DatagramSocket socket){

        sendData = new byte[Broker.BUFFER_SIZE];
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void respond() {

    }
}