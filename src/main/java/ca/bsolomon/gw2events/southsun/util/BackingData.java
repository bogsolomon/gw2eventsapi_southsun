package ca.bsolomon.gw2events.southsun.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BackingData {

	private static ConcurrentMap<String, String> eventState = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
	
	public boolean addEventState(String eventId, String state) {
		if (eventState.containsKey(eventId)) {
			if (!eventState.get(eventId).equals(state)) {
				eventState.put(eventId, state);
				return true;
			}
		} else {
			eventState.put(eventId, state);
			return true;
		}
		
		return false;
	}
	
	public Map<String, String> getEventStates() {
		Map<String ,String> map = new LinkedHashMap<String ,String>();
        for(String key: eventState.keySet()){
        	map.put(key, map.get(key));
        }
		
		return map;
	}
	
	public String getEventState(String eventId) {
		return eventState.get(eventId);
	}
}
