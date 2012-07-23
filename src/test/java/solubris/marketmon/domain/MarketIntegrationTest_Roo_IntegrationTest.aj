// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketIntegrationTest;

privileged aspect MarketIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MarketIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MarketIntegrationTest: @Transactional;
    
    @Test
    public void MarketIntegrationTest.testCountMarkets() {
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", dod.getRandomMarket());
        long count = Market.countMarkets();
        Assert.assertTrue("Counter for 'Market' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MarketIntegrationTest.testFindMarket() {
        Market obj = dod.getRandomMarket();
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Market' failed to provide an identifier", id);
        obj = Market.findMarket(id);
        Assert.assertNotNull("Find method for 'Market' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Market' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MarketIntegrationTest.testFindAllMarkets() {
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", dod.getRandomMarket());
        long count = Market.countMarkets();
        Assert.assertTrue("Too expensive to perform a find all test for 'Market', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Market> result = Market.findAllMarkets();
        Assert.assertNotNull("Find all method for 'Market' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Market' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void MarketIntegrationTest.testFindMarketEntries() {
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", dod.getRandomMarket());
        long count = Market.countMarkets();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Market> result = Market.findMarketEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Market' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Market' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MarketIntegrationTest.testFlush() {
        Market obj = dod.getRandomMarket();
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Market' failed to provide an identifier", id);
        obj = Market.findMarket(id);
        Assert.assertNotNull("Find method for 'Market' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMarket(obj);
        Short currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Market' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MarketIntegrationTest.testMergeUpdate() {
        Market obj = dod.getRandomMarket();
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Market' failed to provide an identifier", id);
        obj = Market.findMarket(id);
        boolean modified =  dod.modifyMarket(obj);
        Short currentVersion = obj.getVersion();
        Market merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Market' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MarketIntegrationTest.testRemove() {
        Market obj = dod.getRandomMarket();
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Market' failed to provide an identifier", id);
        obj = Market.findMarket(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Market' with identifier '" + id + "'", Market.findMarket(id));
    }
    
}
