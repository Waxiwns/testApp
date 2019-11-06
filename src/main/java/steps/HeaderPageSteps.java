// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.HeaderPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.text;

public class HeaderPageSteps extends BaseStep {

    private HeaderPage headerPage = new HeaderPage();

    @Step
    public void usersPhotoShouldBeVisible() {
        headerPage.usersPhoto.shouldBe(Condition.visible);
    }

    @Step
    public void inputDataInOrderIdField(String orderId) {
        headerPage.inputOrderSearchField.setValue(orderId);
    }

    @Step
    public void clickOrderSearchButton() {
        headerPage.orderSearchButton.click();
    }

    @Step
    public void goToOrder(String orderId) {
        inputDataInOrderIdField(orderId);
        clickOrderSearchButton();
    }

    @Step
    public void clickLanguageSwitcherButton() {
        headerPage.languageSwitcher.click();
    }

    @Step
    public void selectLanguageInLanguageSwitcherDropDown() {
        headerPage.languageDropItem.click();
    }

    @Step
    public void changeLanguage() {
        String language = headerPage.languageSwitcher.getText();
        clickLanguageSwitcherButton();
        selectLanguageInLanguageSwitcherDropDown();
        headerPage.languageSwitcher.shouldNotHave(text(language));
    }
}
