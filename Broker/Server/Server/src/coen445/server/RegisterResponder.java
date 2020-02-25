package coen445.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import Messages.*;


public class RegisterResponder extends BaseResponder {

    RegisterUpdateMessage registerUpdateMessage;
    RegisterMessage registerMessage;

    public RegisterResponder() {

    }


    @Override
    public void respond() {

        System.out.println("this is the RegisterResponder respond method");
        registerMessage = (RegisterMessage) message;
        
        addIpToData(IPAddress, port, registerMessage.getDeviceType());
        displayListOfParticipants();

        Set<InetAddress> inetAddresses = Broker.ipToDevice.keySet();

        for(InetAddress i : inetAddresses){

            DeviceData data = Broker.ipToDevice.get(i);

            UDPMessage udpMessage = new RegisterUpdateMessage();

            try {
                sendData = Broker.getBytes(udpMessage);
            } catch (IOException e) {
                System.out.println("error in RegisterResponder getBytes");
                e.printStackTrace();
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getIPAddress(), data.getPort());
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                System.out.println("error in RegisterResponder sendPacket");

                e.printStackTrace();
            }

        }
        synchronized (Broker.BACKUP) {
        	Broker.writeToBackup();
		}
        

    }


    private void addIpToData(InetAddress ipAddress, int port, int type) {
        System.out.println("Adding client to list of registered users");
        DeviceData data = new DeviceData(ipAddress,port,type);


        Broker.ipToDevice.put(ipAddress, data);

        System.out.println("Adding client to list is Successful");

    }

    private void displayListOfParticipants(){

        System.out.println("Displaying the list of registered devices:");
        Set<InetAddress> mySet = Broker.ipToDevice.keySet();
        int counter = 0;
        for(InetAddress i : mySet){
            counter++;
            System.out.println(counter + "-------------------------");
            DeviceData data = Broker.ipToDevice.get(i);
            System.out.println("IP: " + data.getIPAddress() + "  Port: " + data.getPort() + " Device Type: " + data.getType());
        }
        System.out.println("--------------------------");
    }
}

