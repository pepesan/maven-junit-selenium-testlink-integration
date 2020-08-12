package testlink;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class AutomatedUpdateExample {

    public static String DEVKEY="e3978bbebc7bd5a9ed6fc9a4f65126d3";
    public static String URL="http://localhost/lib/api/xmlrpc/v1/xmlrpc.php";

    public static void reportResult(String TestProject,String TestPlan,String Testcase,String Build,String Notes,String Result) throws TestLinkAPIException{
        TestLinkAPIClient api=new TestLinkAPIClient(DEVKEY, URL);
        api.reportTestCaseResult(TestProject, TestPlan, Testcase, Build, Notes, Result);
    }

    @Test
    public void Test1()throws Exception
    {
        AutomatedUpdateExample a=new AutomatedUpdateExample();
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        WebDriverWait wait=new WebDriverWait(driver, 600);
        String testProject="pantera";
        String testPlan="pantera-test-plan";
        String testCase="pantera-1";
        String build="0.0.1";
        String notes=null;
        String result=null;
        try{
            driver.manage().window().maximize();
            String baseUrl = "http://demo.guru99.com/test/newtours/";
            String expectedTitle = "Welcome: Mercury Tours";
            String actualTitle = "";

            // launch Fire fox and direct it to the Base URL
            driver.get(baseUrl);

            // get the actual value of the title
            actualTitle = driver.getTitle();
            result= TestLinkAPIResults.TEST_PASSED;
            notes="Executed successfully";
        }
        catch(Exception e){
            result=TestLinkAPIResults.TEST_FAILED;
            notes="Execution failed";
        }
        finally{

            a.reportResult(testProject, testPlan, testCase, build, notes, result);
            driver.quit();
        }
    }
}