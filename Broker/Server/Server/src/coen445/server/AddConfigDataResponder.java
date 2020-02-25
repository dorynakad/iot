package coen445.server;

import Messages.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public class AddConfigDataResponder extends BaseResponder {

    public AddConfigDataResponder() {
    }

    /*
     * (non-Javadoc)
     * As soon as AddConfigData message is received, send data to door and thermostat
     * @see coen445.server.BaseResponder#respond()
     */
    @Override
    public void respond() {

        System.out.println("this is the AddConfigDataResponder respond method");
        AddConfigDataMessage addConfigDataMessage = (AddConfigDataMessage) message;
        mapConfigData((AddConfigDataMessage) addConfigDataMessage);
        

            UDPMessage publishMessage;
            System.out.println("creating publish message");
            Set<InetAddress> mySet = Broker.ipToDevice.keySet();
            for(InetAddress i : mySet){
            	publishMessage = new PublishMessage(i,Broker.ipToDevice.get(i).getType(),addConfigDataMessage);
            	publishMessage.displayMessage();
            	DeviceData deviceData = Broker.ipToDevice.get(i);
            	
            	//Send config data only to door and thermostat
            	if(deviceData.getType() == 2) { //if  thermostat
            		
                    sendMessage(publishMessage,deviceData.getIPAddress(),deviceData.getPort());
            	}
            }

        	
            synchronized (Broker.BACKUP) {
            	Broker.writeToBackup();
    		}
    }


    private void mapConfigData(AddConfigDataMessage configDataMessage) {


        System.out.println("Mapping meeting number to meeting data");
        TempConfigData configData = new TempConfigData();
        configData.setName(configDataMessage.getName());
        configData.setTemp(configDataMessage.getTemp());
        configData.setRequestID(configDataMessage.getRequestNumber());
        displayConfigDataList("Before adding");

        Broker.IDtoConfigData.put(configDataMessage.getRequestNumber(),configData);


        displayConfigDataList("After adding");

    }

    private void displayConfigDataList(String when) {

        System.out.println("Displaying meetingIDToMeetingData " + when);
        Set<Integer> mySet1 = Broker.IDtoConfigData.keySet();

        for( int i : mySet1){
            TempConfigData myData = Broker.IDtoConfigData.get(i);
            myData.displayConfigData();
        }
    }


//    private void removeMeetingDataFromMap() {
//        System.out.println("removing mapping for meeting#:" + getMeetingID());
//        displayConfigDataList("Before remove");
//        Broker.IDtoConfigData.remove(getMeetingID());
//        displayConfigDataList("After remove");
//    }

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