package ca.bsolomon.gw2events.southsun.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

public class BackingData {

	private static ConcurrentMap<String, String> eventState = new ConcurrentHashMap<String, String>(16, 0.9f, 1);
	private static ConcurrentMap<String, DateTime> eventTime = new ConcurrentHashMap<String, DateTime>(16, 0.9f, 1);
	private static ConcurrentMap<String, DateTime> lastActiveTime = new ConcurrentHashMap<String, DateTime>(16, 0.9f, 1);
	private static ConcurrentMap<String, Period> maxEventDiff = new ConcurrentHashMap<String, Period>(16, 0.9f, 1);
	private static ConcurrentMap<String, Period> minEventDiff = new ConcurrentHashMap<String, Period>(16, 0.9f, 1);
	
	private static Period THREEHOURS = new Period(3,0, 0, 0);
	
	public boolean addEventState(String eventId, String state, DateTime time) {
		if (eventState.containsKey(eventId)) {
			if (!eventState.get(eventId).equals(state)) {
				eventState.put(eventId, state);
				eventTime.put(eventId, time);
				
				if (state.equals("Active")) {
					computeEventTiming(eventId, time);
				}
				
				return true;
			}
		} else {
			eventState.put(eventId, state);
			eventTime.put(eventId, time);
			
			return true;
		}
		
		return false;
	}
	
	private void computeEventTiming(String eventId, DateTime time) {
		if (lastActiveTime.containsKey(eventId)) {
			DateTime startEvent = lastActiveTime.get(eventId);
			
			Period p = new Period(startEvent, time);
						
			if (maxEventDiff.containsKey(eventId)) {
				if (maxEventDiff.get(eventId) != null) {
					Duration maxD = maxEventDiff.get(eventId).toDurationFrom(time);
					Duration currD = p.toDurationFrom(time);
					Duration threeHoursD = THREEHOURS.toDurationFrom(time);
					
					if (currD.isLongerThan(maxD) && currD.isShorterThan(threeHoursD)) {
						maxEventDiff.put(eventId, p);
					}
				}
			} else {
				maxEventDiff.put(eventId, p);
			}
			
			if (minEventDiff.containsKey(eventId)) {
				if (minEventDiff.get(eventId) != null) {
					Duration minD = minEventDiff.get(eventId).toDurationFrom(time);
					Duration currD = p.toDurationFrom(time);
					
					if (currD.isShorterThan(minD)) {
						minEventDiff.put(eventId, p);
					}
				}
			} else {
				minEventDiff.put(eventId, p);
			}
		}
		
		lastActiveTime.put(eventId, time);
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
	
	public DateTime getEventTime(String eventId) {
		return eventTime.get(eventId);
	}
	
	public Period getCurrentPeriod(String eventId, DateTime time) {
		if (lastActiveTime.get(eventId)!=null)
			return new Period(lastActiveTime.get(eventId), time);
		else
			return new Period(time, time);
	}
	
	public Period getMaxPeriod(String eventId) {
		return maxEventDiff.get(eventId);
	}
	
	public Period getMinPeriod(String eventId) {
		return minEventDiff.get(eventId);
	}

	public static ConcurrentMap<String, DateTime> getLastActiveTime() {
		return lastActiveTime;
	}

	public static void setLastActiveTime(
			ConcurrentMap<String, DateTime> lastActiveTime) {
		BackingData.lastActiveTime = lastActiveTime;
	}
}

