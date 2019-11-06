// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
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

public class CourierOrderTariffsPageTest extends BaseTest {

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create courier new order");
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
        orderHeaderPageSteps.breadcrumbsServiceTypesTabShouldBeCorrectlyDisplayed(COURIER_SERVICE_TYPE, CUSTOMER_TARIFFS_URL);
    }

    @Test
    @Description("Customer Tariffs page: Check possibility to proceed to the 'Customer Tariffs' settings for delivery area")
    public void checkPossibilityToTheProceedToTheCustomerTariffsSettingsForDeliveryArea() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

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
    public void checkTheMinimalDeliveryFeeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Minimal Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, MINIMAL_FEE, TWELVE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Fixed Fee' field")
    public void checkTheDeliveryFixedFeeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Fixed Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, FIXED_FEE, ZERO);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Rate per km' field")
    public void checkTheDeliveryRatePerKmField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Rate per km' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndRequired(DELIVERY, RATE_PER_KM, ONE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Delivery Rate per min' field")
    public void checkTheDeliveryRatePerKMinField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Delivery Rate per min' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, RATE_PER_MIN, FIVE);
    }

    @Test
    @Description("Customer Tariffs page: Check enabled 'Use pre estimated Delivery Fee' field")
    public void checkEnabledUsePreEstimatedDeliveryFeeField() {
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, COURIER_SERVICE_TYPE, true);
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre estimated Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeOn(DELIVERY, USE_PRE_ESTIMATED_DELIVERY_FEE);

        logStep(3, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(4, "Check reaction in 'Tariff' table");
        costCalcPageSteps.fieldShouldHaveReaction(SUCCESS);
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, COURIER_SERVICE_TYPE, false);
    }

    @Test
    @Description("Customer Tariffs page: Check turned off 'Use pre estimated Delivery Fee' field")
    public void checkTurnedOffUsePreEstimatedDeliveryFeeField() {
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, COURIER_SERVICE_TYPE, false);
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre estimated Delivery Fee' field is displayed");
        customerTariffsPageSteps.fieldShouldBeOff(DELIVERY, USE_PRE_ESTIMATED_DELIVERY_FEE);

        logStep(3, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logStep(4, "Check reaction in 'Tariff' table");
        costCalcPageSteps.fieldShouldHaveReaction(FAILURE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Free cancellation time' field")
    public void checkTheFreeCancellationTimeField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Free cancellation time' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(CANCELLATION, FREE_TIME_CANCELLATION, ONE);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Cancellation penalty' field")
    public void checkTheCancellationPenaltyField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Cancellation penalty' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(CANCELLATION, PENALTY, COST_TEN);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Driver Service Fee Rate (Percent)' field")
    public void checkTheDriverServiceFeeRateField() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Driver Service Fee Rate (Percent)' field is displayed");
        customerTariffsPageSteps.fieldShouldBeVisibleAndHaveCost(DELIVERY, DRIVER_SERVICE_FEE, ZERO);
    }

    @Test
    @Description("Customer Tariffs page: Check the 'Use pre-auth payment' field - PA is off")
    public void checkTheUsePreAuthPaymentFieldIsOff() {
        createOrderAndGoToCustomerTariffsTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Input operation area in 'Operation area' field");
        customerTariffsPageSteps.inputOperationArea(KHARKIV);

        logStep(2, "Check that the 'Use pre-auth payment' field is displayed and off");
        customerTariffsPageSteps.fieldShouldBeOff(PRE_AUTHORIZATION_PAYMENT, USE_PRE_AUTHORIZATION_PAYMENT);

        logStep(3, "Check that 'Delivery Fee' and 'Basket Cost' fields are not required");
        customerTariffsPageSteps.fieldShouldNotBeRequired(PRE_AUTHORIZATION_PAYMENT, DELIVERY_FEE);
        customerTariffsPageSteps.fieldShouldNotBeRequired(PRE_AUTHORIZATION_PAYMENT, BASKET_COST);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
