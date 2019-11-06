// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.ProductDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.text;

public class ProductDetailsPageSteps extends BaseStep {

    private ProductDetailsPage productDetailsPage = new ProductDetailsPage();

    @Step
    public void productTitleShouldHave(String text) {
        productDetailsPage.orderCartHeaderText.shouldHave(text(text));
    }
}
