package Messages;

public class RegisterMessage extends UDPMessage {
    private static final long serialVersionUID = 7526472295622776147L;
    
    private int deviceType;

    public RegisterMessage(){

        setType("Register");
        System.out.println("Register message created");
    }

    public int getDeviceType() {
    	return deviceType;
    }
}
