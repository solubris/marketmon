// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import solubris.marketmon.domain.Runner;
import solubris.marketmon.domain.RunnerDataOnDemand;
import solubris.marketmon.domain.RunnerDescription;
import solubris.marketmon.domain.RunnerExchange;
import solubris.marketmon.domain.RunnerState;
import solubris.marketmon.domain.types.RunnerStatus;

privileged aspect RunnerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RunnerDataOnDemand: @Component;
    
    private Random RunnerDataOnDemand.rnd = new SecureRandom();
    
    private List<Runner> RunnerDataOnDemand.data;
    
    public void RunnerDataOnDemand.setDescription(Runner obj, int index) {
        RunnerDescription embeddedClass = new RunnerDescription();
        setDescriptionRunnerName(embeddedClass, index);
        obj.setDescription(embeddedClass);
    }
    
    public void RunnerDataOnDemand.setDescriptionRunnerName(RunnerDescription obj, int index) {
        String runnerName = "runnerName_" + index;
        obj.setRunnerName(runnerName);
    }
    
    public void RunnerDataOnDemand.setState(Runner obj, int index) {
        RunnerState embeddedClass = new RunnerState();
        setStateTotalMatched(embeddedClass, index);
        setStateSortPriority(embeddedClass, index);
        setStateLastPriceTraded(embeddedClass, index);
        setStateStatus(embeddedClass, index);
        obj.setState(embeddedClass);
    }
    
    public void RunnerDataOnDemand.setStateTotalMatched(RunnerState obj, int index) {
        Double totalMatched = new Integer(index).doubleValue();
        obj.setTotalMatched(totalMatched);
    }
    
    public void RunnerDataOnDemand.setStateSortPriority(RunnerState obj, int index) {
        Short sortPriority = new Integer(index).shortValue();
        obj.setSortPriority(sortPriority);
    }
    
    public void RunnerDataOnDemand.setStateLastPriceTraded(RunnerState obj, int index) {
        Double lastPriceTraded = new Integer(index).doubleValue();
        obj.setLastPriceTraded(lastPriceTraded);
    }
    
    public void RunnerDataOnDemand.setStateStatus(RunnerState obj, int index) {
        RunnerStatus status = RunnerStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public void RunnerDataOnDemand.setExchange(Runner obj, int index) {
        RunnerExchange embeddedClass = new RunnerExchange();
        setExchangeAvailableToBack(embeddedClass, index);
        setExchangeAvailableToLay(embeddedClass, index);
        obj.setExchange(embeddedClass);
    }
    
    public void RunnerDataOnDemand.setExchangeAvailableToBack(RunnerExchange obj, int index) {
        List availableToBack = null;
        obj.setAvailableToBack(availableToBack);
    }
    
    public void RunnerDataOnDemand.setExchangeAvailableToLay(RunnerExchange obj, int index) {
        List availableToLay = null;
        obj.setAvailableToLay(availableToLay);
    }
    
    public void RunnerDataOnDemand.setHandicap(Runner obj, int index) {
        Double handicap = new Integer(index).doubleValue();
        obj.setHandicap(handicap);
    }
    
    public Runner RunnerDataOnDemand.getSpecificRunner(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Runner obj = data.get(index);
        Long id = obj.getSelectionId();
        return Runner.findRunner(id);
    }
    
    public Runner RunnerDataOnDemand.getRandomRunner() {
        init();
        Runner obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getSelectionId();
        return Runner.findRunner(id);
    }
    
    public boolean RunnerDataOnDemand.modifyRunner(Runner obj) {
        return false;
    }
    
    public void RunnerDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Runner.findRunnerEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Runner' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Runner>();
        for (int i = 0; i < 10; i++) {
            Runner obj = getNewTransientRunner(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
