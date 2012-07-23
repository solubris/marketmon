package solubris.marketmon.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import solubris.marketmon.ITestBase;
import solubris.marketmon.domain.types.MarketStatus;

//needed to force ROO not to generate another annotation which overrides definition in super class
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext-test-override.xml" })
@RooIntegrationTest(entity = Market.class)
public class MarketIntegrationTest extends ITestBase {
    @Autowired
    private MarketDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Market' failed to initialize correctly", dod.getRandomMarket());
        Market obj = dod.getNewTransientMarket(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Market' failed to provide a new transient entity", obj);
        Assert.assertNotNull("Expected 'Market' identifier to be not null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Market' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testFindMarketsByStatus() {
        List<Market> before = Market.findMarketsByStatus(MarketStatus.OPEN);

        Market obj = dod.getNewTransientMarket(12345);
        obj.setStatusOpen();
        obj.persist();
        obj.flush();
        obj = dod.getNewTransientMarket(12346);
        obj.setStatusClosed();
        obj.persist();
        obj.flush();

        List<Market> after = Market.findMarketsByStatus(MarketStatus.OPEN);

        Assert.assertEquals(before.size()+1, after.size());
    }

    @Test
    public void testFindMarketsByStatusOrStatus() {
        List<Market> before = Market.findMarketsByStatusOrStatus(MarketStatus.OPEN, MarketStatus.SUSPENDED);

        Market obj = dod.getNewTransientMarket(12345);
        obj.setStatusOpen();
        obj.persist();
        obj.flush();
        obj = dod.getNewTransientMarket(12346);
        obj.setStatusSuspended();
        obj.persist();
        obj.flush();
        obj = dod.getNewTransientMarket(12347);
        obj.setStatusClosed();
        obj.persist();
        obj.flush();

        List<Market> after = Market.findMarketsByStatusOrStatus(MarketStatus.OPEN, MarketStatus.SUSPENDED);

        Assert.assertEquals(before.size()+2, after.size());
    }
}
