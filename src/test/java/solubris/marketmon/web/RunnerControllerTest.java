package solubris.marketmon.web;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import solubris.marketmon.ITestBase;
import solubris.marketmon.web.RunnerController;

//needed to force ROO not to generate another annotation which overrides definition in super class
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext-test-override.xml"})
@Ignore
public class RunnerControllerTest extends ITestBase {

	@Autowired
    private RunnerController runnerController;

    @Test
    public void showJson() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void listJson() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void createFromJson() throws IOException {
        org.junit.Assert.assertTrue(true);

        String json=readFileContents("/runner.json");
        runnerController.createFromJson(json);
    }

    @Test
    public void createFromJsonArray() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void updateFromJson() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void updateFromJsonArray() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void deleteFromJson() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void create() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void createForm() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void show() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void list() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void update() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void updateForm() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void delete() {
        org.junit.Assert.assertTrue(true);
    }
}
