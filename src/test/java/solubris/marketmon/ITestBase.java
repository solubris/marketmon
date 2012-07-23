package solubris.marketmon;

import java.io.IOException;
import java.io.InputStream;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/applicationContext-test-override.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ITestBase {

	public ITestBase() {
		super();
	}

	protected String readFileContents(String fileName) throws IOException {
		InputStream is=this.getClass().getResourceAsStream(fileName);
		byte []b=new byte[1000000];
		is.read(b);
		String content=new String(b);
		return content;
	}

}