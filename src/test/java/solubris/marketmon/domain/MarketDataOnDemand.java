package solubris.marketmon.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Market.class)
public class MarketDataOnDemand {
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

    public void setId(Market obj, long index) {
        obj.setId(index);
    }
}
