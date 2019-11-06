// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.MerchantDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.value;

public class MerchantDetailsPageSteps extends BaseStep {

    private MerchantDetailsPage merchantDetailsPage = new MerchantDetailsPage();

    @Step
    public void firstNameShouldHaveText(String name) {
        merchantDetailsPage.firstName.shouldHave(value(name));
    }

}
