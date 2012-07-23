package solubris.marketmon.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import solubris.marketmon.ITestBase;

@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext-test-override.xml" })
@RooIntegrationTest(entity = MarketStatusTime.class)
public class MarketStatusTimeIntegrationTest extends ITestBase {

    @Test
    public void testMarkerMethod() {
    }
}
