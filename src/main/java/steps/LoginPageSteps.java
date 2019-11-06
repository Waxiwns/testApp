// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.sleep;
import static pages.LoginPage.*;

public class LoginPageSteps extends BaseStep {

    public String alignAr = "right";
    public String alignEn = "start";

    private LoginPage loginPage = new LoginPage();

    @Step
    public void authorizationStep(String email, String password) {

        inputDataInEmailField(email);

        inputDataInPasswordField(password);

        clickSubmitButton();
    }

    @Step
    public void inputDataInEmailField(String email) {
        sleep(500);
        loginPage.email.shouldBe(visible).sendKeys(email);
    }

    @Step
    public void inputDataInPasswordField(String password) {
        sleep(500);
        loginPage.password.shouldBe(visible).sendKeys(password);
    }

    @Step
    public void placeholderOfEmailInputShouldBe() {
        loginPage.email.shouldHave(attribute("placeholder", EMAIL_PLACEHOLDER));
    }

    @Step
    public void placeholderOfPassInputShouldBe() {
        loginPage.password.shouldHave(attribute("placeholder", PASSWORD_PLACEHOLDER));
    }

    @Step
    public void clickSubmitButton() {
        checkVisibilityOfSubmitButton();
        checkThatSubmitButtonIsEnabled();
        loginPage.submit.click();
    }

    @Step
    public void checkVisibilityOfSubmitButton() {
        loginPage.submit.shouldBe(visible);
    }

    @Step
    public void checkThatSubmitButtonIsEnabled() {
        loginPage.submit.shouldBe(enabled);
    }

    @Step
    public void checkThatSubmitButtonIsDisabled() {
        loginPage.submit.shouldBe(disabled);
    }

    @Step
    public void checkTextOfSubmitButton(String text) {
        loginPage.submit.shouldHave(text(text));
    }

    @Step
    public void switchLanguageTo(String lang) {
        if (!loginPage.languageSwitcher.getSelectedOption().getText().toUpperCase().equals(lang))
            loginPage.languageSwitcher.selectOptionContainingText(lang.toUpperCase());
    }

    @Step
    public void checkEmailFieldLabelText(String labelText) {
        loginPage.emailFieldLabel.shouldHave(text(labelText));
    }

    @Step
    public void checkPassFieldLabelText(String labelText) {
        loginPage.passFieldLabel.shouldHave(text(labelText));
    }

    @Step
    public void checkLoginPageTitleText(String labelText) {
        loginPage.loginPageTitle.shouldHave(text(labelText));
    }

    @Step
    public void checkLoginBlockTitleText(String labelText) {
        loginPage.loginBlockTitle.shouldHave(text(labelText));
    }

    @Step
    public void checkLanguageSwitcherText(String labelText) {
        loginPage.languageSwitcher.shouldHave(text(labelText));
    }

    @Step
    public void checkErrorMessageInLogInBlock() {
        loginPage.errorMessageInLogInBlock.shouldBe(visible);
    }

    @Step
    public void loginErrorShouldHaveText(String locale) {
        checkErrorMessageInLogInBlock();
        loginPage.errorMessageInLogInBlock.shouldHave(text(locale.equals("en") ? ERROR_FIELD_TITLE_EN : ERROR_FIELD_TITLE_AR));
    }

    @Step
    public void checkArabicTranslationAndAlignElements() {
        checkEmailFieldLabelText(EMAIL_FIELD_TITLE_AR);
        loginPage.emailFieldLabel.shouldHave(cssValue("text-align", alignAr));

        checkPassFieldLabelText(PASSWORD_FIELD_TITLE_AR);
        loginPage.passFieldLabel.shouldHave(cssValue("text-align", alignAr));

        checkTextOfSubmitButton(SUBMIT_BUTTON_AR);
        loginPage.submit.shouldHave(cssValue("text-align", alignAr));

        checkLanguageSwitcherText(LANGUAGE_SWITCHER_AR);
        loginPage.languageSwitcher.shouldHave(cssValue("text-align", alignEn)); // should be right align but it is not visible
    }
}
