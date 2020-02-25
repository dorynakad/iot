package coen445.server;

/**
 * Created by Ahmed on 15-11-24.
 */
public class ResponderFactory {

    public static BaseResponder createResponder(String messageType) {

        BaseResponder newIResponder = null;
        String type = "coen445.server." + messageType + "Responder";
        try {
            newIResponder = (BaseResponder) Class.forName(type).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find this responder type");
            e.printStackTrace();
        }


        return newIResponder;

    }
}

