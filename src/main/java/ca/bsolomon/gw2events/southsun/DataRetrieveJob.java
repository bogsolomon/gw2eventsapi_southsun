package ca.bsolomon.gw2events.southsun;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.bsolomon.gw2events.southsun.util.GW2EventsAPI;
import ca.bsolomon.gw2events.southsun.util.SouthsunEventIDs;

public class DataRetrieveJob implements Job {

	private static Map<String, String> eventState = new HashMap<String, String>();
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		PushContext pushContext = PushContextFactory.getDefault().getPushContext();
		
		if (GW2EventsAPI.eventIdToName.size() == 0) {
			System.out.println("Generating IDs");
			GW2EventsAPI.generateEventIds();
		}
		
		JSONArray result = GW2EventsAPI.queryServer(1013, 873);
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss z");
		format.setCalendar(cal);
		String time = format.format(cal.getTime());
		
		for (int i=0;i< result.size();i++) {
			JSONObject obj = result.getJSONObject(i);
			
			String eventId = obj.getString("event_id");
			String state = obj.getString("state");
			
			if (SouthsunEventIDs.eventIDs.keySet().contains(eventId)) {
				if (eventState.containsKey(eventId)) {
					if (!eventState.get(eventId).equals(state)) {
						String output = "["+time+"]["+SouthsunEventIDs.eventIDs.get(eventId)+"]";
						
						if (state.equals("Active")) {
							output = output+"[<span style='color: #009933;'>"+state+"</span>]";
						} else if (state.equals("Fail") || state.equals("Success")) {
							output = output+"[<span style='color: #660000;'>"+state+"</span>]";
						} else if (state.equals("Warmup") || state.equals("Preparation ")) {
							output = output+"[<span style='color: #CCCC33;'>"+state+"</span>]";
						}
						
						pushContext.push("/counter", output);
					}
				}
			}
			
			eventState.put(eventId, state);
		}
	}
}