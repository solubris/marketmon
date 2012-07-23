package solubris.marketmon.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Event.class)
public class EventDataOnDemand {
    public Event getNewTransientEvent(int index) {
        Event obj = new Event();
        setEventId(obj, (long)index+2);
        setEvent(obj, index);
        setEventType(obj, index);
        return obj;
    }

	private void setEventId(Event obj, long index) {
        obj.setEventId(index);
	}
}
