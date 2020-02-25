package Messages;


/**
 * Created by Ahmed on 15-11-24.
 */
public class RegisterMessage extends UDPMessage {
    private static final long serialVersionUID = 7526472295622776147L;

    //type 0 is from app
    public int type = 2;
    
    public RegisterMessage(){

        setType("Register");
        //System.out.println("Register message created");
        type = 2;
    }

}
