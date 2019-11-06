// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import pages.orderPageTabs.OrderCostCalculationPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.orderPageTabs.OrderCostCalculationPage.*;
import static io.restassured.path.json.JsonPath.from;

public class OrderCostCalculationPageSteps extends BaseStep {

    private OrderCostCalculationPage orderCostCalculationPage = new OrderCostCalculationPage();
    private double delta = 0.000001;

    @Step
    public SelenideElement getCostCalculationHeader() {
        return orderCostCalculationPage.costCalcHeaderText;
    }

    @Step
    public void checkPreAuthTable() {
        orderCostCalculationPage.preAuthTableBody.shouldBe(visible);
        orderCostCalculationPage.preAuthTableHeader.shouldBe(visible);
        orderCostCalculationPage.debtRow.shouldBe(visible);
        orderCostCalculationPage.initialAmountRow.shouldBe(visible);
        orderCostCalculationPage.reservedFromBalanceRow.shouldBe(visible);
        orderCostCalculationPage.reservedFromCreditCardRow.shouldBe(visible);
        orderCostCalculationPage.totalPreauthAmountRow.shouldBe(visible);
    }

    @Step
    public void checkPreAuthTableValues(String debtValue, String initAmount, String balanceReserved, String cardReserved, String totalPreauthAmount) {
        orderCostCalculationPage.debtValueRow.shouldBe(visible).shouldHave(text(debtValue));
        orderCostCalculationPage.initialAmountValueRow.shouldBe(visible).shouldHave(text(initAmount));
        orderCostCalculationPage.reservedFromBalanceValueRow.shouldBe(visible).shouldHave(text(balanceReserved));
        orderCostCalculationPage.reservedFromCreditCardValueRow.shouldBe(visible).shouldHave(text(cardReserved));
        orderCostCalculationPage.totalPreauthAmountValueRow.shouldBe(visible).shouldHave(text(totalPreauthAmount));
    }

    @Step
    public void checkThatTariffHeaderIsDisplayed() {
        orderCostCalculationPage.tariffLinkElement.should(visible);
    }

    @Step
    public void proceedToTheTariffProfilePage() {
        orderCostCalculationPage.tariffLinkElement.shouldBe(visible).click();
    }

    @Step
    public void checkThatTariffPageIsOpen() {
        orderCostCalculationPage.tariffProfilePageHeader.shouldBe(visible);
    }

    @Step
    public void checkTariffInfoBlockLines() {
        orderCostCalculationPage.minimalDeliveryFeeRow.shouldBe(visible);
        orderCostCalculationPage.deliveryFixedFeeRow.shouldBe(visible);
        orderCostCalculationPage.usePreEstimatedDeliveryFeeRow.shouldBe(visible);
        orderCostCalculationPage.deliveryFeeRatePerKmRow.shouldBe(visible);
        orderCostCalculationPage.deliveryFeeRatePerMinRow.shouldBe(visible);
        orderCostCalculationPage.waitingRatePerMinRow.shouldBe(visible);
        orderCostCalculationPage.freeCancellationTimeRow.shouldBe(visible);
        orderCostCalculationPage.cancellationPenaltyRow.shouldBe(visible);
        orderCostCalculationPage.driverServiceFeeRateRow.shouldBe(visible);
        orderCostCalculationPage.usePreAuthPaymentRow.shouldBe(visible);
        orderCostCalculationPage.fixedPreAuthDeliveryFeeRow.shouldBe(visible);
        orderCostCalculationPage.fixedPreAuthBasketCostRow.shouldBe(visible);
    }

    @Step
    public void checkTariffInfoBlockLinesValues() {
        orderCostCalculationPage.minimalDeliveryFeeValueRow.shouldNotBe(empty);
        orderCostCalculationPage.deliveryFixedFeeValueRow.shouldNotBe(empty);
        orderCostCalculationPage.usePreEstimatedDeliveryFeeValueRow.shouldNotBe(empty);
        orderCostCalculationPage.deliveryFeeRatePerKmValueRow.shouldNotBe(empty);
        orderCostCalculationPage.deliveryFeeRatePerMinValueRow.shouldNotBe(empty);
        orderCostCalculationPage.waitingRatePerMinValueRow.shouldBe(empty);
        orderCostCalculationPage.freeCancellationTimeValueRow.shouldNotBe(empty);
        orderCostCalculationPage.cancellationPenaltyValueRow.shouldNotBe(empty);
        orderCostCalculationPage.driverServiceFeeRateValueRow.shouldNotBe(empty);
        orderCostCalculationPage.usePreAuthPaymentValueRow.shouldNotBe(empty);
        orderCostCalculationPage.fixedPreAuthDeliveryFeeValueRow.shouldBe(empty);
        orderCostCalculationPage.fixedPreAuthBasketCostValueRow.shouldBe(empty);
    }

    @Step
    public void checkOrderReceiptLines() {
        orderCostCalculationPage.basketServiceCostLine.shouldBe(visible);
        orderCostCalculationPage.deliveryFeeLine.shouldBe(visible);
        orderCostCalculationPage.serviceFeeLine.shouldBe(visible);
        orderCostCalculationPage.cancellationFee.shouldBe(visible);
        orderCostCalculationPage.totalCostLine.shouldBe(visible);
        orderCostCalculationPage.vatLine.shouldBe(visible);
    }

    @Step
    public void checkOrderReceiptLinesValues(String basketCostValue, String deliveryFeeValue, String serviceFeeValue, String orderTotalCost, String orderVatValue) {
        orderCostCalculationPage.basketServiceCostValue.shouldBe(visible).shouldHave(text(basketCostValue));
        orderCostCalculationPage.deliveryFeeValue.shouldBe(visible).shouldHave(text(deliveryFeeValue));
        orderCostCalculationPage.serviceFeeValue.shouldBe(visible).shouldHave(text(serviceFeeValue));
        orderCostCalculationPage.cancellationFeeValue.shouldBe(visible).shouldBe(empty);
        orderCostCalculationPage.totalCostValue.shouldBe(visible).shouldHave(text(orderTotalCost));
        orderCostCalculationPage.vatValue.shouldBe(visible).shouldHave(text(orderVatValue));
    }

    @Step
    public void checkOrderReceiptLinesValues(String apiResult) {
        JsonPath result = from(apiResult);
        orderCostCalculationPage.cancellationFeeValue.shouldBe(visible).shouldBe(empty);
        assertEquals(Double.parseDouble( orderCostCalculationPage.basketServiceCostValue.getText()),result.getDouble("shown.basketCost.originalValue"),delta);
        assertEquals(Double.parseDouble( orderCostCalculationPage.deliveryFeeValue.getText()),result.getDouble("shown.deliveryFee.originalValue"),delta);
        assertEquals(Double.parseDouble( orderCostCalculationPage.serviceFeeValue.getText()), result.getDouble("shown.serviceFee.originalValue"),delta);
        assertEquals(Double.parseDouble( orderCostCalculationPage.totalCostValue.getText()),result.getDouble("shown.totalCosts.originalValue"),delta);
        assertEquals(Double.parseDouble( orderCostCalculationPage.vatValue.getText()),result.getDouble("shown.totalCosts.originalVAT"),delta);
    }

    @Step
    public void dropdownMenuShouldBeVisible() {
        orderCostCalculationPage.deliveryFeeCostDropdownMenu.shouldBe(visible);
    }

    @Step
    public void clickOnDropdownMenu() {
        dropdownMenuShouldBeVisible();
        orderCostCalculationPage.deliveryFeeCostDropdownMenu.click();
    }

    @Step
    public void chooseParameterDropdownMenu(String parameter) {
        orderCostCalculationPage.dropdownMenuFields(parameter).hover();
        orderCostCalculationPage.dropdownMenuFields(parameter).click();
    }

    @Step
    public void inputFieldDeliveryFeeShouldBeVisible() {
        orderCostCalculationPage.deliveryFeeCostInputField.shouldBe(visible);
    }

    @Step
    public void inputNewCostDeliveryFee(String profilePageElements) {
        inputFieldDeliveryFeeShouldBeVisible();
        orderCostCalculationPage.deliveryFeeCostInputField.setValue(profilePageElements);
    }

    @Step
    public void saveBtnShouldBeVisible() {
        orderCostCalculationPage.saveButton.shouldBe(visible);
    }


    @Step
    public void clickOnSaveBtn() {
        executeJavaScript("arguments[0].scrollIntoView(true);", orderCostCalculationPage.createTicket);
        saveBtnShouldBeVisible();
        orderCostCalculationPage.saveButton.click();
    }

    @Step
    public void tariffHeaderShouldBeVisible() {
        orderCostCalculationPage.tariffHeader.shouldBe(visible);
    }

    @Step
    public void clickOnTariffHeaderLink() {
        orderCostCalculationPage.tariffHeaderLink.shouldBe(visible);
        orderCostCalculationPage.tariffHeaderLink.click();
    }

    @Step
    public void fieldShouldHaveReaction(String reaction) {
        orderCostCalculationPage.usePreEstimatedDeliveryFeeValueRow.shouldBe(visible);
        orderCostCalculationPage.usePreEstimatedDeliveryFeeValueRow.shouldHave(text(reaction));
    }

    @Step
    public double parseCostStringToDouble(String cost) {
        return Double.parseDouble(cost);
    }

    @Step
    public String calculateCostWithPromo(String originalCost, String promo) {
        double result = parseCostStringToDouble(originalCost) - parseCostStringToDouble(promo);
        result *= 100;
        result = Math.round(result);
        result /= 100;

        return String.valueOf(result);
    }

    @Step
    public void checkThatCostCorrectlyDisplayedWithTwoCompositions(String str, String column, String originalCost, String promo) {
        orderCostCalculationPage.calculationTableField(str, column).shouldBe(matchesText(calculateCostWithPromo(originalCost, promo)));
    }

    @Step
    public String calculateCostWithPromoSubtractItFromTotalCost(String promo) {
        double result = parseCostStringToDouble(orderCostCalculationPage.calculationTableField(TOTAL_COSTS, AFTER_CORRECTION).getText()) - parseCostStringToDouble(promo);
        return Double.toString(result);
    }

    @Step
    public void checkThatCostSubtractItFromTotalCostCorrectlyDisplayed(String str, String column, String promo) {
        if (column.equals(SHOWN_AMOUNT)) {
            String tableAmount = Double.parseDouble(orderCostCalculationPage.calculationTableField(str, column).getText().split(" ")[0]) + "";
            assertTrue(tableAmount.contains(calculateCostWithPromoSubtractItFromTotalCost(promo)));
        } else
            orderCostCalculationPage.calculationTableField(str, column).shouldHave(exactText(promo));
    }

    @Step
    public void checkThatValueCorrectlyDisplayed(String str, String column, String value) {
        sleep(3000);
        if (column.equals(column)) {
            String tableAmount = Double.parseDouble(orderCostCalculationPage.calculationTableField(str, column).getText().split(" ")[0]) + "";
            assertTrue(tableAmount.contains(value));
        } else
            orderCostCalculationPage.calculationTableField(str, column).shouldHave(exactText(value));
    }
}
