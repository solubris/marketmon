package solubris.marketmon.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = EventType.class)
public class EventTypeDataOnDemand {
    public EventType getNewTransientEventType(int index) {
        EventType obj = new EventType();
        setEventTypeId(obj, (long)index+2);
        return obj;
    }

	private void setEventTypeId(EventType obj, long index) {
        obj.setEventTypeId(index);
	}
}
