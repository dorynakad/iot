package coen445.server;


import java.io.*;
import java.net.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.output.TeeOutputStream;

import Messages.*;

public class Broker{

    public static final int BUFFER_SIZE = 1024;
    static final int WAIT_TIME_MILLIS = 5000;
    public static final File BACKUP = new File("backup.tmp");
    private static DatagramSocket serverSocket;
    public static ConcurrentHashMap<InetAddress,DeviceData> ipToDevice;
    public static ConcurrentHashMap<Integer,TempConfigData> IDtoConfigData;


    public Broker() {

    }
    
    private static class DataContainer implements Serializable {
    	private static final long serialVersionUID = 7526472295622776147L;
    	public int serverPort;
    	public ConcurrentHashMap<InetAddress,DeviceData> containedIpToData;
        public ConcurrentHashMap<Integer,TempConfigData> containedIDToConfigData;
    	
        
        
		public DataContainer() {
			try {
			serverPort = 0;
			containedIpToData = new ConcurrentHashMap<InetAddress, DeviceData>();
			containedIDToConfigData = new ConcurrentHashMap<Integer, TempConfigData>();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}



		public DataContainer(int port,
				ConcurrentHashMap<InetAddress, DeviceData> containedIpToData,
				ConcurrentHashMap<Integer, TempConfigData> containedIDToConfigData) {
			
			this.serverPort = port;
			this.containedIpToData = containedIpToData;
			this.containedIDToConfigData = containedIDToConfigData;
		}
    }

    private void setup()  {
    	String time = ""+LocalDateTime.now().getYear()+LocalDateTime.now().getMonthValue()+LocalDateTime.now().
    			getDayOfMonth()+LocalDateTime.now().getHour()+LocalDateTime.now().
    			getMinute()+LocalDateTime.now().getSecond();
    	File log = new File("log"+time+".txt");
    	try {
    		log.createNewFile();
    	    FileOutputStream fos = new FileOutputStream(log);
    	    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    	        try {
    	            fos.flush();
    	        }
    	        catch (Throwable t) {
    	            // Ignore
    	        }
    	    }, "Shutdown hook Thread flushing " + log));
    	    //we will want to print in standard "System.out" and in "file"
    	    TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
    	    PrintStream ps = new PrintStream(myOut, true); //true - auto-flush after println
    	    System.setOut(ps);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        ipToDevice = new ConcurrentHashMap<InetAddress,DeviceData>();
        IDtoConfigData = new ConcurrentHashMap<Integer,TempConfigData>();
        
        try {
        	if(!setupBackup()) {
        		System.out.println("Please Configure Server");
                System.out.println("Enter the server port number");
        		int serverPort = getServerPortFromUser();

                InetAddress serverIPAddress;
                serverIPAddress = getServerInetAddress();
                createServerSocket(serverPort, serverIPAddress);
                
        	}
        	
        	writeToBackup();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        setupIpToData();
        
    }
    
    private boolean setupBackup() throws Exception {
    	boolean backupExists = false;
    	if(BACKUP.exists() && BACKUP.length() != 0) {
    		backupExists = true;
    		System.out.println("Backup detected. Restoring configuration");
    		DataContainer container = new DataContainer();
    		FileInputStream fis = new FileInputStream("backup.tmp");
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		container = (DataContainer) ois.readObject();
    		ois.close();
    		fis.close();
    		createServerSocket(container.serverPort, InetAddress.getLocalHost());
    		ipToDevice = container.containedIpToData;
    		IDtoConfigData = container.containedIDToConfigData;
    	} else {
    		BACKUP.createNewFile();
    	}
    	return backupExists;
    }

    private void setupIpToData() {

        displayRegisteredUsers();
    }

    private void displayRegisteredUsers() {
        System.out.println("Displaying the list of registered devices:");
        System.out.println("--------------------------");
        Set<InetAddress> mySet = Broker.ipToDevice.keySet();
        boolean isEmpty = true;
        for(InetAddress i : mySet){

            DeviceData data = Broker.ipToDevice.get(i);

            System.out.println("IPAddress: " + data.getIPAddress());
            System.out.println("Port: " + data.getPort());
            System.out.println("Device type: " + data.getType());
            isEmpty = false;
        }
        if(isEmpty)
            System.out.println("No registered devices");
        System.out.println("--------------------------");
    }


    private int getServerPortFromUser() {
        String userInput;
        int serverPort = 0;
        while (serverPort == 0 || serverPort < 1025 ||  serverPort > 65534) {

            System.out.println("Please enter a number greater than 1024 and less than 65535");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                userInput = bufferedReader.readLine();
                serverPort = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                System.out.println("This is not a number");
            } catch (IOException e) {


                e.printStackTrace();
            } catch (IllegalArgumentException e){
                System.out.println("Not allowed");
            }


        }
        return serverPort;
    }

    private InetAddress getServerInetAddress() {
        InetAddress serverIPAddress = null;

        try {
            serverIPAddress = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            System.out.println("Server IP address is not known");
            e.printStackTrace();
        }
        return serverIPAddress;
    }

    private void createServerSocket(int serverPort, InetAddress serverIPAddress) {
        try {
        	if(serverSocket == null) {
        		serverSocket = new DatagramSocket(serverPort,serverIPAddress);
        		System.out.println("Server setup was successful");
        	}
        } catch (SocketException e) {
            System.out.println("Could not create server socket");
            e.printStackTrace();
        }
    }

    private void displayServerInfo() {

        System.out.println("Server Port is set to: " + serverSocket.getLocalPort());
        System.out.println("Server Ip is set to: " + serverSocket.getLocalAddress());
        System.out.println("");
    }


    public void listen(){

        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);


        while(true){

            try {

                serverSocket.receive(receivePacket);

            } catch (IOException e) {
                e.printStackTrace();
            }


            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("RECEIVED Address: " + IPAddress);
            int port = receivePacket.getPort();
            System.out.println("RECEIVED Port: " + port);


            UDPMessage message;
            message = getUdpMessage(receiveData);

            ResponseThread myResponseThread = new ResponseThread(message,IPAddress,port, serverSocket);

            Thread t = new Thread(myResponseThread);
            t.start();

        }
    }

    private UDPMessage getUdpMessage(byte[] receiveData) {
        UDPMessage message = null;
        ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
        try {

            ObjectInputStream is = new ObjectInputStream(in);
            message = (UDPMessage) is.readObject();

            System.out.println("RECEIVED message: "+ message);

            System.out.println(" ");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("From Server, creating message object: " + message.toString());
        return outputStream.toByteArray();
    }
    
    public static DatagramSocket getServerSocket() {
    	return serverSocket;
    }
    
    public static Boolean isResponseNeeded(String message) {
    	Boolean needed = false;
    	return needed;
    }
    
    public static void writeToBackup() {
    	DataContainer container = new DataContainer(serverSocket.getLocalPort(), ipToDevice, IDtoConfigData);
    	try {
	    	if(!BACKUP.exists()) {
	    		BACKUP.createNewFile();
			}
    		//False to overite, true to append
			FileOutputStream fos = new FileOutputStream(BACKUP, false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(container);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) throws Exception {

        Broker myServer = new Broker();

        myServer.setup();
        myServer.displayServerInfo();
        myServer.listen();

    }

}
