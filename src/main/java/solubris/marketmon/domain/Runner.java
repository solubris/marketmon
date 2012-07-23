package solubris.marketmon.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * prices are mapped from json as exchanged.availableToBack
 * for persistence, could map these into different field
 * bestPriceToBack1, bestPriceToBack2
 * could also record totalMatched in another field
 * 
 * @author walterst
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierField="selectionId", versionType = Short.class)
@RooJson
public class Runner {

	@Id
    @NotNull
    @Min(1)
    private Long selectionId;

    private Double handicap;

    @Embedded
    private RunnerDescription description;

    @Embedded
    private RunnerState state;

    @Embedded
    private RunnerExchange exchange;

    /**
     * Inverse relation for market/runner
     * Runner needs to know which markets it belongs to
     */
    @ManyToMany
    @JoinTable(name = "market_runner", joinColumns={@JoinColumn(name="runner")}, inverseJoinColumns={@JoinColumn(name="market")})
    private List<Market> markets = new ArrayList<Market>();

	/**
	 * only add new price if it different to previous 2 prices
	 * if new price is same as old 2 prices, then change date/time of old price to new price
	 * 
	 * XXX ForBack and ForLay methods are almost same, need to de-dup
	 * 
	 * @param availableToBack
	 * @param market
	 */
	public void addPricesForBack(List<PriceSizeForBack> availableToBack, Market market) {
		for(PriceSizeForBack priceSizeForBack : availableToBack) {
			// just load last 2 entries here
			List<? extends PriceSize> existingPrices = PriceSizeForBack.find2MostRecentPriceSizesByMarketAndRunner(market, this);
			if(existingPrices.size()>=2) {
				PriceSizeForBack e2Before=(PriceSizeForBack) existingPrices.get(1);
				PriceSizeForBack e1Before=(PriceSizeForBack) existingPrices.get(0);
				if(e2Before.getPrice().equals(e1Before.getPrice()) && e2Before.getPrice().equals(priceSizeForBack.getPrice())) {
					e1Before.setCreatedAt(priceSizeForBack.getCreatedAt());
					e1Before.merge();
					continue;
				}
			}
			exchange.getAvailableToBack().add(priceSizeForBack);
			priceSizeForBack.setRunner(this);
			priceSizeForBack.setMarket(market);
		}
	}

	/**
	 * only add new price if it different to previous 2 prices
	 * if new price is same as old 2 prices, then change date/time of old price to new price
	 * 
	 * @param availableToLay
	 * @param market
	 */
	public void addPricesForLay(List<PriceSizeForLay> availableToLay, Market market) {
		for(PriceSizeForLay priceSizeForLay : availableToLay) {
			// just load last 2 entries here
			List<? extends PriceSize> existingPrices = PriceSizeForLay.find2MostRecentPriceSizesByMarketAndRunner(market, this);
			if(existingPrices.size()>=2) {
				PriceSizeForLay e2Before=(PriceSizeForLay) existingPrices.get(1);
				PriceSizeForLay e1Before=(PriceSizeForLay) existingPrices.get(0);
				if(e2Before.getPrice().equals(e1Before.getPrice()) && e2Before.getPrice().equals(priceSizeForLay.getPrice())) {
					e1Before.setCreatedAt(priceSizeForLay.getCreatedAt());
					e1Before.merge();
					continue;
				}
			}
			exchange.getAvailableToLay().add(priceSizeForLay);
			priceSizeForLay.setRunner(this);
			priceSizeForLay.setMarket(market);
		}
	}

	public void linkPricesToMarket(Market market) {
		for(PriceSizeForBack priceSizeForBack : exchange.getAvailableToBack()) {
			priceSizeForBack.setMarket(market);
		}
		for(PriceSizeForLay priceSizeForLay : exchange.getAvailableToLay()) {
			priceSizeForLay.setMarket(market);
		}
	}

    @Override
	public String toString() {
		return String
				.format("%s selectionId=%s, handicap=%s, status=%s",
						(description==null?"":description.getRunnerName()), selectionId, handicap, (state==null?"":state.getStatus()));
	}
}
