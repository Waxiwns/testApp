// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.DriverDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.value;

public class DriverDetailsSteps extends BaseStep {

    private DriverDetailsPage diverDetailsPage = new DriverDetailsPage();

    @Step
    public void driverFirstNameEnShouldHaveText(String name) {
        diverDetailsPage.firstNameEn.shouldHave(value(name));
    }

    @Step
    public void driverLastNameEnShouldHaveText(String name) {
        diverDetailsPage.lastNameEn.shouldHave(value(name));
    }
}
