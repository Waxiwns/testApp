package userApp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import utils.AppiumDriverFactory;


public class AppTests {

    int timeout = 10000;

    private AppiumDriver driver;


    @Before
    public void setConfiguration() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %4$s %5$s%6$s%n");

        driver = AppiumDriverFactory.getAndroidDriver();
        WebDriverRunner.setWebDriver(driver);
        Configuration.timeout = timeout;
    }

    public void printTestName(){
        System.out.println("Test " + new Throwable()
                .getStackTrace()[1]
                .getMethodName() + " is running");
    }

    @After
    public void closeApp(){
        driver.quit();
    }
}
