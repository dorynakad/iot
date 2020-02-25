package coen446.app;



import java.net.DatagramSocket;
import java.net.InetAddress;
import Messages.*;

public class BaseResponder implements IResponder {

    byte[] sendData;
    InetAddress IPAddress;
    int port;
    UDPMessage message;
    DatagramSocket socket;

    public void setup(UDPMessage message, InetAddress IPAddress, int port, DatagramSocket socket){

        sendData = new byte[Client.BUFFER_SIZE];
        this.IPAddress = IPAddress;
        this.port = port;
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void respond() {

    }
}