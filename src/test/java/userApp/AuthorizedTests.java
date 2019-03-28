package userApp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.userApp.AuthorizationActivitySteps;
import steps.userApp.EnterToYouActivitySteps;
import steps.userApp.GeneralActivitySteps;
import steps.userApp.MoreTabSteps;
import utils.AppiumDriverFactory;

public class AuthorizedTests {

    int timeout = 10000;

    String firstTab = "Shopping";

    String country = "Ukraine";

    String phoneNumber = "0630000000";

    String moreTab = "More";

    String firstName = "Tester";

    String secondName = "Test";

    private AppiumDriver driver;

    private AuthorizationActivitySteps authorizationSteps = new AuthorizationActivitySteps();

    private GeneralActivitySteps generalSteps = new GeneralActivitySteps();

    private EnterToYouActivitySteps enterSteps = new EnterToYouActivitySteps();

    private MoreTabSteps moreSteps = new MoreTabSteps();


    @Before
    public void setUp() {
        driver = AppiumDriverFactory.getAndroidDriver();
        WebDriverRunner.setWebDriver(driver);
        Configuration.timeout = timeout;
    }

    @Test
    public void authorizeAndGoMore() {
        authorizationSteps.clickStarted();
        enterSteps.enterActivityIsDisplayed();

//        sign in
        enterSteps.signIn(country, phoneNumber);
        generalSteps.activeTab(firstTab);

//        go to More
        generalSteps.chooseTab(moreTab);
        moreSteps.titleActivityShouldBe(moreTab);
        moreSteps.userNameShouldBe(firstName + " " + secondName);
    }

    @After
    public void closeApp(){
        driver.quit();
    }
}
