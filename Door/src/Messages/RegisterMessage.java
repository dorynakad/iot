package Messages;


public class RegisterMessage extends UDPMessage {
    private static final long serialVersionUID = 7526472295622776147L;

    //type 0 is from app
    int type = 1;
    
    public RegisterMessage(){

        setType("Register");
        //System.out.println("Register message created");
        type = 1;
    }

}
