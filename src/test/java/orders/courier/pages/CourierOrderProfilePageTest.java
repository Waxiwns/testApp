// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.constants.ApiConstants;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.modalPages.DialogModal.CANCEL_ORDER;
import static pages.orderPageTabs.OrderCostCalculationPage.VALUE;
import static pages.orderPageTabs.OrderHeaderPage.CUSTOMERS;
import static pages.orderPageTabs.OrderHeaderPage.DRIVERS;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.ApiConstants.PAYMENT_TYPE;
import static utils.constants.Buttons.*;
import static utils.constants.PageIdentifiers.*;
import static utils.constants.Statuses.*;

public class CourierOrderProfilePageTest extends BaseTest {

    private String cancelReason = "Test cancel reason";

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

    public void createOrderAndGoToProfilePage(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(7, "Check order profile page is active");
        orderHeaderPageSteps.isActiveTab(PROFILE);
    }

    @Test
    @Description("Courier order 'Customer' section: Check customer information displaying at 'Customer' section")
    public void checkCustomerInformationDisplayingAtCustomerSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check customer section parameters");
        log("Check customer full name is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.NAME, firstCustomer.getFullName());
        log("Check customer phone is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.PHONE, firstCustomer.getPhone());
    }

    @Test
    @Description("Courier order 'Customer' section: Check that 'Details' button at 'Customer' section leads to Customer Profile page")
    public void checkThatDetailsButtonAtCustomerSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check 'Details' button");
        orderProfilePageSteps.sectionButtonClick(CUSTOMER, DETAILS);
        customerDetailsPageSteps.pageTitleShouldBeVisible();
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Courier order 'Finances' section: Check the correct 'Promo code' is displayed, if applied")
    public void checkTheCorrectPromoCodeIsDisplayed() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCPromoOrderByFirstCustomer(testInitValues.promoCourierDeliveryValue()));

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Promo code' field is displayed promo code 'COURIER'");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PROMO_CODE, testInitValues.promoCourierDeliveryValue());
    }

    @Test
    @Description("Courier order 'Finances' section: Check the correct 'COURIERSERVICEPERCENTDISCOUNT' is displayed, if applied")
    public void checkTheCorrectPromoCodeServiceValueIsDisplayed() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierServicePercentDiscount()));
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
        refresh();

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Promo code' field is displayed promo code 'COURIERSERVICEPERCENTDISCOUNT'");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PROMO_CODE, testInitValues.promoCourierServicePercentDiscount());
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(3, "Discount field should be displayed");
        orderProfilePageSteps.discountShouldBeDisplayed(FINANCES, DISCOUNT, order.getServiceFee());
    }

    //Doesn't check 'Cancellation Fee' field
    @Test
    @Description("Courier order 'Finances' section: Check correct order prices displaying at 'Finances' section (Basket, Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkCorrectOrderPricesDisplaying() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierServiceDiscount()));
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
        refresh();

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Basket' field is displayed");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, BASKET, PRICE_0_AND_00_SAR);
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(3, "Check that 'Service Fee' field is displayed");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, SERVICE_FEE, order.getServiceFee());

        logStep(4, "Check that 'Delivery fee' field is displayed");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, order.getDeliveryCost());

        logStep(5, "Check that 'Total' field is displayed");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, order.getTotalCost());
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(6, "Check that 'Discount' field is displayed");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PROMO_CODE, testInitValues.promoCourierServiceDiscount());
    }

    @Test
    @Description("Courier order 'Finances' section: Check that changes, which applied on 'Cost Calculation' tab is displayed")
    public void checkThatCostChangesAreDisplayed() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Delivery fee' field is displayed delivery price");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, order.getDeliveryFee());

        logStep(3, "Click on 'Cost Calculation' tab and check redirect to this page");
        orderHeaderPageSteps.clickOnTab(orderId, COSTCALCULATION);
        orderHeaderPageSteps.checkOpenedTab(orderId, COSTCALCULATION);

        logStep(4, "Input new 'Delivery fee'");
        costCalcPageSteps.clickOnDropdownMenu();
        costCalcPageSteps.chooseParameterDropdownMenu(VALUE);
        costCalcPageSteps.inputNewCostDeliveryFee(NEW_DELIVERY_FEE);
        costCalcPageSteps.clickOnSaveBtn();

        logStep(5, "Check that success message appears");
        notificationModalPageSteps.costCorrectionsUpdatedMessageShouldBeDisplayed();

        logStep(6, "Click on 'Profile' tab and check redirect to this page");
        orderHeaderPageSteps.clickOnTab(orderId, PROFILE);
        orderHeaderPageSteps.checkOpenedTab(orderId, PROFILE);

        logStep(7, "Check that 'Delivery fee' field is displayed changed delivery price");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, NEW_DELIVERY_FEE);
    }

    @Test
    @Description("Courier order 'Finances' section: Check correct payment method displaying at 'Finances' section")
    public void checkCorrectPaymentMethodAtFinancesSection() {
        logStep(1, "Create order with 'CASH' payment method");
        createOrderAndGoToProfilePage(useAPISteps.createCourierCashOrderByFirstCustomer());
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);


        logStep(3, "Check that payment method displaying 'CASH'");
        order = useAPISteps.updateOrderDetails(orderId);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PAYMENT, order.getKey(PAYMENT_TYPE));

        logStep(4, "Cancel order");
        useAPISteps.cancelOrder(order);

        logStep(5, "Create order with 'Credit card' payment method");
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(6, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(7, "Check that payment method displaying 'CASH'");
        order = useAPISteps.updateOrderDetails(orderId);
        orderProfilePageSteps.paymentMethodShouldBe(order.getKey(PAYMENT_TYPE));
    }

    @Test
    @Description("Courier order 'Finances' section: Check that 'Details' button at 'Finances' section leads to Cost Calculation tab")
    public void checkThatDetailsBtnLeadsTOCostCalculationTab() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Click on 'Details' button");
        orderProfilePageSteps.sectionButtonClick(FINANCES, DETAILS);

        logStep(3, "Check that Cost Calculation tab is opened");
        orderHeaderPageSteps.checkOpenedTab(orderId, COSTCALCULATION);
    }

    @Test
    @Description("Courier order 'Finances' section: Check that prices which are not defined for now is displayed as 'Not available yet' instead of zero")
    public void checkThatPricesCorrectDisplayedNotAvailableYet() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Basket' field is displayed as 'Not available yet' instead of zero");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, BASKET, NOT_AVAILABLE_YET);
    }

    @Test
    @Description("Courier order 'Finances' section: Check that VAT line is displayed correct VAT value")
    public void checkThatVatLineIsDisplayedCorrectVatValue() {
        createOrderAndGoToProfilePage(useAPISteps.createCompletedCourierOrderBySecondDriver());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'VAT' field is displayed correct VAT value");
        orderProfilePageSteps.sectionVatShouldHaveCorrectCost(order.getVAT());
    }

    @Test
    @Description("Courier order 'Order Tracking' section: Check that the new order goes into the 'NEW' section in Order Tracking")
    public void checkNewSectionOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check that Order Tracking is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER_TRACKING);

        logStep(2, "Check that 'NEW' Order Tracking block is active");
        orderProfilePageSteps.orderTrackingBlockShouldBeActive(NEW);
    }

    @Test
    @Description("Courier order 'Order Tracking' section: Check that the new order goes into the 'SUSPENDED' section in Order Tracking")
    public void checkSuspendedSectionOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(3, "Go to the order description page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logStep(4, "Check that 'SUSPENDED' Order Tracking block is active");
        orderProfilePageSteps.orderTrackingBlockShouldBeActive(SUSPENDED);
    }

    @Test
    @Description("Courier order 'Action' section: Check that 'Cancel order' popup is displayed after click 'Cancel' button")
    public void checkThatCancelOrderPopupIsDisplayedAfterClickCancelButton() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check Action section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ACTIONS);

        logStep(2, "Click 'To support' button");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check 'Cancel order' popup is displayed");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, CANCEL_lower);
        dialogModalSteps.cancelOrderTitleShouldBeVisible();
    }

    @Test
    @Description("Courier order 'Action' section: Check 'Cancel' button")
    public void checkCancelButton() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check Action section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ACTIONS);

        logStep(2, "Click 'To support' button");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check 'Cancel order' popup is displayed");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, CANCEL_lower);
        dialogModalSteps.cancelOrderTitleShouldBeVisible();

        logStep(4, "Check 'Reason' field");
        dialogModalSteps.cancelOrderReasonTitleShouldBeVisible();
        dialogModalSteps.cancelOrderSaveBtnShouldBeDisabled();

        logStep(5, "Fill reason");
        dialogModalSteps.fillCancelReason(cancelReason);
        dialogModalSteps.cancelOrderSaveBtnShouldBeEnabled();

        logStep(6, "Check 'Cancel' button");
        dialogModalSteps.clickFooterButton(CANCEL_ORDER, CANCEL);
        dialogModalSteps.modalShouldNotBeVisible1(CANCEL_ORDER);
    }

    @Test
    @Description("Courier order 'Action' section: Check that entered reason is displayed as cancellation reason on 'Orders' section")
    public void checkThatEnteredReasonIsDisplayedAsCancellationReason() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check Action section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ACTIONS);

        logStep(2, "Click 'To support' button");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check 'Cancel order' popup is displayed");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, CANCEL_lower);
        dialogModalSteps.cancelOrderTitleShouldBeVisible();

        logStep(4, "Check 'Reason' field");
        dialogModalSteps.cancelOrderReasonTitleShouldBeVisible();
        dialogModalSteps.cancelOrderSaveBtnShouldBeDisabled();
        logStep(5, "Fill reason");
        dialogModalSteps.fillCancelReason(cancelReason);
        dialogModalSteps.cancelOrderSaveBtnShouldBeEnabled();

        logStep(6, "Check 'Save' button");
        dialogModalSteps.clickFooterButton(CANCEL_ORDER, SAVE);
        dialogModalSteps.modalShouldNotBeVisible1(CANCEL_ORDER);

        logStep(7, "Check order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, CANCELLED.getEnValue());
        order = useAPISteps.updateOrderDetails(orderId);
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, order.getKey(ApiConstants.STATUS));

        logStep(8, "Check entered reason in 'Order' section 'Status' parameter tooltip");
        orderProfilePageSteps.sectionParameterTooltipShouldHaveText(ORDER, OrderProfilePage.STATUS, cancelReason);
    }

    @Test
    @Description("Courier order 'Contact' section: Check 'Chat' button functionality at 'Customer' field of 'Contact' section ")
    public void checkCustomerChatBtnFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, CUSTOMER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, CUSTOMERS);
    }

    @Test
    @Description("Courier order 'Contact' section: Check 'Call' button functionality at 'Customer' field of 'Contact' section")
    public void checkCustomerCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, CUSTOMER_PARAMETER, CALL);
    }

    @Test
    @Description("Courier order 'Contact' section: Check 'Chat' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverChatButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierOrderAndAcceptByDriver(secondDriver));

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, DRIVER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, DRIVERS);
    }

    @Test
    @Description("Courier order 'Contact' section: Check 'Call' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierOrderAndAcceptByDriver(secondDriver));

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, DRIVER_PARAMETER, CALL);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
