// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.MerchantUserDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;

public class MerchantUserDetailsPageSteps extends BaseStep {

    private MerchantUserDetailsPage merchantUserDetailsPage = new MerchantUserDetailsPage();

    @Step
    public void pageTitleShouldBeVisible(String value) {
        merchantUserDetailsPage.pageTitle.shouldBe(visible).shouldBe(text(value));
    }

    @Step
    public void customerFirstNameEnShouldHaveText(String name) {
        merchantUserDetailsPage.firstName.shouldHave(value(name));
    }

    @Step
    public void customerLastNameEnShouldHaveText(String name) {
        merchantUserDetailsPage.lastName.shouldHave(value(name));
    }
}
