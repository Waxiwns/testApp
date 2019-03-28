import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorizedTests {

    String firstTab = "Shopping";

    String country = "Ukraine";

    String phoneNumber = "0630000000";

    private AppiumDriver driver;

    private AuthorizationActivitySteps authorizationSteps = new AuthorizationActivitySteps();

    private GeneralActivitySteps generalSteps = new GeneralActivitySteps();

    private EnterToYouActivitySteps enterSteps = new EnterToYouActivitySteps();


    @Before
    public void setUp() {
        driver = AppiumDriverFactory.getAndroidDriver();
        WebDriverRunner.setWebDriver(driver);
        Configuration.timeout = 10000;
    }

    @Test
    public void authorize() {
        authorizationSteps.clickStarted();
        enterSteps.enterActivityIsDisplayed();

//        sign in
        enterSteps.signIn(country, phoneNumber);
        generalSteps.activeTab(firstTab);
    }

    @After
    public void closeApp(){
        driver.quit();
    }
}
