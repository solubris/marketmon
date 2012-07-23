package solubris.marketmon.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import solubris.marketmon.domain.Event;
import solubris.marketmon.domain.EventType;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.PriceSizeForBack;
import solubris.marketmon.domain.PriceSizeForLay;
import solubris.marketmon.domain.Runner;
import flexjson.JSONDeserializer;
import flexjson.transformer.DateTransformer;

@RooJavaBean
@RooJson
@Configurable
public class ByMarket {

    private static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private List<EventType> eventTypes = new ArrayList<EventType>();

    private Boolean indicative;

    public static ByMarket fromJsonToByMarket(String json) {
    	ByMarket byMarket=new JSONDeserializer<ByMarket>().use(Date.class, new DateTransformer(JSON_DATE_FORMAT)).use(null, ByMarket.class).deserialize(json);
        return byMarket;
    }
    
    /**
     * create link from priceSize back to runner
     * 
     * @param byMarket
     */
    public void inverseMapping() {
    	for(EventType eventType : getEventTypes()) {
        	for(Event event : eventType.getEventNodes()) {
            	for(Market market : event.getMarketNodes()) {
                	for(Runner runner : market.getRunners()) {
                    	for(PriceSizeForBack priceSizeForBack : runner.getExchange().getAvailableToBack()) {
                    		priceSizeForBack.setRunner(runner);
                    		priceSizeForBack.setMarket(market);
                    	}
                    	for(PriceSizeForLay priceSizeForLay : runner.getExchange().getAvailableToLay()) {
                    		priceSizeForLay.setRunner(runner);
                    		priceSizeForLay.setMarket(market);
                    	}
                	}
            	}
        	}
    	}
	}

	public static Collection<ByMarket> fromJsonArrayToByMarkets(String json) {
        return new JSONDeserializer<List<ByMarket>>().use(Date.class, new DateTransformer(JSON_DATE_FORMAT)).use(null, ArrayList.class).use("values", ByMarket.class).deserialize(json);
    }

	public void useBestPriceOnly() {
    	for(EventType eventType : this.eventTypes) {
        	for(Event event : eventType.getEventNodes()) {
            	for(Market market : event.getMarketNodes()) {
                	for(Runner runner : market.getRunners()) {
                		double bestPrice=0;

                		PriceSizeForBack bestPriceSizeForBack=null;
                    	for(PriceSizeForBack priceSizeForBack : runner.getExchange().getAvailableToBack()) {
                    		if(priceSizeForBack.getPrice()>=bestPrice) {
                    			bestPriceSizeForBack=priceSizeForBack;
                    			bestPrice=priceSizeForBack.getPrice();
                    		}
                    	}
                    	ArrayList<PriceSizeForBack> backPrices=new ArrayList<PriceSizeForBack>();
                    	if(bestPriceSizeForBack!=null) {
                    		backPrices.add(bestPriceSizeForBack);
                    	}
                    	runner.getExchange().setAvailableToBack(backPrices);

                		bestPrice=1000;

                		PriceSizeForLay bestPriceSizeForLay=null;
                    	for(PriceSizeForLay priceSizeForLay : runner.getExchange().getAvailableToLay()) {
                    		if(priceSizeForLay.getPrice()<=bestPrice) {
                    			bestPriceSizeForLay=priceSizeForLay;
                    			bestPrice=priceSizeForLay.getPrice();
                    		}
                    	}
                    	ArrayList<PriceSizeForLay> layPrices=new ArrayList<PriceSizeForLay>();
                    	if(bestPriceSizeForLay!=null) {
                    		layPrices.add(bestPriceSizeForLay);
                    	}
                    	runner.getExchange().setAvailableToLay(layPrices);
                	}
            	}
        	}
    	}
	}
}
