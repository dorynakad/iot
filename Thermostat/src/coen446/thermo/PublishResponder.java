package coen446.thermo;

import java.util.Set;

import Messages.*;

public class PublishResponder extends BaseResponder{
	private PublishMessage publishmessage;
	private TempConfigData tempconfigdata;
	private static int counter =1;
	public PublishResponder() {
		publishmessage = (PublishMessage) message;
		
		if (publishmessage.getConfigData() != null) { //PublishMessage contains configData
			
			Client.configlist.add(publishmessage.getConfigData());
			
		}
		if (publishmessage.getEventData() != null) { //PublishMessage contains eventData
			String name = publishmessage.getEventData().getName();
			if(Client.people.isEmpty()) {
				Client.temperature = 15;
			} else {
			if (Client.people.contains(publishmessage.getEventData().getName())) {
				int lowestKey=Integer.MAX_VALUE;
				boolean priorityLeave = false;
				for (int i = 0 ; i < Client.people.size() ; i++) {
					if(Client.people.get(i).equals(name)) {
						Client.people.remove(i);
						if(i == 0) {
							priorityLeave = true;
						}
						break;
					}
				}
				if(priorityLeave) {
					if(Client.people.isEmpty()) {
						Client.temperature = 15;
					} else {
						String lowest = Client.people.get(0);
						for(int i = 0; i <Client.configlist.size(); i++) {
							if(Client.configlist.get(i).getName().equals(lowest)) {
								Client.temperature = Client.configlist.get(i).getTemp();
							}
						}
					}
				}
			} else {
				boolean isEmpty = Client.people.isEmpty();
				
				Client.people.add(publishmessage.getEventData().getName());
				if(isEmpty) {
					for(int i = 0; i < Client.configlist.size() ; i++) {
						if(Client.configlist.get(i).getName().equals(publishmessage.getEventData().getName())) {
							Client.temperature = Client.configlist.get(i).getTemp();
						}
					}
				}
				
				counter++;
			}
			}
			
			
			
			
			
		}
		
		
		
	}

}
