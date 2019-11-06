// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseStep;
import utils.constants.Buttons;
import utils.constants.Statuses;

import static com.codeborne.selenide.Condition.*;
import static utils.constants.Buttons.CANCEL_lower;
import static utils.constants.Buttons.SAVE;

public class OrderProfilePageSteps extends BaseStep {

    private OrderProfilePage orderProfilePage = new OrderProfilePage();

    //HISTORY_SECTION
    @Step
    public void historyTableShouldShouldBe() {
        orderProfilePage.historyTable.first().shouldBe(exist);
    }

    @Step
    public void historyTableShouldShouldHaveField(String name) {
        orderProfilePage.historyTable.find(text(name)).shouldBe(exist);
    }

    @Step
    public void paymentPopUpSaveButtonClick() {
        orderProfilePage.paymentPopUpSaveButton.shouldHave(text(SAVE.getValue()));
        orderProfilePage.paymentPopUpSaveButton.shouldBe(visible).click();
    }

    @Step
    public void paymentPopUpCancelButtonClick() {
        orderProfilePage.paymentPopUpCancelButton.shouldHave(text(CANCEL_lower.getValue()));
        orderProfilePage.paymentPopUpCancelButton.shouldBe(visible).click();
    }

    @Step
    public void paymentPopUpCrossButtonClick() {
        orderProfilePage.paymentPopUpCrossButton.shouldBe(visible).click();
    }

    @Step
    public void setPaymentMethod(String value) {
        orderProfilePage.paymentPopUpSelect.shouldBe(visible);
        orderProfilePage.paymentPopUpSelect.selectOptionContainingText(value);
    }

    @Step
    public String getPaymentMethodValue() {
        return orderProfilePage.paymentPopUpSelect.getValue();
    }

    @Step
    public void paymentMethodShouldHaveValue(String value) {
        orderProfilePage.paymentPopUpSelect.shouldHave(value(value));
    }

    @Step
    public void paymentPopUpTitleShouldBeVisible(String value) {
        orderProfilePage.paymentPopUpTitle.shouldBe(visible);
        orderProfilePage.paymentPopUpTitle.shouldHave(text(value));
    }

    @Step
    public void paymentPopUpTitleShouldNotBeVisible() {
        orderProfilePage.paymentPopUpTitle.shouldNotBe(visible);
    }


    @Step
    public void sectionTitleShouldBeVisible(String section) {
        orderProfilePage.sectionTitle(section).shouldBe(visible);
    }

    @Step
    public void sectionParameterShouldBeVisible(String section, String parameter) {
        orderProfilePage.sectionParameter(section, parameter).shouldBe(visible);
    }

    @Step
    public void sectionParameterShouldNotBeVisible(String section, String parameter) {
        orderProfilePage.sectionParameter(section, parameter).shouldNotBe(visible);
    }

    @Step
    public void sectionParameterShouldNotBe(String section, String parameter) {
        orderProfilePage.sectionParameter(section, parameter).shouldNot(visible);
    }

    @Step
    public void sectionParameterShouldHaveText(String section, String parameter, String text) {
        orderProfilePage.sectionParameter(section, parameter).shouldHave(text(text));
    }

    @Step
    public void discountShouldBeDisplayed(String section, String parameter, String text) {
        double expectedResult = Double.parseDouble("-" + text.split(" ")[0]);
        double actualResult = Double.parseDouble(orderProfilePage.sectionParameter(section, parameter).getText().split(" ")[0].split("\n")[1]);
        Assert.assertEquals(expectedResult, actualResult, 0);
    }

    @Step
    public void sectionVatShouldHaveCorrectCost(String String) {
        orderProfilePage.vatValue.shouldHave(text(String));
    }

    @Step
    public void sectionParameterShouldHaveNotText(String section, String parameter, String text) {
        orderProfilePage.sectionParameter(section, parameter).shouldNotHave(text(text));
    }

    @Step
    public void sectionParameterMessageIconShouldBeVisible(String section, String parameter) {
        orderProfilePage.sectionParameterMessageIcon(section, parameter).shouldBe(visible);
    }

    @Step
    public void sectionParameterTooltipShouldHaveText(String section, String parameter, String text) {
        sectionParameterMessageIconShouldBeVisible(section, parameter);
        orderProfilePage.sectionParameterMessageIcon(section, parameter).hover();
        orderProfilePage.sectionParameterTooltip(section, parameter).shouldBe(visible);
        orderProfilePage.sectionParameterTooltip(section, parameter).shouldBe(text(text));
    }

    @Step
    public void sectionButtonShouldBeVisible(String section, Buttons textButton) {
        orderProfilePage.sectionButton(section, textButton.getValue()).shouldBe(visible);
    }

    @Step
    public void sectionButtonShouldNotBeVisible(String section, Buttons textButton) {
        orderProfilePage.sectionButton(section, textButton.getValue()).shouldNotBe(visible);
    }

    @Step
    public void sectionParameterButtonShouldBeVisible(String section, String parameter, Buttons textButton) {
        orderProfilePage.sectionParameterButton(section, parameter, textButton.getValue()).shouldBe(visible);
    }

    @Step
    public void sectionButtonClick(String section, Buttons button) {
        SelenideElement sectionButton = orderProfilePage.sectionButton(section, button.getValue()).shouldBe(visible);
        sectionButtonEnabled(section, button);
        clickButton(sectionButton);
    }

    @Step
    public void sectionParameterButtonClick(String section, String parameter, Buttons buttons) {
        SelenideElement sectionParameterButton = orderProfilePage.sectionParameterButton(section, parameter, buttons.getValue());
        sectionParameterButtonShouldBeVisible(section, parameter, buttons);
        clickButton(sectionParameterButton);
    }

    @Step
    public void sectionButtonEnabled(String section, Buttons buttons) {
        orderProfilePage.sectionButton(section, buttons.getValue()).shouldBe(visible).shouldNotBe(disabled);
    }

    @Step
    public void sectionButtonDisabled(String section, Buttons buttons) {
        orderProfilePage.sectionButton(section, buttons.getValue()).shouldBe(visible).shouldBe(disabled);
    }

    @Step
    public void clickButton(SelenideElement button) {
        button.click();
    }

    @Step
    public void orderTrackingBlockShouldBeActive(Statuses statuses) {
        orderProfilePage.activeBlockOrderTrackingTable.shouldHave(text(statuses.getValue()));
    }

    @Step
    public void paymentMethodShouldBe(String paymentMethod) {
        orderProfilePage.paymentMethod.shouldHave(text(paymentMethod));
    }
}

