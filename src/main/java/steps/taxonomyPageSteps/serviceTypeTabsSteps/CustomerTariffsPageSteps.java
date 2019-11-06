// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.taxonomyPageSteps.serviceTypeTabsSteps;

import io.qameta.allure.Step;
import pages.taxonomy.serviceTypeTabs.CustomerTariffsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.sleep;

public class CustomerTariffsPageSteps extends BaseStep {

    private CustomerTariffsPage customerTariffsPage = new CustomerTariffsPage();

    @Step
    public void isActiveTab(String activePage) {
        customerTariffsPage.activePage.shouldHave(text(activePage));
        sleep(1000); // wait for page downloading
    }

    @Step
    public void clickOnTab(String pageUrl) {
        customerTariffsPage.getOrderPageTab(pageUrl).shouldBe(visible);
        customerTariffsPage.getOrderPageTab(pageUrl).click();
    }

    @Step
    public void inputOperationArea(String area) {
        customerTariffsPage.operationAreaInputField.sendKeys(area);
        customerTariffsPage.operationAreaInputField.pressEnter();
    }

    @Step
    public void tableInfoShouldBeDisplayed(String header) {
        customerTariffsPage.getDeliveryTableHeader(header);
    }

    @Step
    public void fieldShouldBeVisibleAndHaveCost(String header, String nameField, String cost) {
        customerTariffsPage.getFeeInputField(header, nameField).shouldBe(visible);
        customerTariffsPage.getFeeInputField(header, nameField).shouldHave(value(cost));
    }

    @Step
    public void fieldShouldBeVisibleAndEmpty(String header, String nameField) {
        customerTariffsPage.getFeeInputField(header, nameField).shouldBe(visible);
        customerTariffsPage.getFeeInputField(header, nameField).shouldBe(empty);
    }

    @Step
    public void fieldShouldBeVisibleAndRequired(String header, String nameField, String cost) {
        customerTariffsPage.getFeeInputField(header, nameField).shouldBe(visible);
        customerTariffsPage.getFeeInputField(header, nameField).shouldHave(attribute(requiredField));
        customerTariffsPage.getFeeInputField(header, nameField).shouldHave(value(cost));
    }

    @Step
    public void fieldShouldBeOff(String header, String nameField) {
        customerTariffsPage.getCheckbox(header, nameField).shouldNotBe(checked);
    }

    @Step
    public void fieldShouldBeOn(String header, String nameField) {
        customerTariffsPage.getCheckbox(header, nameField).shouldBe(checked);
    }

    @Step
    public void clickOnCheckbox(String header, String nameField) {
        customerTariffsPage.getCheckbox(header, nameField).parent().click();
    }

    @Step
    public void clickOnSaveButton() {
        customerTariffsPage.saveButton.click();
    }

    @Step
    public void fieldShouldNotBeRequired(String header, String nameField) {
        customerTariffsPage.getFeeInputField(header, nameField).shouldNotHave(attribute(requiredField));
    }
}
