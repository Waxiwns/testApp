// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.mdb.pages;

import io.qameta.allure.Description;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.objects.Order;

import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderCostCalculationPage.FAILURE;
import static pages.orderPageTabs.OrderCostCalculationPage.SUCCESS;
import static pages.taxonomy.serviceTypeTabs.CustomerTariffsPage.*;
import static utils.constants.PageIdentifiers.COSTCALCULATION;

@FixMethodOrder()
public class MdbTariffsPageTest extends BaseTest {
    @BeforeEach
    public void goToProfilePage() {
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, MERCHANT_DELIVERY_SERVICE_TYPE, false);
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create mdb new order");
    }

    public void createOrderAndGoToCustomerTariffsTab(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(7, "Go to 'General' tab");
        costCalcPageSteps.tariffHeaderShouldBeVisible();
        costCalcPageSteps.clickOnTariffHeaderLink();
        customerTariffsPageSteps.isActiveTab(GENERAL);

        logPreconditionStep(8, "Go to 'Customer Tariffs' tab");
        customerTariffsPageSteps.clickOnTab(CUSTOMER_TARIFFS_URL);
        customerTariffsPageSteps.isActiveTab(CUSTOMER_TARIFFS);
        orderHeaderPageSteps.breadcrumbsServiceTypesTabShouldBeCorrectlyDisplayed(MERCHANT_DELIVERY_SERVICE_TYPE, CUSTOMER_TARIFFS_URL);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }

    @Test
    @Description("Customer Tariffs page: Check possibility to proceed to the 'Customer Tariffs' settings for delivery area")
    public void t01_checkPossibilityToTheProceedToTheCustomerTariffsSettingsForDeliveryArea() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "heck possibility to proceed to the 'Customer Tariffs' settings for delivery area");
        customerTariffsPageSteps.tableInfoShouldBeDisplayed(DELIVERY);
        customerTariffsPageSteps.tableInfoShouldBeDisplayed(CANCELLATION);
        customerTariffsPageSteps.tableInfoShouldBeDisplayed(PRE_AUTHORIZATION_PAYMENT);
        customerTariffsPageSteps.tableInfoShouldBeDisplayed(SERVICE_FEE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Minimal Delivery Fee' field")
    public void t02_checkTheMinimalDeliveryFeeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Minimal Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, MINIMAL_FEE, COST_TEN);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Fixed Fee' field")
    public void t03_checkTheDeliveryFixedFeeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Fixed Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, FIXED_FEE, FIVE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Rate per km' field")
    public void t04_checkTheDeliveryRatePerKmField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Rate per km' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndRequired(DELIVERY, RATE_PER_KM, ONE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Rate per min' field")
    public void t05_checkTheDeliveryRatePerKMinField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Rate per min' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, RATE_PER_MIN, ZERO);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Free cancellation time' field")
    public void t06_checkTheFreeCancellationTimeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Free cancellation time' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(CANCELLATION, FREE_TIME_CANCELLATION, ONE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Cancellation penalty' field")
    public void t07_checkTheCancellationPenaltyField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Cancellation penalty' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(CANCELLATION, PENALTY, COST_TEN);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Driver Service Fee Rate (Percent)' field")
    public void t08_checkTheDriverServiceFeeRateField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Driver Service Fee Rate (Percent)' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, DRIVER_SERVICE_FEE, ZERO);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Use pre-auth payment' field - PA is off")
    public void t09_checkTheUsePreAuthPaymentFieldIsOff() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre-auth payment' field is displayed and off");
        customerTariffsPageSteps.fieldShouldBeOff(PRE_AUTHORIZATION_PAYMENT, USE_PRE_AUTHORIZATION_PAYMENT);

        logStep(3, "Check that 'Delivery Fee' and 'Basket Cost' fields are not required");
        customerTariffsPageSteps.fieldShouldNotBeRequired(PRE_AUTHORIZATION_PAYMENT, DELIVERY_FEE);
        customerTariffsPageSteps.fieldShouldNotBeRequired(PRE_AUTHORIZATION_PAYMENT, BASKET_COST);
    }

    @Test
    @Description("Customer Tariffs page: Check enabled 'Use pre estimated Delivery Fee' field")
    public void t10_checkEnabledUsePreEstimatedDeliveryFeeField() {
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, MERCHANT_DELIVERY_SERVICE_TYPE, true);
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre estimated Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeOn(DELIVERY, USE_PRE_ESTIMATED_DELIVERY_FEE);

        logStep(3, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(4, "Check reaction in 'Tariff' table");
        costCalcPageSteps.fieldShouldHaveReaction(SUCCESS);
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, MERCHANT_DELIVERY_SERVICE_TYPE, false);
    }

    @Test
    @Description("Customer Tariffs page: Check turned off 'Use pre estimated Delivery Fee' field")
    public void t11_checkTurnedOffUsePreEstimatedDeliveryFeeField() {
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, MERCHANT_DELIVERY_SERVICE_TYPE, false);
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre estimated Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeOff(DELIVERY, USE_PRE_ESTIMATED_DELIVERY_FEE);

        logStep(3, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(4, "Check reaction in 'Tariff' table");
        costCalcPageSteps.fieldShouldHaveReaction(FAILURE);
    }
}