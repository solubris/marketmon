// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import solubris.marketmon.domain.MarketState;
import solubris.marketmon.domain.types.MarketStatus;

privileged aspect MarketState_Roo_JavaBean {
    
    public Double MarketState.getTotalMatched() {
        return this.totalMatched;
    }
    
    public void MarketState.setTotalMatched(Double totalMatched) {
        this.totalMatched = totalMatched;
    }
    
    public Double MarketState.getTotalAvailable() {
        return this.totalAvailable;
    }
    
    public void MarketState.setTotalAvailable(Double totalAvailable) {
        this.totalAvailable = totalAvailable;
    }
    
    public Long MarketState.getVersion() {
        return this.version;
    }
    
    public void MarketState.setVersion(Long version) {
        this.version = version;
    }
    
    public Short MarketState.getNumberOfWinners() {
        return this.numberOfWinners;
    }
    
    public void MarketState.setNumberOfWinners(Short numberOfWinners) {
        this.numberOfWinners = numberOfWinners;
    }
    
    public Short MarketState.getNumberOfRunners() {
        return this.numberOfRunners;
    }
    
    public void MarketState.setNumberOfRunners(Short numberOfRunners) {
        this.numberOfRunners = numberOfRunners;
    }
    
    public Short MarketState.getNumberOfActiveRunners() {
        return this.numberOfActiveRunners;
    }
    
    public void MarketState.setNumberOfActiveRunners(Short numberOfActiveRunners) {
        this.numberOfActiveRunners = numberOfActiveRunners;
    }
    
    public Short MarketState.getBetDelay() {
        return this.betDelay;
    }
    
    public void MarketState.setBetDelay(Short betDelay) {
        this.betDelay = betDelay;
    }
    
    public Boolean MarketState.getRunnersVoidable() {
        return this.runnersVoidable;
    }
    
    public void MarketState.setRunnersVoidable(Boolean runnersVoidable) {
        this.runnersVoidable = runnersVoidable;
    }
    
    public Boolean MarketState.getBspReconciled() {
        return this.bspReconciled;
    }
    
    public void MarketState.setBspReconciled(Boolean bspReconciled) {
        this.bspReconciled = bspReconciled;
    }
    
    public Boolean MarketState.getComplete() {
        return this.complete;
    }
    
    public void MarketState.setComplete(Boolean complete) {
        this.complete = complete;
    }
    
    public Boolean MarketState.getInplay() {
        return this.inplay;
    }
    
    public void MarketState.setInplay(Boolean inplay) {
        this.inplay = inplay;
    }
    
    public Boolean MarketState.getCrossMatching() {
        return this.crossMatching;
    }
    
    public void MarketState.setCrossMatching(Boolean crossMatching) {
        this.crossMatching = crossMatching;
    }
    
    public MarketStatus MarketState.getStatus() {
        return this.status;
    }
    
    public void MarketState.setStatus(MarketStatus status) {
        this.status = status;
    }
    
}
