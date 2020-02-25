package coen446.app;


public class QuitResponder extends BaseResponder {

    @Override
    public void respond() {
        super.respond();
        System.out.println("Quitting");
        Client.BACKUP.deleteOnExit();
        System.exit(0);
    }
}
