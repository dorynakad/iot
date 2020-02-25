package Messages;

/**
 * Created by Ahmed on 15-12-02.
 */
public class ConfirmMessage extends UDPMessage {

    private static final long serialVersionUID = 7526472295622776147L;
    private int meetingID;

    public ConfirmMessage(int meetingID){

        this.meetingID = meetingID;
        setType("Confirm");
    }


    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    @Override
    public void displayMessage() {
        super.displayMessage();
        System.out.println("Message type: " + getType());
        System.out.println("Meeting ID: " + meetingID);
    }
}
