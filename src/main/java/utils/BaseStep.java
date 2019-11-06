// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.Color;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.WebDriverRunner.url;
import static core.TestStepLogger.log;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static utils.constants.PageIdentifiers.*;

public class BaseStep extends Base {

    public void elementShouldHaveAttribute(SelenideElement element, String attributeName, String value) {
        element.shouldHave(attribute(attributeName, value));
    }

    public void elementShouldHaveCssValue(SelenideElement element, String propertyName, String value) {
        if (propertyName.contains("color")) {
            element.shouldHave(cssValue((propertyName), Color.fromString(value).asRgba()));
        } else {
            element.shouldHave(cssValue((propertyName), value));
        }
    }

    public String getOrderPageUrl(String orderId, String pageName) {
        String url = testProperties.BASE_URL() + testProperties.orderLink() + orderId + "/";
        String page;

        if (pageName.equals(PROFILE.getValue())) page = PROFILE.getUrlValue();
        else if (pageName.equals(CART.getValue())) page = CART.getUrlValue();
        else if (pageName.equals(HISTORY.getValue())) page = HISTORY.getUrlValue();
        else if (pageName.equals(CHANGES.getValue())) page = CHANGES.getUrlValue();
        else if (pageName.equals(COSTCALCULATION.getValue())) page = COSTCALCULATION.getUrlValue();
        else if (pageName.equals(TRANSACTIONS.getValue())) page = TRANSACTIONS.getUrlValue();
        else if (pageName.equals(TICKETS.getValue())) page = TICKETS.getUrlValue();
        else if (pageName.equals(TRACK.getValue())) page = TRACK.getUrlValue();
        else {
            log(testProperties.errorLink() + pageName);
            fail();
            return "";
        }
        return url + page;
    }

    @Step
    public void checkPageUrl(String orderId, String pageName) {
        assertEquals(getOrderPageUrl(orderId, pageName), url());
    }

    public long currentTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public String stringCurrentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000L);
    }

    protected String txDateTimeFormat = "MMM d, yyyy, h:mm:ss a";

    // Attributes
    protected String placeholder = "placeholder";
    protected String requiredField = "required";
    protected String color = "color";

    // Colors
    protected String colorRed = "#dc3545!important";
    protected String colorGreen = "#28a745!important";
}
