package solubris.marketmon.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Market.class)
public class MarketDataOnDemand {
    @Autowired
    private EventDataOnDemand eventDataOnDemand;

    public Market getNewTransientMarket(int index) {
        Market obj = new Market();
        setId(obj, (long)index+2);
        setState(obj, index);
        setDescription(obj, index);
        setEvent(obj, index);
        setExchangeId(obj, index);
        setMarketDataDelayed(obj, index);
        return obj;
    }

    public void setEvent(Market obj, int index) {
        Event event = eventDataOnDemand.getRandomEvent();
        obj.setEvent(event);
    }

    public void setId(Market obj, long index) {
        obj.setId(index);
    }
}
