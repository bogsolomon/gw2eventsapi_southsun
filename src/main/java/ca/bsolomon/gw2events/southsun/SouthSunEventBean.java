package ca.bsolomon.gw2events.southsun;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.chrono.GJChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import ca.bsolomon.gw2events.southsun.util.BackingData;
import ca.bsolomon.gw2events.southsun.util.SouthsunEventIDs;

@ManagedBean(name="southsunEventBean")
@ViewScoped
public class SouthSunEventBean {

	private BackingData data = new BackingData();
	
	private static DateTimeZone zone = DateTimeZone.forID("America/New_York");
	private static Chronology gregorianJuian = GJChronology.getInstance(zone);
	
	private static DateTimeFormatter format = new DateTimeFormatterBuilder().
			appendHourOfDay(2).appendLiteral(":").
			appendMinuteOfHour(2).appendLiteral(":").
			appendSecondOfMinute(2).appendLiteral(" ").
			appendTimeZoneShortName().toFormatter();
	
	private static PeriodFormatter HHMMSSFormater = new PeriodFormatterBuilder().
			printZeroAlways().minimumPrintedDigits(2).
			appendHours().appendSeparator(":").
			appendMinutes().appendSeparator(":").
			appendSeconds().toFormatter();
	
	private static PeriodFormatter MMSSFormater = new PeriodFormatterBuilder().
			printZeroAlways().minimumPrintedDigits(2).
			appendMinutes().appendSeparator(":").
			appendSeconds().toFormatter();
	
	public String getLionVictimStatus() {
		return formatEventResult("5B7F1A45-27DB-45D5-803F-57B6FBB5DBE8");
	}
	
	public String getLionFactionsStatus() {
		return formatEventResult("5C591A3E-900C-4B74-94CB-E2AD4E98B79A");
	}
	
	public String getPrideVictimStatus() {
		return formatEventResult("AB17125F-A0D6-4103-99FD-FF236D03679A");
	}
	
	public String getPrideFactionsStatus() {
		return formatEventResult("C98B3487-A842-4DF3-B712-7520DA227DBE");
	}
	
	public String getOwainVictimStatus() {
		return formatEventResult("F6ECAE88-EE84-4B64-8A4E-0BFC3FABE486");
	}
	
	public String getOwainFactionsStatus() {
		return formatEventResult("F91C5E5D-F654-43B3-9DEE-EE502A7C5E81");
	}
	
	public String getKarkaVictimStatus() {
		return formatEventResult("C037718C-0300-4357-8071-C543B9C9BE57");
	}
	
	public String getKarkaFactionsStatus() {
		return formatEventResult("510DBF11-BF20-4E38-A763-BAD9A0307CB5");
	}
	
	public String getKarkaKarkaStatus() {
		return formatEventResult("F6563016-3832-4EBA-82F1-535BFF96422D");
	}
	
	public String getDriftVictimStatus() {
		return formatEventResult("834B249D-8541-4DE9-B040-845C0A349303");
	}
	
	public String getDriftFactionsStatus() {
		return formatEventResult("821557D1-CFEB-4D6D-9DDA-D4C17CE6794B");
	}
	
	public String getDriftKarkaStatus() {
		return formatEventResult("4AB50247-DB57-4C6D-9922-7E4D4FFC1F58");
	}
	
	public String getDriftInstigatorStatus() {
		return formatEventResult("81F89AC2-4764-49CB-A4F1-8B7546201DC7");
	}
	
	public String getKielVictimStatus() {
		return formatEventResult("68CFE130-BB99-4297-B376-0B9E4E0B1E5B");
	}
	
	public String getKielFactionsStatus() {
		return formatEventResult("F63CFAF5-14AB-4DB5-9FCC-4CCC5C8658BD");
	}
	
	public String getKielKarkaStatus() {
		return formatEventResult("42B3E918-03F3-4070-8115-902773C13C0D");
	}
	
	public String getKielInstigatorStatus() {
		return formatEventResult("72F93CD8-94AC-4234-8D86-996CCAC76A46");
	}

	private String formatEventResult(String eventId) {
		String state = data.getEventState(eventId);
		DateTime time = data.getEventTime(eventId);
		
		String timeStr = format.print(time);
		
		DateTime now = new DateTime(gregorianJuian);
		Period period = new Period(time, now);
		
		String periodStr = HHMMSSFormater.print(period);
		
		//String output = "["+timeStr+"]"+"["+SouthsunEventIDs.eventIDs.get(eventId)+"]";
		String output = "["+SouthsunEventIDs.eventIDs.get(eventId)+"]";
		String color = "";
		
		if (state.equals("Active")) {
			color = "009933";
		} else if (state.equals("Fail") || state.equals("Success")) {
			color = "660000";
		} else if (state.equals("Warmup") || state.equals("Preparation ")) {
			color = "CCCC33";
		}
		
		output = output+"[<span style='color: #"+color+";'>"+state+"</span>]"+"["+periodStr+"]";
		
		output = output + "[Last Active: "+MMSSFormater.print(data.getCurrentPeriod(eventId, now))+"]";
		
		if (data.getMinPeriod(eventId)!=null) {
			output = output + "[Min Active: "+MMSSFormater.print(data.getMinPeriod(eventId))+"]";
		}
		
		if (data.getMaxPeriod(eventId)!=null) {
			output = output + "[Max Active: "+MMSSFormater.print(data.getMaxPeriod(eventId))+"]";
		}
		
		return output;
	}
	
}

