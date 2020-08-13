package testlink;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class MyStepdefs {
    public static String DEVKEY="e3978bbebc7bd5a9ed6fc9a4f65126d3";
    public static String URL="http://localhost/lib/api/xmlrpc/v1/xmlrpc.php";
    String testProject="pantera";
    String testPlan="pantera-test-plan";
    String testCase="pantera-1";
    String build="0.0.1";
    String notes=null;
    String result=null;
    WebDriver driver;
    WebDriverWait wait;
    String expectedTitle = "Welcome: Mercury Tours";
    String actualTitle = "";
    public static void reportResult(String TestProject,String TestPlan,String Testcase,String Build,String Notes,String Result) throws TestLinkAPIException {
        TestLinkAPIClient api=new TestLinkAPIClient(DEVKEY, URL);
        api.reportTestCaseResult(TestProject, TestPlan, Testcase, Build, Notes, Result);
    }
    @Before
    public void setUp(){
        AutomatedUpdateExample a=new AutomatedUpdateExample();
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        wait=new WebDriverWait(driver, 600);


    }
    @Given("^Abro el navegador$")
    public void initializeDriver() throws Throwable {
        try {
            driver.manage().window().maximize();
        }catch(Exception e){
            this.result= TestLinkAPIResults.TEST_FAILED;
            notes="Execution failed: failed webdriver init";
        }
    }

    @When("^Voy a la página principal$")
    public void openMainPage() throws Throwable {
        String baseUrl = "http://demo.guru99.com/test/newtours/";
        // launch driver and direct it to the Base URL
        driver.get(baseUrl);
    }

    @Then("^Compruebo que está todo ok$")
    public void validateResult() throws Throwable {
        // get the actual value of the title
        actualTitle = driver.getTitle();
        try{
            Assert.assertThat(actualTitle, Matchers.equalTo(expectedTitle));
            result= TestLinkAPIResults.TEST_PASSED;
            notes="Executed successfully";
        } catch (Exception e){
            result= TestLinkAPIResults.TEST_FAILED;
            notes="Execution failed: assert failed";
            e.printStackTrace();
        }
        finally {
            reportResult(testProject, testPlan, testCase, build, notes, this.result);
            driver.quit();
        }
    }
}
