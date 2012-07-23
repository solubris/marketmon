package solubris.marketmon.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import solubris.marketmon.ITestBase;

//needed to force ROO not to generate another annotation which overrides definition in super class
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext-test-override.xml" })
@RooIntegrationTest(entity = EventType.class)
public class EventTypeIntegrationTest extends ITestBase {
    @Autowired
    private EventTypeDataOnDemand dod;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'EventType' failed to initialize correctly", dod.getRandomEventType());
        EventType obj = dod.getNewTransientEventType(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'EventType' failed to provide a new transient entity", obj);
        Assert.assertNotNull("Expected 'EventType' identifier to be not null", obj.getEventTypeId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'EventType' identifier to no longer be null", obj.getEventTypeId());
    }
}
