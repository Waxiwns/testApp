// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.*;
import steps.UseAPISteps;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.objects.Order;

import java.util.Map;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderCostCalculationPage.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.PageIdentifiers.CART;
import static utils.constants.PageIdentifiers.COSTCALCULATION;

@Log4j
public class MerchantOrderCostCalculationPageTest extends BaseTest {
    private static Order classOrder;
    private static String classOrderId;
    Map<String, Float> preAuthData;

    @BeforeAll
    public static void enablePreAuth() {
        logPreconditionStep(1, "Turn on pre-auth");
        new UseAPISteps().updateServiceTypeCustomerTariffsDeliveryKharkiv(true, "delivery", false);
        classOrder = new UseAPISteps().createRegularOrder(EMPTY_VALUE);
        classOrderId = classOrder.getId();
        sleep(5000);
    }

    @AfterAll
    public static void disablePreAuth() {
        logPostconditionStep(1, "Turn off pre-auth");
        new UseAPISteps().updateServiceTypeCustomerTariffsDeliveryKharkiv(false, "delivery", false);
        logPostconditionStep(2, "Cancel created order");
        new UseAPISteps().cancelOrder(classOrder);
    }

    @BeforeEach
    public void loginAsSystemUserToAdminUI() {
        logPreconditionStep(2, "Open 'Login' page");
        configure();

        logPreconditionStep(3, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(4, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(5, "Created order and go to '" + COSTCALCULATION.getValue() + "' page");
        order = useAPISteps.updateOrderDetails(classOrderId);
        orderId = order.getId();
        preAuthData = useAPISteps.createPreAuthMap(orderId);
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());
    }

    @Test
    @Description("Check order preAuth table rows")
    public void checkOrderPreAuthTableRows() {
        logStep(1, "Check pre-auth table");
        costCalcPageSteps.checkPreAuthTable();
        String debtAmount = String.valueOf(preAuthData.get("debtAmount").intValue());
        String initialPreAuthAmount = String.valueOf(preAuthData.get("initialPreAuthAmount").intValue());
        String amountReservedFromBalance = String.valueOf(preAuthData.get("amountReservedFromBalance").intValue());
        String amountPreAuthorizedFromCard = String.valueOf(preAuthData.get("amountPreAuthorizedFromCard").intValue());
        String totalPreAuthorizedAmount = String.valueOf(preAuthData.get("totalPreAuthorizedAmount").intValue());
        costCalcPageSteps.checkPreAuthTableValues(debtAmount, initialPreAuthAmount, amountReservedFromBalance, amountPreAuthorizedFromCard, totalPreAuthorizedAmount);
    }

    @Test
    @Description("Check possibility to proceed to the customer tariffs settings")
    public void checkPossibilityToProceedToTheCustomerTariffsSettings() {
        logStep(1, "Check tariff page");
        costCalcPageSteps.checkThatTariffHeaderIsDisplayed();
        costCalcPageSteps.proceedToTheTariffProfilePage();
        costCalcPageSteps.checkThatTariffPageIsOpen();
    }

    @Test
    @Description("Check tariff info block")
    public void checkTariffInfoBlock() {
        logStep(1, "Check tarif block lines values");
        costCalcPageSteps.checkTariffInfoBlockLines();
        costCalcPageSteps.checkTariffInfoBlockLinesValues();
    }

    @Test
    @Description("Check order receipt table and values")
    public void checkOrderReceiptTableAndValues() {
        logStep(1, "Check Pre-Auth table has values");
        costCalcPageSteps.checkOrderReceiptLines();
        String debtAmount = String.valueOf(preAuthData.get("debtAmount").intValue());
        String initialPreAuthAmount = String.valueOf(preAuthData.get("initialPreAuthAmount").intValue());
        String amountReservedFromBalance = String.valueOf(preAuthData.get("amountReservedFromBalance").intValue());
        String amountPreAuthorizedFromCard = String.valueOf(preAuthData.get("amountPreAuthorizedFromCard").intValue());
        String totalPreAuthorizedAmount = String.valueOf(preAuthData.get("totalPreAuthorizedAmount").intValue());
        costCalcPageSteps.checkPreAuthTableValues(debtAmount, initialPreAuthAmount, amountReservedFromBalance, amountPreAuthorizedFromCard, totalPreAuthorizedAmount);
    }

    @Test
    @Description("Check that order receipt lines increase after adding new item to the cart")
    public void checkThatOrderReceiptLinesIncreaseAfterAddingNewItemToTheCart() {
        logStep(1, "Check order receipt lines than set values and save it");
        costCalcPageSteps.checkOrderReceiptLines();
        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
        useAPISteps.updateOrderCart(orderId);
        refresh();

        logStep(2, "RefreshPage");
        orderCartPageSteps.refreshPage();

        logStep(3, "Check values in table");
        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
        String debtAmount = String.valueOf(preAuthData.get("debtAmount").intValue());
        String initialPreAuthAmount = String.valueOf(preAuthData.get("initialPreAuthAmount").intValue());
        String amountReservedFromBalance = String.valueOf(preAuthData.get("amountReservedFromBalance").intValue());
        String amountPreAuthorizedFromCard = String.valueOf(preAuthData.get("amountPreAuthorizedFromCard").intValue());
        String totalPreAuthorizedAmount = String.valueOf(preAuthData.get("totalPreAuthorizedAmount").intValue());
        costCalcPageSteps.checkPreAuthTableValues(debtAmount, initialPreAuthAmount, amountReservedFromBalance, amountPreAuthorizedFromCard, totalPreAuthorizedAmount);
    }

    @Test
    @Description("Check that order total cost decrease after deleting item from the cart")
    public void checkThatOrderTotalCostDecreaseAfterDeletingItemFromTheCart() {
        logStep(1, "Add product ot order cart");
        useAPISteps.updateOrderCart(orderId);
        refresh();

        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
        switchToTheOrdersPage(orderId, CART.getValue());

        logStep(2, "Delete first item from the cart");
        orderCartPageSteps.deleteFirstItemFromTheCart();
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(3, "Check receipt line");
        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
    }

    @Bug
    @Issue("BACK-5942")
    @Test
    @Description("Check that order total cost decrease after deleting item from the cart with negative balance")
    public void checkThatOrderTotalCostDecreaseAfterDeletingItemFromTheCartWithNegativeBalance() {
        logStep(1, "Update customer balance");
        useAPISteps.createManualTransactionForCustomer(firstCustomer, penalty, NUMBER_200, "");

        logStep(2, "add product");
        useAPISteps.updateOrderCart(orderId);
        refresh();
        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
        switchToTheOrdersPage(orderId, CART.getValue());

        logStep(3, "Delete first product");
        orderCartPageSteps.deleteFirstItemFromTheCart();

        logStep(4, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(5, "Check that changes is saved");
        costCalcPageSteps.checkOrderReceiptLinesValues(admin.getTariffCalculator(orderId));
        costCalcPageSteps.checkPreAuthTableValues(NUMBER_0, "", "", "", "");
    }
}
