package coen446.app;

import Messages.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.io.output.TeeOutputStream;

public class Client {

    public static final int BUFFER_SIZE = 5120;
    static File agenda = new File("agenda.txt");
    public static final File BACKUP = new File("backup.tmp");

    public static DatagramSocket socket;
    public static int serverPort;
    public static InetAddress serverAddress;
    MessageFactory factory = new MessageFactory();
    public static ConcurrentHashMap<Integer,TempConfigData> IDtoConfigData;
    public static boolean restored =false;



    Client (){
    }

    private static class DataContainer implements Serializable {
    	private static final long serialVersionUID = 7526472295622776147L;
    	public int serverPort;
    	public InetAddress serverAddress;
        public ConcurrentHashMap<Integer,TempConfigData> containedIDToConfigData;
    	

		public DataContainer(int port, InetAddress address,
				ConcurrentHashMap<Integer, TempConfigData> containedIDToConfigData) {
			
			this.serverPort = port;
			this.serverAddress = address;
			this.containedIDToConfigData = containedIDToConfigData;
		}
		
		public DataContainer() {
			try {
			serverPort = 0;
			serverAddress = null;
			containedIDToConfigData = new ConcurrentHashMap<Integer, TempConfigData>();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    private static boolean setupBackup() throws Exception {
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
    		serverPort = container.serverPort;
    		serverAddress = container.serverAddress;
    		IDtoConfigData = container.containedIDToConfigData;
    		
    		System.out.println("Generating list of configurations");
    		Set<Integer> set = IDtoConfigData.keySet();
			System.out.println("-------------------------------");
			for(int i : set) {
				IDtoConfigData.get(i).displayConfigData();
				System.out.println("-------------------------------");
			}
			restored =true;
    	} else {
    		BACKUP.createNewFile();
    	}
    	return backupExists;
    }

    public void connect(){

        try {

            socket = new DatagramSocket();
            if(!restored) {
            IDtoConfigData = new ConcurrentHashMap<Integer,TempConfigData>();
            }

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

            if(serverPort == 0 && serverAddress == null) {
	            BufferedReader inFromUser =
	                    new BufferedReader(new InputStreamReader(System.in));
	
	            serverPort = getServerPort(inFromUser);
	
	            serverAddress = getServerAddress(inFromUser);
            }

            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            SendRegisterMessage(serverPort, serverAddress, sendPacket);


            socket.receive(receivePacket);

            writeToBackup();


            ListenThread myListenThread = new ListenThread(socket,receivePacket,serverAddress,receiveData);

            Thread listen = new Thread(myListenThread);
            listen.start();


            while(true) {


                UDPMessage newMessage = null;
                newMessage = getMessage();
                sendData = getBytes(newMessage);
                sendPacket.setData(sendData);
                socket.send(sendPacket);

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
        }

    }

    private void SendRegisterMessage(int serverPort, InetAddress IPAddress, DatagramPacket sendPacket) throws IOException {
        byte[] sendData;
        UDPMessage registerMessage = null;
        try {
            registerMessage = (UDPMessage) Class.forName("Messages.RegisterMessage").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sendData = getBytes(registerMessage);
        sendPacket.setData(sendData);
        socket.connect(IPAddress, serverPort);
        socket.send(sendPacket);
    }

    private UDPMessage getMessage() {
        UDPMessage message = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        message = factory.createMessage(br);
        message.displayMessage();
        return message;
    }

    private InetAddress getServerAddress(BufferedReader inFromUser) {
        InetAddress serverAddress = null;

        boolean isReady = false;
        while(!isReady) {

            System.out.println("Please enter the IP address for the server");

            try {
                serverAddress = InetAddress.getByName(inFromUser.readLine());
                System.out.println("You entered InetAddress = " + serverAddress);

                isReady = true;
            } catch (IOException e) {
                System.out.println("Not Valid IP address");
                isReady = false;
            }
        }
        return serverAddress;
    }

    public static byte[] getBytes(UDPMessage message) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(message);
        System.out.println("Client: creating message object: \"" + message.toString() +"\"");
        //System.out.println("");
        return outputStream.toByteArray();
    }

    //todo must add error handling here
    private int getServerPort(BufferedReader inFromUser) {
        System.out.println("Please Configure Client");
        System.out.println("Enter the server port number that the client will connect to:");


        int serverPort = 0;
        while (serverPort == 0) {


            try {
                serverPort = Integer.parseInt(inFromUser.readLine());
            } catch (IOException e) {
                System.out.println("incorrect number");
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }

        }
        System.out.println("You have entered Server port: " + serverPort);
        return serverPort;

    }

    public static void writeToBackup() {
    	DataContainer container = new DataContainer(serverPort, serverAddress, IDtoConfigData);
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

    public static void main(String[] args) throws Exception{
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

        Client client = new Client();
        setupBackup();
        client.connect();

    }

}
