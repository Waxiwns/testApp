// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.constants.ApiConstants;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.modalPages.DialogModal.CHANGE_DELIVERY_DATE_TITLE;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.Buttons.CHANGE;
import static utils.constants.OrderPrices.ZERO;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.Statuses.*;

public class CourierOrderProfilePageOrderSectionTest extends BaseTest {

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
    @Description("Courier order 'Order' section: Check order section parameters displaying")
    public void checkOrderSectionParametersDisplayingAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section parameters");
        log("Check order ID is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ID, orderNumber);
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order service group is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, SERVICE_GROUP, order.getServiceGroup());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order required vehicle 'Car' type is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_VEHICLE_TYPE, ApiConstants.CAR);
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
        log("Check order finished time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, FINISHED_TIME, "");
        log("Check driver rating for this order is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, DRIVER_RATING_FOR_THIS_ORDER, ZERO.getValue());
        log("Check order ASAP 'Yes' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);
    }

    @Test
    @Description("Courier order 'Order' section: Check that timer works correctly")
    public void checkThatTimerWorksCorrectly() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();

        logStep(4, "Check Date and Time");
        dialogModalSteps.checkFormDateTime(order.getDeliveryDateLong());
    }

    @Test
    @Description("Courier order 'Order' section: Check selecting invalid time value (e.g. '86:90')")
    public void checkSelectingInvalidTimeValue() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();

        logStep(5, "Check selecting invalid time");
        dialogModalSteps.setAndCheckInvalidTime();
    }

    @Test
    @Description("Courier order 'Order' section: Check order required delivery time and ASAP No displaying")
    public void checkOrderRequiredDeliveryTimeDisplayingAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section 'SUSPENDED' order status");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getValue());

        logStep(3, "Check order creation and delivery time");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());

        logStep(3, "Check order ASAP 'No' is displaying");
        log("Check order ASAP is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);
    }

    @Test
    @Description("Courier order 'Order' section: Check order section finished time parameter displaying")
    public void checkOrderSectionFinishedTimeParameterDisplayingAtOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Refresh page to check updated information");
        refresh();

        logStep(3, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, CANCELLED.getValue());
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());

        log("Check order finished time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, FINISHED_TIME, order.getFinishedDate());
    }

    @Test
    @Description("Courier order 'Order' section: Check that 'ASAP' mark changes to 'No' after setting up 'Delivery time' in future for 'Ride Now' order")
    public void checkThatASAPMarkChangesToNoAfterSettingUpDeliveryTimeInFutureForRideNowOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order to support is displaying 'yes'");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, TO_SUPPORT, YES);
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
        log("Check order ASAP 'Yes' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to future");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);
    }

    @Bug
    @Issue("TPD-1677")
    @Test
    @Description("Courier order 'Order' section: Check that 'ASAP' mark changes to 'Yes' after setting up 'Delivery time' to 'Now' for 'Ride letter' order")
    public void checkThatASAPMarkChangesToYesAfterSettingUpDeliveryTimeNowForRideLetterOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to now");
        long date = dialogModalSteps.setNowDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
        log("Check order ASAP 'Yes' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);
    }

    @Test
    @Description("Courier order 'Order' section: Check the 'Change' button functionality in the 'Order' section")
    public void checkTheChangeButtonFunctionalityInTheOrderSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();

        logStep(4, "Close Change time modal ");
        dialogModalSteps.closeModal(CHANGE_DELIVERY_DATE_TITLE);

        logStep(5, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
    }

    @Test
    @Description("Courier order 'Order' section: Check possibility to set 'Delivery time/date' in future. (Order must be moved to 'Suspended'/'Planned' status)")
    public void checkPossibilityToSetDeliveryTimeDateInFutureOrderMustBeMovedToSuspendedStatus() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to future");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);

        logStep(8, "Restart order");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.RESTART_ORDER);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(9, "Check 'SUSPENDED' order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getValue());
    }

    @Test
    @Description("Courier order 'Order' section: Check that 'Delivery date/time' couldn't be selected in the past")
    public void checkThatDeliveryDateCouldNotBeSelectedInThePast() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
        log("Check order ASAP 'Yes' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, YES);

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to future");
        long date = dialogModalSteps.setPastDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' couldn't be selected in the past");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }

    @Test
    @Description("Courier order 'Order' section: Check that delivery current date is selected when 'Delivery date/time' is selected in the past for already suspended orders")
    public void checkThatDeliveryCurrentDateIsSelectedWhenDeliveryDateIsSelectedInThePastForAlreadySuspendedOrders() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.oneDaySeconds));

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getEnValue());
        log("Check order creation date is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, CREATED, order.getCreationDate());
        log("Check order to support is displaying 'yes'");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, TO_SUPPORT, YES);
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to future");
        long date = dialogModalSteps.setPastDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' couldn't be selected in the past");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
    }

    @Test
    @Description("Courier order 'Order' section: Check possibility to reselect already selected 'Delivery date/time' and Check possibility to clear 'Date'/'Time' fields")
    public void checkPossibilityToReselectAlreadySelectedDeliveryDateTimeAndClearDateTimeFields() {
        createOrderAndGoToProfilePage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        order = useAPISteps.updateOrderDetails(orderId);

        logStep(2, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(3, "Check order section parameters");
        log("Check order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, order.getDeliveryDate());

        logStep(4, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();
        logStep(5, "Change 'Delivery time' to future");
        long date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(6, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(7, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);

        logStep(8, "Open Change modal");
        orderProfilePageSteps.sectionButtonClick(ORDER, CHANGE);
        dialogModalSteps.deliveryDateTitleShouldBeVisible();

        logStep(9, "Check possibility to clear 'Date'/'Time' fields");
        dialogModalSteps.clearDate();
        dialogModalSteps.clearTime();

        logStep(10, "Reselect selected date/time");
        date = dialogModalSteps.setSuspendDate();
        dialogModalSteps.saveBtnClick(CHANGE_DELIVERY_DATE_TITLE);

        logStep(11, "Check that Change modal is not visible and success message is visible");
        dialogModalSteps.changeDateModalShouldNotBeVisible();
        notificationModalPageSteps.updateDeliveryDateMessageShouldBeDisplayed();

        logStep(12, "Check order section 'Date' and 'ASAP' parameters");
        log("Check order required delivery time is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, REQUIRED_DELIVERY_TIME, useAPISteps.convertOrderDate(date));
        log("Check order ASAP 'No' is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, ASAP, NO);
    }

    @Test
    @Description("Courier order 'Order' section: Check that driver receive 'Suspended'/'Planned' order for a 20 minutes before selected time and order status changes to 'Ready to execute'.")
    public void checkThatDriverReceiveSuspendedPlannedOrderForA20MinutesBeforeSelectedTime() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendCourierOrderByFirstCustomer(useAPISteps.twentyOneMinutesSeconds));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(ORDER);

        logStep(2, "Check Suspended order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, SUSPENDED.getValue());

        logStep(3, "Cancel driver order");
        secondDriver.checkActiveOrderAndCancelIt();

        logStep(4, "Wait For 'Ready To Execute' Order Status and Update driver location");
        useAPISteps.waitForReadyToExecuteOrderStatus(secondDriver, locationKharkivPushkinska1, orderId);
        refresh();

        logStep(5, "Check Ready to execute order status is displaying");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());

        logStep(6, "Accept order by second driver");
        secondDriver.acceptOrder();

        logStep(7, "Check that order is accepted by driver");
        refresh();
        useAPISteps.checkActiveOrderForSecondDriver(orderId);
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, ON_THE_WAY_TO_PICK_UP.getEnValue());
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
