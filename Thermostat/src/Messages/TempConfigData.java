package Messages;


import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArraySet;

public class TempConfigData implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private int requestID;
    private String name;
    private int temp;
    private boolean inside;


    public TempConfigData()
    {

    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
    public int getTemp() {
    	return temp;
    }
    
    public void setTemp(int temp) {
    	this.temp = temp;
    }

    public boolean isInside() {
		return inside;
	}

	public void setInside(boolean inside) {
		this.inside = inside;
	}

	public void displayConfigData(){

        System.out.println("");
        System.out.println("Displaying Temperature Configuration Data:");
        System.out.println("Request ID: " + getRequestID());
        System.out.println("Displaying Name and requested temperature:");
        System.out.println("Name:" + getName());
        System.out.println("Temp:" + getTemp());
        System.out.println("");

    }
}

