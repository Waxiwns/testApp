// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import io.qameta.allure.Step;
import pages.orderPageTabs.DriverAnalysePage;

import static com.codeborne.selenide.Condition.*;
import static pages.orderPageTabs.DriverAnalysePage.PAGE_TITLE;

public class DriverAnalysePageSteps {

    private DriverAnalysePage driverAnalysePage = new DriverAnalysePage();

    @Step
    public void titleShouldBe() {
        driverAnalysePage.pageTitle.shouldBe(text(PAGE_TITLE));
    }

    @Step
    public void getButtonShouldBeVisible() {
        driverAnalysePage.getButton.shouldBe(visible);
    }

    @Step
    public void getButtonClick() {
        getButtonShouldBeVisible();
        driverAnalysePage.getButton.click();
    }

    @Step
    public void idInputFieldShouldBeVisible() {
        driverAnalysePage.idInputField.shouldBe(visible);
    }

    @Step
    public void idInputFieldShouldHaveDriverId(String driverId) {
        idInputFieldShouldBeVisible();
        driverAnalysePage.idInputField.shouldHave(value(driverId));
    }

    @Step
    public void resultBlockShouldHaveText(String text) {
        driverAnalysePage.resultBlock.shouldHave(text(text));
    }
}