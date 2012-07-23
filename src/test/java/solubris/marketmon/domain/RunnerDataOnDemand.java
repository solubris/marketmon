package solubris.marketmon.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Runner.class)
public class RunnerDataOnDemand {
    public Runner getNewTransientRunner(int index) {
        Runner obj = new Runner();
        setSelectionId(obj, (long)index+2);
        setDescription(obj, index);
        setState(obj, index);
        setExchange(obj, index);
        setHandicap(obj, index);
        return obj;
    }

    public void setSelectionId(Runner obj, long index) {
        obj.setSelectionId(index);
    }

	/*
    public void setExchangeAvailableToBack(RunnerExchange obj, int index) {
        RunnerPriceSize[] availableToBack = null;
        obj.setAvailableToBack(availableToBack);
    }
    
    public void setExchangeAvailableToLay(RunnerExchange obj, int index) {
        RunnerPriceSize[] availableToLay = null;
        obj.setAvailableToLay(availableToLay);
    }
   */ 
}
