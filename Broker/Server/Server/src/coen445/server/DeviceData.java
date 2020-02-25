package coen445.server;

import java.io.Serializable;
import java.net.InetAddress;


public class DeviceData implements Serializable {

	private static final long serialVersionUID = 7526472295622776147L;
    private InetAddress IPAddress;
    private int port;
    //App is type 0
    //Door is type 1
    //Thermostat is type 2
    private int type;

    public DeviceData(InetAddress IPAddress, int port, int type){

        this.port = port;
        this.IPAddress = IPAddress;
        this.type = type;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public void setType(int type) {
    	this.type = type;
    }
    
    public int getType() {
		return type;
	}

}
