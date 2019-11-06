// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.CustomerDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static pages.detailsPages.CustomerDetailsPage.TITLE;

public class CustomerDetailsPageSteps extends BaseStep {

    private CustomerDetailsPage customerDetailsPage = new CustomerDetailsPage();

    @Step
    public void pageTitleShouldBeVisible() {
        customerDetailsPage.pageTitle.shouldBe(visible).shouldBe(text(TITLE));
    }

    @Step
    public void customerFirstNameEnShouldHaveText(String name) {
        customerDetailsPage.firstName.shouldHave(value(name));
    }

    @Step
    public void customerLastNameEnShouldHaveText(String name) {
        customerDetailsPage.lastName.shouldHave(value(name));
    }
}
