package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumDriverFactory {

    private static final String SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    private static final String PLATFORM_ANDROID = "Android";

    private static final String PLATFORM_VERSION = "6.0";

    private static final String DEVICE_NAME = "039f1bcdf0b58ecd";

//    private static final String DEVICE_NAME = "emulator-5554";
//
//    private static final String PLATFORM_VERSION = "9";

    private static final String PLATFORM_IOS = "iOS";


    public static AppiumDriver getAndroidDriver() {
        return getDriver(PLATFORM_ANDROID);
    }

    public static AppiumDriver getIOSDriver() {
        return getDriver(PLATFORM_IOS);
    }

    private static AppiumDriver getDriver(String platform) {
        URL serverUrl;
        try {
            serverUrl = new URL(SERVER_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid appium server url: " + SERVER_URL);
        }

        if (PLATFORM_IOS.equals(platform)) {
            return new IOSDriver(serverUrl, getIOSCapabilities());
        } else {
            return new AndroidDriver(serverUrl, getAndroidCapabilities());
        }
    }

//    Android driver
    private static DesiredCapabilities getAndroidCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability("gpsEnabled", true);
        capabilities.setCapability("autoGrantPermissions", true);

//        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("appPackage", "com.arammeem.toyou.android.regression");
        capabilities.setCapability("appActivity", "com.arammeem.toyou.android.app.ui.authorization.AuthorizationActivity");
        return capabilities;
    }

//    For current state no need for this driver
    private static DesiredCapabilities getIOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        capabilities.setCapability(MobileCapabilityType.UDID, "F97C79FE-5F55-4CE3-BA88-9351B7A9421F");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        capabilities.setCapability("showXcodeLog", "true");
        capabilities.setCapability("autoAcceptAlerts", "true");
        return capabilities;
    }
}