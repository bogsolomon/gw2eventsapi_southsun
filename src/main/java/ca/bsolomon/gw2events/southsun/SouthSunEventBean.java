package ca.bsolomon.gw2events.southsun;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import ca.bsolomon.gw2events.southsun.util.GW2EventsAPI;
import ca.bsolomon.gw2events.southsun.util.SouthsunEventIDs;

@ManagedBean(name="southsunEventBean")
@ViewScoped
public class SouthSunEventBean {

	public String getLionVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "5B7F1A45-27DB-45D5-803F-57B6FBB5DBE8");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getLionFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "5C591A3E-900C-4B74-94CB-E2AD4E98B79A");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getPrideVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "AB17125F-A0D6-4103-99FD-FF236D03679A");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getPrideFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "C98B3487-A842-4DF3-B712-7520DA227DBE");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getOwainVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "F6ECAE88-EE84-4B64-8A4E-0BFC3FABE486");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getOwainFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "F91C5E5D-F654-43B3-9DEE-EE502A7C5E81");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKarkaVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "C037718C-0300-4357-8071-C543B9C9BE57");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKarkaFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "510DBF11-BF20-4E38-A763-BAD9A0307CB5");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKarkaKarkaStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "F6563016-3832-4EBA-82F1-535BFF96422D");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getDriftVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "834B249D-8541-4DE9-B040-845C0A349303");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getDriftFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "821557D1-CFEB-4D6D-9DDA-D4C17CE6794B");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getDriftKarkaStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "4AB50247-DB57-4C6D-9922-7E4D4FFC1F58");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getDriftInstigatorStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "81F89AC2-4764-49CB-A4F1-8B7546201DC7");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKielVictimStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "68CFE130-BB99-4297-B376-0B9E4E0B1E5B");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKielFactionsStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "F63CFAF5-14AB-4DB5-9FCC-4CCC5C8658BD");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKielKarkaStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "42B3E918-03F3-4070-8115-902773C13C0D");
		
		return formatEventResult(result.getJSONObject(0));
	}
	
	public String getKielInstigatorStatus() {
		JSONArray result = GW2EventsAPI.queryServer(1013, "72F93CD8-94AC-4234-8D86-996CCAC76A46");
		
		return formatEventResult(result.getJSONObject(0));
	}

	private String formatEventResult(JSONObject result) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss z");
		format.setCalendar(cal);
		String time = format.format(cal.getTime());
				
		String eventId = result.getString("event_id");
		String state = result.getString("state");
		
		String output = "["+time+"]["+SouthsunEventIDs.eventIDs.get(eventId)+"]";
		String color = "";
		
		if (state.equals("Active")) {
			color = "009933";
		} else if (state.equals("Fail") || state.equals("Success")) {
			color = "660000";
		} else if (state.equals("Warmup") || state.equals("Preparation ")) {
			color = "CCCC33";
		}
		
		output = output+"[<span style='color: #"+color+";'>"+state+"</span>]";
		
		return output;
	}
	
}
