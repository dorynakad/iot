package coen446.thermo;



public class ResponderFactory {

    public static BaseResponder createResponder(String messageType) {

        BaseResponder newIResponder = null;
        String type = "coen446.thermo." + messageType + "Responder";
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