package Messages;

/**
 * Created by Ahmed on 15-12-05.
 */
public class QuitMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;

    @Override
    public void displayMessage() {
        super.displayMessage();
        setType("Quit");
        System.out.println("Quit message created");
    }
}
