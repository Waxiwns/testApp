// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.mdb.pages;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static io.restassured.path.json.JsonPath.from;
import static pages.modalPages.DialogModal.CANCEL_ORDER;
import static pages.orderPageTabs.OrderHeaderPage.*;
import static pages.orderPageTabs.OrderProfilePage.CAR;
import static pages.orderPageTabs.OrderProfilePage.DELIVERY_FEE;
import static pages.orderPageTabs.OrderProfilePage.SERVICE_FEE;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.ApiConstants.DROP_OFF;
import static utils.constants.ApiConstants.PHONE;
import static utils.constants.ApiConstants.*;
import static utils.constants.Buttons.TO_SUPPORT;
import static utils.constants.Buttons.*;
import static utils.constants.PageIdentifiers.*;
import static utils.constants.Statuses.*;

@Log4j
public class mdbProfilePageTest extends BaseTest {

    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create mdb new order");
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
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
    @Description("Customer section: Check Section")
    public void CheckCustomerSectionName() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());
        logStep(1, "Check Section name: Customer(Cashier) for MDB orders");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER_CASHIER);

        logStep(2, "Check Check that Cashier profile name is displayed");
        String fullName = merchant.getMerchantUserInfo(testInitValues.firstMerchantFirstUserId(), FIRST_NAME) + " " + merchant.getMerchantUserInfo(testInitValues.firstMerchantFirstUserId(), LAST_NAME);
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER_CASHIER, OrderProfilePage.NAME, fullName);

        logStep(3, "Check Check Cashier phone displayed");
        String phone = merchant.getMerchantUserInfo(testInitValues.firstMerchantFirstUserId(), PHONE);
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER_CASHIER, OrderProfilePage.PHONE, phone);

        logStep(4, "Check that 'Details button' at 'Customer(Cashier) section leads to Cashier profile page");
        orderProfilePageSteps.sectionButtonClick(CUSTOMER_CASHIER, DETAILS);
        merchantUserDetailsPageSteps.pageTitleShouldBeVisible(merchant.getName());
        merchantUserDetailsPageSteps.customerFirstNameEnShouldHaveText(merchant.getMerchantUserInfo(testInitValues.firstMerchantFirstUserId(), FIRST_NAME));
        merchantUserDetailsPageSteps.customerLastNameEnShouldHaveText(merchant.getMerchantUserInfo(testInitValues.firstMerchantFirstUserId(), LAST_NAME));
    }

    @Test
    @Description("Driver section: Check driver information displaying at 'Driver' section (Name, Phone, Car)")
    public void checkDriverInformationInDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(1, "Get driver information");
        String fullName = firstDriver.getFullName();
        String car = firstDriver.getCar();

        logStep(2, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(3, "Check Driver section");
        log("Check the driver name field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.NAME, fullName);
        log("Check the driver phone field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.PHONE, firstDriver.getPhone());
        log("Check the driver car field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);
    }

    @Test
    @Description("Driver section: Check that 'Details' button at 'Driver' section leads to Driver Profile page in English")
    public void checkDetailsButtonInDriverSectionEn() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());
        logStep(1, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Click button details");
        orderProfilePageSteps.sectionButtonClick(DRIVER, DETAILS);

        logStep(3, "Check that information on driver page is correct");
        driverDetailsSteps.driverFirstNameEnShouldHaveText(firstDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(firstDriver.getLastName());
    }

    @Test
    @Description("Driver section: Check 'Analyze' button functionality at 'Driver' section")
    public void checkAnalyzeButton() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());
        logStep(1, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check 'Analyze' button functionality at 'Driver' section");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);

        logStep(3, "Check the 'Driver analyse' page");
        driverAnalysePageSteps.idInputFieldShouldBeVisible();
    }

    @Test
    @Description("Driver section: Check the 'Driver analyse' page")
    public void checkAnalyzeButtonPage() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());
        logStep(1, "Check 'Analyze' button functionality at 'Driver' section");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);

        logStep(2, "Driver field should be visible");
        driverAnalysePageSteps.idInputFieldShouldBeVisible();

        logStep(3, "Click to analise button");
        driverAnalysePageSteps.getButtonClick();
        sleep(2000);

        logStep(4, "Check result block and them content");
        driverAnalysePageSteps.resultBlockShouldHaveText(testInitValues.firstDriverId());
    }

    @Test
    @Description("Driver section: Check 'Change Order Driver' button functionality")
    public void checkChangeOrderButtonInDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Send driver to support to can assign driver for order");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(3, "Check change button is display and not disabled");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);

        logStep(4, "Check that after clicking the button we can see the model map");
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.modalMapCloseButtonClick();
    }


    @Test
    @Description("Finances section: Check that 'Details' button at 'Finances' section leads to Cost Calculation tab")
    public void checkDetailsButtonInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check the visibility of the button and press it");
        orderProfilePageSteps.sectionButtonClick(FINANCES, DETAILS);

        logStep(3, "Check current url");
        orderProfilePageSteps.checkPageUrl(orderId, COSTCALCULATION.getValue());
    }

    @Test
    @Description("Finances section: Check correct payment method displaying at 'Finances' section")
    public void checkPaymentMethodInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check payment method is correct");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.PAYMENT, OrderProfilePage.CASH);
    }

    @Test
    @Description("Finances section: Check correct order prices displaying at 'Finances' section (Basket, Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());
        JsonPath result = from(admin.getTariffCalculator(orderId));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that basket values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, BASKET, result.getString("shown.basketCost.originalValue"));

        logStep(3, "Check that service fee values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, SERVICE_FEE, result.getString("shown.serviceFee.originalValue"));

        logStep(4, "Check that delivery fee values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, result.getString("shown.deliveryFee.originalValue"));

        logStep(5, "Check that total cost values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.TOTAL_COST, result.getString("shown.totalCosts.originalValue"));

        logStep(6, "Check that payment values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.PAYMENT, OrderProfilePage.CASH);
    }

    @Test
    @Description("Finances section: Check correct order prices displaying at 'Finances' section (Basket, Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkFinancesSectionForCanceledOrder() {
        order = useAPISteps.createMdbOrderWithKharkivPos();
        useAPISteps.cancelOrder(order);
        createOrderAndGoToProfilePage(order);

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that cancellation fee values is " + PRICE_0_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, CANCELLATION_FEE, PRICE_0_AND_00_SAR);

        logStep(3, "Check that total cost values is " + PRICE_0_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.TOTAL_COST, PRICE_0_AND_00_SAR);

        logStep(4, "Check that payment values is " + OrderProfilePage.CASH);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.PAYMENT, OrderProfilePage.CASH);
    }

    @Test
    @Description("Change Payment Method pop-up: Check change Payment Method pop-up")
    public void checkChangePaymentPopUp() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Check that payment values is " + OrderProfilePage.CASH);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.PAYMENT, OrderProfilePage.CASH);

        logStep(3, "Check 'Change Payment' button appears if get order to support and click it");
        orderProfilePageSteps.sectionButtonClick(FINANCES, CHANGE);

        logStep(4, "Check 'Change payment type for order' pop-up appears if click 'Change Payment' button");
        orderProfilePageSteps.paymentPopUpTitleShouldBeVisible(CHANGE_PAYMENT_TYPE_FOR_ORDER_TITLE);

        logStep(5, "Check 'Payment method' drop-down functionality");
        log("Set the CASH value and make sure it was selected");
        orderProfilePageSteps.paymentMethodShouldHaveValue(EMPTY_VALUE);
        orderProfilePageSteps.setPaymentMethod(OrderProfilePage.CASH);
        orderProfilePageSteps.paymentMethodShouldHaveValue(OrderProfilePage.CASH);
        log("Set the NO_SETTLEMENTS value and make sure it was selected");
        orderProfilePageSteps.setPaymentMethod(NO_SETTLEMENTS);
        orderProfilePageSteps.paymentMethodShouldHaveValue(NO_SETTLEMENTS);

        logStep(6, "Check 'Save' button functionality");
        orderProfilePageSteps.paymentPopUpSaveButtonClick();

        logStep(7, "Check the correct Payment method is set within the Order after changing it");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, OrderProfilePage.PAYMENT, NO_SETTLEMENTS);

        logStep(8, "Check 'Cross' and 'Cancel' buttons functionality");
        log("Check Cancel button functionality");
        orderProfilePageSteps.sectionButtonClick(FINANCES, CHANGE);
        orderProfilePageSteps.paymentPopUpTitleShouldBeVisible(CHANGE_PAYMENT_TYPE_FOR_ORDER_TITLE);
        orderProfilePageSteps.paymentPopUpCancelButtonClick();
        orderProfilePageSteps.paymentPopUpTitleShouldNotBeVisible();
        log("Check Cross button functionality");
        orderProfilePageSteps.sectionButtonClick(FINANCES, CHANGE);
        orderProfilePageSteps.paymentPopUpTitleShouldBeVisible(CHANGE_PAYMENT_TYPE_FOR_ORDER_TITLE);
        orderProfilePageSteps.paymentPopUpCrossButtonClick();
        orderProfilePageSteps.paymentPopUpTitleShouldNotBeVisible();
    }

    @Test
    @Description("Contact section: Check 'Chat' button functionality at 'Customer(DROP_OFF)' field of 'Contact' section ")
    public void checkCustomerChatBtnFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, CUSTOMER_PARAMETER_DROP_OFF, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, CUSTOMERS);
    }

    @Test
    @Description("Contact section: Check 'Call' button functionality at 'Customer(DROP_OFF)' field of 'Contact' section")
    public void checkCustomerCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, CUSTOMER_PARAMETER_DROP_OFF, CALL);
    }

    @Test
    @Description("Contact section: Check 'Chat' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverChatButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, DRIVER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, DRIVERS);
    }

    @Test
    @Description("Contact section: Check 'Call' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, DRIVER_PARAMETER, CALL);
    }


    @Test
    @Description("Contact section: Check 'Chat' button functionality at 'Customer(CASHIER)' field of 'Contact' section")
    public void checkCashierChatButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, CUSTOMER_PARAMETER_CASHIER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, MERCHANTS);
    }

    @Test
    @Description("Contact section: Check 'Call' button functionality at 'Customer(CASHIER)' field of 'Contact' section")
    public void checkCashierCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, CUSTOMER_PARAMETER_CASHIER, CALL);
    }

    @Test
    @Description("Actions section: Check possibility to restart order from 'To support'")
    public void checkPossibilityToRestartOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Move order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(3, "Check possibility to set 'To support' flag");
        orderProfilePageSteps.sectionParameterShouldBeVisible(ORDER, OrderProfilePage.TO_SUPPORT);

        logStep(4, "Restart order");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, RESTART_ORDER);

        logStep(5, "Check that section parameter To_support is not visible");
        refresh();
        orderProfilePageSteps.sectionParameterShouldNotBeVisible(ORDER, OrderProfilePage.TO_SUPPORT);
    }

    @Test
    @Description("Actions section: Check that 'Actions' buttons are not displayed after completing order.")
    public void checkActionButtonAreNotDisplayedAfterCompleting() {
        createOrderAndGoToProfilePage(useAPISteps.createCompletedMdbOrderByFirstDriver());

        logStep(1, "Check that buttons are not displayed");
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, TO_SUPPORT);
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, RESTART_ORDER);
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, CANCEL_lower);
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, Buttons.COMPLETE);
    }

    @Test
    @Description("Actions section: Check available action buttons functionality")
    public void checkCancelOrderButton() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Order status must be READY_TO_EXECUTE");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, READY_TO_EXECUTE.getEnValue());

        logStep(2, "Move order to support and try to cancel order");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);
        orderProfilePageSteps.sectionButtonClick(ACTIONS, CANCEL_lower);

        logStep(3, "Cancel order");
        dialogModalSteps.fillCancelReason("Test");
        dialogModalSteps.cancelOrderSaveBtnShouldBeEnabled();
        dialogModalSteps.clickFooterButton(CANCEL_ORDER, SAVE);

        logStep(4, "Check that modal window and cancel  button are not visible");
        dialogModalSteps.modalShouldNotBeVisible1(CANCEL_ORDER);
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, CANCEL_lower);

        logStep(5, "Order status must be CANCELLED");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.STATUS, CANCELLED.getEnValue());
    }


    @Test
    @Description("Actions section: Check that 'Complete' button is displayed only if order is in 'To Support' state when the Driver is at drop-off point")
    public void checkThatCompleteButtonIsDisplay() {
        logStep(1, "Create order and move to support");
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());

        logStep(2, "Check completed button is not visible for ON_THE_WAY_TO_PICK_UP status");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, Buttons.COMPLETE);
        orderProfilePageSteps.sectionButtonClick(ACTIONS, RESTART_ORDER);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldBeVisible(ACTIONS, TO_SUPPORT);
        firstDriver.updateActiveOrderStatus(AT_PICK_UP.getValue());
        refresh();

        logStep(3, "Check completed button is not visible for AT_PICK_UP status");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, Buttons.COMPLETE);
        orderProfilePageSteps.sectionButtonClick(ACTIONS, RESTART_ORDER);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldBeVisible(ACTIONS, TO_SUPPORT);
        firstDriver.updateActiveOrderStatus(ON_THE_WAY_TO_DROP_OFF.getValue());
        refresh();

        logStep(4, "Check completed button is not visible for ON_THE_WAY_TO_DROP_OFF status");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldNotBeVisible(ACTIONS, Buttons.COMPLETE);
        orderProfilePageSteps.sectionButtonClick(ACTIONS, RESTART_ORDER);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonShouldBeVisible(ACTIONS, TO_SUPPORT);
        firstDriver.updateLocation(order.getAddress(DROP_OFF));
        firstDriver.updateActiveOrderStatus(AT_DROP_OFF.getValue());
        refresh();

        logStep(5, "Check completed button is visible for AT_DROP_OFF status");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);
        orderProfilePageSteps.sectionButtonShouldBeVisible(ACTIONS, Buttons.COMPLETE);
    }
}