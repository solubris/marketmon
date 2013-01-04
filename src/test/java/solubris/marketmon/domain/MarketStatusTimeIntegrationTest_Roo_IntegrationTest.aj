// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import solubris.marketmon.domain.MarketStatusTime;
import solubris.marketmon.domain.MarketStatusTimeDataOnDemand;
import solubris.marketmon.domain.MarketStatusTimeIntegrationTest;

privileged aspect MarketStatusTimeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MarketStatusTimeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MarketStatusTimeIntegrationTest: @Transactional;
    
    @Autowired
    MarketStatusTimeDataOnDemand MarketStatusTimeIntegrationTest.dod;
    
    @Test
    public void MarketStatusTimeIntegrationTest.testCountMarketStatusTimes() {
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", dod.getRandomMarketStatusTime());
        long count = MarketStatusTime.countMarketStatusTimes();
        Assert.assertTrue("Counter for 'MarketStatusTime' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testFindMarketStatusTime() {
        MarketStatusTime obj = dod.getRandomMarketStatusTime();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to provide an identifier", id);
        obj = MarketStatusTime.findMarketStatusTime(id);
        Assert.assertNotNull("Find method for 'MarketStatusTime' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MarketStatusTime' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testFindAllMarketStatusTimes() {
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", dod.getRandomMarketStatusTime());
        long count = MarketStatusTime.countMarketStatusTimes();
        Assert.assertTrue("Too expensive to perform a find all test for 'MarketStatusTime', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MarketStatusTime> result = MarketStatusTime.findAllMarketStatusTimes();
        Assert.assertNotNull("Find all method for 'MarketStatusTime' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MarketStatusTime' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testFindMarketStatusTimeEntries() {
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", dod.getRandomMarketStatusTime());
        long count = MarketStatusTime.countMarketStatusTimes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<MarketStatusTime> result = MarketStatusTime.findMarketStatusTimeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'MarketStatusTime' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MarketStatusTime' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testFlush() {
        MarketStatusTime obj = dod.getRandomMarketStatusTime();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to provide an identifier", id);
        obj = MarketStatusTime.findMarketStatusTime(id);
        Assert.assertNotNull("Find method for 'MarketStatusTime' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMarketStatusTime(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'MarketStatusTime' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testMergeUpdate() {
        MarketStatusTime obj = dod.getRandomMarketStatusTime();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to provide an identifier", id);
        obj = MarketStatusTime.findMarketStatusTime(id);
        boolean modified =  dod.modifyMarketStatusTime(obj);
        Integer currentVersion = obj.getVersion();
        MarketStatusTime merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MarketStatusTime' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", dod.getRandomMarketStatusTime());
        MarketStatusTime obj = dod.getNewTransientMarketStatusTime(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MarketStatusTime' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'MarketStatusTime' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void MarketStatusTimeIntegrationTest.testRemove() {
        MarketStatusTime obj = dod.getRandomMarketStatusTime();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MarketStatusTime' failed to provide an identifier", id);
        obj = MarketStatusTime.findMarketStatusTime(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'MarketStatusTime' with identifier '" + id + "'", MarketStatusTime.findMarketStatusTime(id));
    }
    
}
