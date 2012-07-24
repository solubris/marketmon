package solubris.marketmon.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Event.class)
public class EventDataOnDemand {
    @Autowired
    private EventTypeDataOnDemand eventTypeDataOnDemand;

    public Event getNewTransientEvent(int index) {
        Event obj = new Event();
        setEventId(obj, (long)index+2);
        setEvent(obj, index);
        setEventType(obj, index);
        return obj;
    }

    public void setEventType(Event obj, int index) {
        EventType eventType = eventTypeDataOnDemand.getRandomEventType();
        obj.setEventType(eventType);
    }

	private void setEventId(Event obj, long index) {
        obj.setEventId(index);
	}
}
