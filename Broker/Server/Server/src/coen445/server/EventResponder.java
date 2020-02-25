package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventResponder extends BaseResponder {

    EventMessage eventMessage;
    @Override
    public void respond() {
    	System.out.println("This is the EventResponder respond method");
    	eventMessage = (EventMessage) message;
    	
    	UDPMessage publishMessage;
        System.out.println("creating publish message");
    	Set<InetAddress> inetAddresses = Broker.ipToDevice.keySet();
    	for(InetAddress i : inetAddresses) {
    		DeviceData deviceData = Broker.ipToDevice.get(i);
    		
    		if(deviceData.getType() == 2) { //is thermostat
    			publishMessage = new PublishMessage(i,Broker.ipToDevice.get(i).getType(),eventMessage);
    			publishMessage.displayMessage();
    			sendMessage(publishMessage,deviceData.getIPAddress(),deviceData.getPort());
    		}
    	}
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
