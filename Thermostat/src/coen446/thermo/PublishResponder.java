package coen446.thermo;

import java.util.Set;

import Messages.*;

public class PublishResponder extends BaseResponder{
	private PublishMessage publishmessage;
	private TempConfigData tempconfigdata;
	private static int counter =1;
	public PublishResponder() {
		
	}
	
	@Override
    public void respond() {
		publishmessage = (PublishMessage) message;
		
		if (!publishmessage.configDataisNull()) { //PublishMessage contains configData
			
			Client.configlist.add(publishmessage.getConfigData());
			
		}
		if (!publishmessage.eventDataisNull()) { //PublishMessage contains eventData
			String name = publishmessage.getEventData().getName();
			if(Client.people.isEmpty()) {
				Client.temperature = 15;
				
					for(int i = 0; i < Client.configlist.size() ; i++) {
						if(Client.configlist.get(i).getName().equals(publishmessage.getEventData().getName())) {
							Client.people.add(publishmessage.getEventData().getName());
							Client.temperature = Client.configlist.get(i).getTemp();
							System.out.println(publishmessage.getEventData().getName() + " has entered");
							System.out.println("Temperature is now: " + Client.temperature);
						}
					}
				
				counter++;
			} else {
				
			if (Client.people.contains(publishmessage.getEventData().getName())) {
				int lowestKey=Integer.MAX_VALUE;
				boolean priorityLeave = false;
				for (int i = 0 ; i < Client.people.size() ; i++) {
					if(Client.people.get(i).equals(name)) {
						Client.people.remove(i);
						System.out.println(publishmessage.getEventData().getName() + " has left");
						if(i == 0) {
							priorityLeave = true;
						}
						break;
					}
				}
				if(priorityLeave) {
					if(Client.people.isEmpty()) {
						Client.temperature = 15;
						System.out.println("Room is now empty");
						System.out.println("Temperature is now: " + Client.temperature);
					} else {
						String lowest = Client.people.get(0);
						for(int i = 0; i <Client.configlist.size(); i++) {
							if(Client.configlist.get(i).getName().equals(lowest)) {
								Client.temperature = Client.configlist.get(i).getTemp();
								System.out.println(Client.configlist.get(i).getName() + " now has priority");
								System.out.println("Temperature is now: " + Client.temperature);
							}
						}
					}
				}
			} else {
				for(int i = 0; i < Client.configlist.size() ; i++) {
					if(Client.configlist.get(i).getName().equals(publishmessage.getEventData().getName())) {
						Client.people.add(publishmessage.getEventData().getName());
						System.out.println(publishmessage.getEventData().getName() + " has entered");
					}
				}
			}
			}
			
			
			
			
			
		}
		
		
		
	}

}
