package coen446.app;
import Messages.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ListenThread implements Runnable {

    DatagramPacket receivePacket;
    DatagramSocket socket;
    InetAddress IPAddress;
    byte[] receiveData;
    public ListenThread(DatagramSocket socket,DatagramPacket receivePacket, InetAddress IPAddress, byte[] receiveData){
        this.receivePacket = receivePacket;
        this.socket = socket;
        this.IPAddress = IPAddress;
        this.receiveData = receiveData;
    }
    @Override
    public void run() {

        while(true){

            try {
                socket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            UDPMessage fromServerMessage = getUdpMessage(receiveData);


                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();


                ResponseThread myResponseThread = new ResponseThread(fromServerMessage,IPAddress,port, socket);

                Thread t = new Thread(myResponseThread);
                t.start();

        }

    }

    private UDPMessage getUdpMessage(byte[] receiveData) {
        ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
        UDPMessage message = null;
        try {

            ObjectInputStream is = new ObjectInputStream(in);
            message = (UDPMessage) is.readObject();
            System.out.println("->Client received message: \""+ message + "\"");
            message.displayMessage();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }
}
