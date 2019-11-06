// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.taxi.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.constants.ApiConstants;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static pages.modalPages.DialogModal.CANCEL_ORDER;
import static pages.orderPageTabs.OrderCostCalculationPage.VALUE;
import static pages.orderPageTabs.OrderHeaderPage.CUSTOMERS;
import static pages.orderPageTabs.OrderHeaderPage.DRIVERS;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.Buttons.*;
import static utils.constants.PageIdentifiers.*;
import static utils.constants.Statuses.CANCELLED;
import static utils.constants.Statuses.NEW;

public class TaxiOrderProfilePageTest extends BaseTest {

    private String cancelReason = "Test cancel reason";

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();
    }

    public void createOrderAndGoToProfilePage(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(4, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logPreconditionStep(5, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(6, "Check order profile page is active");
        orderHeaderPageSteps.isActiveTab(PROFILE);
    }

    @Test
    @Description("Taxi order 'Customer' section: Check customer information displaying at 'Customer' section")
    public void checkCustomerInformationDisplayingAtCustomerSection() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check customer section parameters");
        log("Check customer full name is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.NAME, firstCustomer.getFullName());
        log("Check customer phone is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.PHONE, firstCustomer.getPhone());
    }

    @Test
    @Description("Taxi order 'Customer' section: Check that 'Details' button at 'Customer' section leads to Customer Profile page")
    public void checkThatDetailsButtonAtCustomerSection() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check 'Details' button");
        orderProfilePageSteps.sectionButtonClick(CUSTOMER, DETAILS);
        customerDetailsPageSteps.pageTitleShouldBeVisible();
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Taxi order 'Finances' section: Check the correct 'Promo code' is displayed, if applied")
    public void checkTheCorrectPromoCodeIsDisplayed() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Promo code' field is displayed promo code 'SMOKETAXI'");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PROMO_CODE, SMOKETAXI);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check correct order prices displaying at 'Finances' section (Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkCorrectOrderPricesDisplaying() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Delivery fee' field is displayed delivery price");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, DELIVERY_FEE_COST_TAXI);

        logStep(3, "Check that 'Total Cost' field is displayed delivery price");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, TOTAL_COST_PARAMETER_TAXI);

        logStep(4, "Check that 'Total' field is displayed delivery price");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL, TOTAL_PARAMETER_TAXI);

        logStep(5, "Check that 'Total' field is displayed delivery price");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, VAT, VAT_COST_TAXI);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check that changes, which applied on 'Cost Calculation' tab is displayed")
    public void checkThatCostChangesAreDisplayed() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Click on 'Cost Calculation' tab and check redirect to this page");
        orderHeaderPageSteps.clickOnTab(orderId, COSTCALCULATION);
        orderHeaderPageSteps.checkOpenedTab(orderId, COSTCALCULATION);

        logStep(3, "Input new 'Delivery fee'");
        costCalcPageSteps.clickOnDropdownMenu();
        costCalcPageSteps.chooseParameterDropdownMenu(VALUE);
        costCalcPageSteps.inputNewCostDeliveryFee(NEW_DELIVERY_FEE);
        costCalcPageSteps.clickOnSaveBtn();

        logStep(4, "Check that success message appears");
        notificationModalPageSteps.costCorrectionsUpdatedMessageShouldBeDisplayed();

        logStep(5, "Click on 'Profile' tab and check redirect to this page");
        orderHeaderPageSteps.clickOnTab(orderId, PROFILE);
        orderHeaderPageSteps.checkOpenedTab(orderId, PROFILE);

        logStep(6, "Check that 'Delivery fee' field is displayed changed delivery price");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, NEW_DELIVERY_FEE);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check correct payment method displaying at 'Finances' section")
    public void checkCorrectPaymentMethodAtFinancesSection() {

        logStep(1, "Create order with 'Credit card' payment method");
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(2, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(3, "Check that payment method displaying 'CC'");
        orderProfilePageSteps.paymentMethodShouldBe(CREDIT_CARD);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check that 'Details' button at 'Finances' section leads to Cost Calculation tab")
    public void checkThatDetailsBtnLeadsTOCostCalculationTab() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Click on 'Details' button");
        orderProfilePageSteps.sectionButtonClick(FINANCES, DETAILS);

        logStep(3, "Check that Cost Calculation tab is opened");
        orderHeaderPageSteps.checkOpenedTab(orderId, COSTCALCULATION);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check that prices which are not defined for now is displayed as 'Not available yet' instead of zero")
    public void checkThatPricesCorrectDisplayedNotAvailableYet() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderForHomeless());
        sleep(2000);

        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that 'Total Cost' field is displayed as 'Not available yet' instead of zero");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, NOT_AVAILABLE_YET);
    }

    @Test
    @Description("Taxi order 'Finances' section: Check that VAT line is displayed correct VAT value")
    public void checkThatVatLineIsDisplayedCorrectVatValue() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());
        logStep(1, "Check that finances section is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);
        logStep(2, "Check that 'VAT' field is displayed correct VAT value");
        orderProfilePageSteps.sectionVatShouldHaveCorrectCost(VAT_COST_TAXI);
    }

    @Test
    @Description("Taxi order 'Order Tracking' section: Check that the new order goes into the 'NEW' section in Order Tracking")
    public void checkNewSectionOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check that Order Tracking is displayed");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER_TRACKING);

        logStep(2, "Check that 'NEW' Order Tracking block is active");
        orderProfilePageSteps.orderTrackingBlockShouldBeActive(NEW);
    }

    @Test
    @Description("Taxi order 'Action' section: Check that 'Cancel order' popup is displayed after click 'Cancel' button")
    public void checkThatCancelOrderPopupIsDisplayedAfterClickCancelButton() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

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
    @Description("Taxi order 'Action' section: Check 'Cancel' button")
    public void checkCancelButton() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

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
    @Description("Taxi order 'Action' section: Check that entered reason is displayed as cancellation reason on 'Orders' section")
    public void checkThatEnteredReasonIsDisplayedAsCancellationReason() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

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
        order = useAPISteps.updateOrderDetails(orderId);
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, CANCELLED.getEnValue());
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, order.getKey(ApiConstants.STATUS));

        logStep(8, "Check entered reason in 'Order' section 'Status' parameter tooltip");
        orderProfilePageSteps.sectionParameterTooltipShouldHaveText(ORDER, OrderProfilePage.STATUS, cancelReason);
    }

    @Test
    @Description("Taxi order 'Contact' section: Check 'Chat' button functionality at 'Customer' field of 'Contact' section ")
    public void checkCustomerChatBtnFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(firstDriver));

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, CUSTOMER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, CUSTOMERS);
    }

    @Test
    @Description("Taxi order 'Contact' section: Check 'Call' button functionality at 'Customer' field of 'Contact' section")
    public void checkCustomerCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(firstDriver));

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, CUSTOMER_PARAMETER, CALL);
    }

    @Test
    @Description("Taxi order 'Contact' section: Check 'Chat' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverChatButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(firstDriver));

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, DRIVER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, DRIVERS);
    }

    @Test
    @Description("Taxi order 'Contact' section: Check 'Call' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(firstDriver));

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, DRIVER_PARAMETER, CALL);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
