package ca.bsolomon.gw2events.southsun;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.GJChronology;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.bsolomon.gw2event.api.GW2EventsAPI;
import ca.bsolomon.gw2events.southsun.util.BackingData;
import ca.bsolomon.gw2events.southsun.util.SouthsunEventIDs;

public class DataRetrieveJob implements Job {
	
	private BackingData data = new BackingData();
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		if (GW2EventsAPI.eventIdToName.size() == 0) {
			System.out.println("Generating IDs");
			GW2EventsAPI.generateEventIds();
		}
		
		JSONArray result = GW2EventsAPI.queryServer(1013, 873);
		
		DateTimeZone zone = DateTimeZone.forID("America/New_York");
		Chronology gregorianJuian = GJChronology.getInstance(zone);
		DateTime time = new DateTime(gregorianJuian);
		
		for (int i=0; i<result.size(); i++) {
			JSONObject obj = result.getJSONObject(i);
			
			String eventId = obj.getString("event_id");
			String state = obj.getString("state");
			
			if (SouthsunEventIDs.eventIDs.keySet().contains(eventId)) {
				if (data.addEventState(eventId, state, time)) {
/*					String output = "["+time+"]["+SouthsunEventIDs.eventIDs.get(eventId)+"]";
					String channel = SouthsunChannelIDs.channelIDs.get(eventId);
					String color = "";
					
					if (state.equals("Active")) {
						color = "009933";
					} else if (state.equals("Fail") || state.equals("Success")) {
						color = "660000";
					} else if (state.equals("Warmup") || state.equals("Preparation")) {
						color = "CCCC33";
					}
					
					output = output+"[<span style='color: #"+color+";'>"+state+"</span>]";*/
				}
			}
		}
	}
}