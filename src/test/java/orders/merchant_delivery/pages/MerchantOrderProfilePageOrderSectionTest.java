// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.constants.ApiConstants;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.modalPages.DialogModal.CHANGE_DELIVERY_DATE_TITLE;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.Buttons.CHANGE;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.Statuses.*;

@Log4j
public class MerchantOrderProfilePageOrderSectionTest extends BaseTest {
    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open Login page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create merchant new order");
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
    @Description("Order section:Check the correct order status displaying at Order section")
    public void checkOrderStatusAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check WAIT_MERCHANT_APPROVAL order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, WAIT_MERCHANT_APPROVAL.getEnValue());

        logStep(3, "Check READY_TO_EXECUTE order status");
        firstDriver.checkActiveOrderAndCancelIt();
        firstDriver.updateLocation(locationKharkivPushkinska1);
        merchant.acceptOrderById(orderId);
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());

        logStep(4, "Check ON_THE_WAY_TO_PICK_UP order status");
        firstDriver.acceptOrder();
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, ON_THE_WAY_TO_PICK_UP.getEnValue());

        logStep(5, "Check AT_PICK_UP order status");
        firstDriver.updateActiveOrderStatus(AT_PICK_UP.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, AT_PICK_UP.getEnValue());

        logStep(6, "Check ON_THE_WAY_TO_DROP_OFF order status");
        firstDriver.updateActiveOrderStatus(ON_THE_WAY_TO_DROP_OFF.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, ON_THE_WAY_TO_DROP_OFF.getEnValue());

        logStep(7, "Check AT_DROP_OFF order status");
        firstDriver.updateActiveOrderStatus(AT_DROP_OFF.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, AT_DROP_OFF.getEnValue());

        logStep(8, "Check COMPLETED order status");
        firstDriver.completeOrder();
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, COMPLETED.getEnValue());
    }

    @Test
    @Description("Order section:Check the correct Merchant status displaying for merchant delivery orders")
    public void checkMerchantStatusAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check WAIT_ACCEPTANCE Merchant status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, WAIT_ACCEPTANCE.getEnValue());

        logStep(3, "Check IN_PROGRESS Merchant status");
        firstDriver.checkActiveOrderAndCancelIt();
        firstDriver.updateLocation(locationKharkivPushkinska1);
        merchant.acceptOrderById(orderId);
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, IN_PROGRESS.getEnValue());

        logStep(4, "Check IN_PROGRESS Merchant status");
        firstDriver.acceptOrder();
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, IN_PROGRESS.getEnValue());

        logStep(5, "Check IN_PROGRESS Merchant status");
        firstDriver.updateActiveOrderStatus(AT_PICK_UP.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, IN_PROGRESS.getEnValue());

        logStep(6, "Check TAKEN Merchant status");
        firstDriver.updateActiveOrderStatus(ON_THE_WAY_TO_DROP_OFF.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, TAKEN.getEnValue());

        logStep(7, "Check TAKEN Merchant status");
        firstDriver.updateActiveOrderStatus(AT_DROP_OFF.getValue());
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, TAKEN.getEnValue());

        logStep(8, "Check COMPLETED Merchant status");
        firstDriver.completeOrder();
        refresh();
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, COMPLETED.getEnValue());
    }

    @Test
    @Description("Order section:Check the correct Service Group displaying at Order section")
    public void checkServiceGroupAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Service Group");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, SERVICE_GROUP, order.getServiceGroup());
    }

    @Test
    @Description("Order section:Check Required vehicle type is displayed at Order section")
    public void checkRequiredVehicleType() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Required vehicle type");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_VEHICLE_TYPE, ApiConstants.CAR);
    }

    @Test
    @Description("Order section:Check Created time is correct and displayed at Order section")
    public void checkCreatedTime() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        String createDate = order.getCreationDate();

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Created time");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, createDate);
    }

    @Test
    @Description("Order section:Check Required delivery time is displayed at Order section")
    public void checkRequiredDeliveryTime() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Required delivery time");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
    }

    @Test
    @Description("Order section:Check Finished time is displayed at Order section for completed/cancelled orders")
    public void checkFinishedTime() {
        order = useAPISteps.createRegularOrder(EMPTY_VALUE);
        useAPISteps.cancelOrder(order);
        order = useAPISteps.updateOrderDetails(order.getId());
        createOrderAndGoToProfilePage(order);

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Finished time");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.FINISHED_TIME, order.getFinishedDate());
    }

    @Test
    @Description("Order section:Check that Driver rating for this order is displayed correctly at Order section")
    public void checkDriverRating() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        String rating = "0";

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section order Driver rating");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, DRIVER_RATING_FOR_THIS_ORDER, rating);
    }

    @Test
    @Description("Check the ASAP label is NO for suspended orders")
    public void checkAsapForSuspendedOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendedRegularOrder());
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section ASAP is NO");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);
    }

    @Test
    @Description("Check the ASAP label is YES for Ride/delivery now orders and Check Surprise marker is displayed for surprise orders at Order section")
    public void checkAsapWithYesAndSurpriseField() {
        createOrderAndGoToProfilePage(useAPISteps.createSurprisedRegularOrder());
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section ASAP is YES");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);

        logStep(3, "Check order section Surprise");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, SURPRISE, EMPTY_VALUE);
    }

    @Bug
    @Issue("TPD-1677")
    @Test
    @Description("Check that ASAP mark changes to Yes after setting up Delivery time to Now for Ride later order")
    public void checkChangeAsapFromNoToYesEN() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendedRegularOrder());
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section ASAP is NO");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);

        logStep(3, "Change time to now");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.setNowDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(4, "Check order section ASAP is YES");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);
    }

    @Test
    @Description("Check that 'ASAP' mark changes to 'No' after setting up 'Delivery time' in future for 'Ride Now' order")
    public void checkChangeAsapFromYesToNoEN() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check order section ASAP is YES");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);

        logStep(3, "Change time to future");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(4, "Check order section ASAP is NO");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);
    }

    @Test
    @Description("Check possibility to set Delivery time/date in future")
    public void checkThePossibilityOfChangingTheDateForTheFuture() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);

        logStep(3, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(4, "Change date");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(5, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }

    @Test
    @Description("Check that Delivery date/time couldn't be selected in the past")
    public void checkThePossibilityOfChangingTheDateForThePast() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);

        logStep(3, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(4, "Change date");
        long date = dialogModalSteps.setPastDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(5, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }

    @Bug
    @Issue("TPD-1677")
    @Test
    @Description("Check possibility to reselect already selected Delivery date/time")
    public void checkThePossibilityOfChangingTheDateForTheSelectedDate() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);

        logStep(3, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(4, "Change date");
        long date = dialogModalSteps.setNowDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(5, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }

    @Test
    @Description("Check selecting invalid time value (e.g. 86:90, any text, etc.)")
    public void checkThePossibilityOfChangingTheDateForTheInvalidTime() {
        String minute = "90";
        String hour = "90";
        String invalidText = "ss";
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);

        logStep(3, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(4, "Change Invalid time");
        dialogModalSteps.setTime(hour, minute);
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(5, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveNotText(ORDER, REQUIRED_DELIVERY_TIME, hour + ":" + minute);

        logStep(6, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(7, "Set invalid text in time field");
        dialogModalSteps.setTime(invalidText, invalidText);
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(8, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveNotText(ORDER, REQUIRED_DELIVERY_TIME, invalidText + ":" + invalidText);
    }

    @Test
    @Description("Check possibility to clear 'Date'/'Time' fields")
    public void checkThePossibilityOfClearDateFieldForChangeTime() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible and click it");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(3, "Clear date and time fields");
        dialogModalSteps.clearDate();
        dialogModalSteps.clearTime();

        logStep(4, "Check fields is empty");
        dialogModalSteps.dateFieldShouldBe(EMPTY_VALUE);
        dialogModalSteps.timeFieldShouldBe(EMPTY_VALUE);
    }

    @Test
    @Description("Check Change delivery date button functionality")
    public void checkTimeChangeButton() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Change button should be visible");
        orderProfilePageSteps.sectionButtonShouldBeVisible(ORDER, CHANGE);

        logStep(3, "Click on change button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(4, "Check that after clicking the modal page appeared");
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
    }

    @Test
    @Description("Check changing delivery date ('Change delivery date') for order with 'To support' flag and in 'Ready to execute/Suspended/Wait for merchant approval' states")
    public void checkChangingDeliveryDateForOrderWithToSupportInSuspendedState() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendedRegularOrder());
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Move order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);

        logStep(3, "Check SUSPENDED order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getEnValue());

        logStep(4, "Click CHANGE button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(5, "Change date");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));

    }

    @Bug
    @Test
    @Description("Check changing delivery date ('Change delivery date') for order with 'To support' flag and in 'Ready to execute/Suspended/Wait for merchant approval' states")
    public void checkChangingDeliveryDateForOrderWithToSupportInWaitForMerchantApprovalState() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Move order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);

        logStep(3, "Check WAIT_MERCHANT_APPROVAL order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, WAIT_MERCHANT_APPROVAL.getEnValue());

        logStep(4, "Click CHANGE button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(5, "Change date");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));

    }

    @Test
    @Description("Check changing delivery date ('Change delivery date') for order with 'To support' flag and in 'Ready to execute/Suspended/Wait for merchant approval' states")
    public void checkChangingDeliveryDateForOrderWithToSupportInReadyToExecuteState() {
        Order orderBody = useAPISteps.createRegularOrder(EMPTY_VALUE);
        merchant.acceptOrderById(orderBody.getId());
        createOrderAndGoToProfilePage(orderBody);
        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Move order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);

        logStep(3, "Check READY_TO_EXECUTE order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());

        logStep(4, "Click CHANGE button");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);

        logStep(5, "Change date");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that the changes are not saved");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }
}
