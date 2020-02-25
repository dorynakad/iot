package Messages;



public class RegisterMessage extends UDPMessage {
    private static final long serialVersionUID = 7526472295622776147L;

    //type 0 is from app
    public int type = 0;
    
    public RegisterMessage(){

        setType("Register");
        //System.out.println("Register message created");
    }

}
