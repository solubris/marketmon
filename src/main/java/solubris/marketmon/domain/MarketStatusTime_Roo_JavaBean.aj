// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.util.Date;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketStatusTime;
import solubris.marketmon.domain.types.MarketStatus;

privileged aspect MarketStatusTime_Roo_JavaBean {
    
    public Date MarketStatusTime.getCreatedAt() {
        return this.createdAt;
    }
    
    public void MarketStatusTime.setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Market MarketStatusTime.getMarket() {
        return this.market;
    }
    
    public void MarketStatusTime.setMarket(Market market) {
        this.market = market;
    }
    
    public Boolean MarketStatusTime.getInplay() {
        return this.inplay;
    }
    
    public void MarketStatusTime.setInplay(Boolean inplay) {
        this.inplay = inplay;
    }
    
    public MarketStatus MarketStatusTime.getStatus() {
        return this.status;
    }
    
    public void MarketStatusTime.setStatus(MarketStatus status) {
        this.status = status;
    }
    
}
