// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import static core.TestStepLogger.*;
import static utils.constants.PageIdentifiers.*;

public class CourierOrderHeaderTest extends BaseTest {

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create courier new order");
        order = useAPISteps.createCourierCCOrderByFirstCustomer();
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());
    }

    @Test
    @Description("Header section: Check that Order Number and search field is displayed")
    public void checkThatOrderNumberIsDisplayed() {
        logStep(1, "Check order number");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(2, "Check search field");
        orderHeaderPageSteps.searchFieldShouldHavePlaceholder();
    }

    @Test
    @Description("Header section: Check that the order page is opened which the order number entered in the search field")
    public void checkThatTheOrderPageIsOpenedWhichTheOrderNumberEnteredInTheSearchField() {
        try {
            logStep(1, "Check order number");
            orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

            logStep(2, "Create courier second new order");
            orderSecond = useAPISteps.createCourierCCOrderByFirstCustomer();
            orderIdSecond = orderSecond.getId();
            orderNumberSecond = orderSecond.getNumber();

            logStep(3, "Search order by second order number and check that order page is opened");
            orderHeaderPageSteps.searchGoBtnShouldBeTranslated();
            orderHeaderPageSteps.searchOrder(orderNumberSecond);
            orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumberSecond);

            logStep(4, "Check that success message appears");
            notificationModalPageSteps.switchToAnotherOrderMessageShouldBeDisplayed();

            logStep(5, "Search order by first order ID and check that order page is opened");
            orderHeaderPageSteps.searchOrder(orderId);
            orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

            logStep(6, "Check that success message appears");
            notificationModalPageSteps.switchToAnotherOrderMessageShouldBeDisplayed();
        } catch (Exception ex) {
            log(ex.getMessage());
        } finally {
            logPostconditionStep(1, "Cancel order");
            useAPISteps.cancelOrder(orderSecond);
        }
    }

    @Test
    @Description("Header section: Check that Create Ticket is displayed and visible")
    public void checkThatCreateTicketIsDisplayed() {
        logStep(1, "Check that 'Create Ticket' button is in English");
        orderHeaderPageSteps.createTicketBtnShouldBeTranslated();

        logStep(2, "Check that 'Create Ticket' button change color after hover");
        orderHeaderPageSteps.createTicketBtnClick();

        logStep(3, "Check that 'Create Ticket' page is opened");
        orderCreateTicketPageSteps.checkThatCreateTicketPageIsOpened();
        orderCreateTicketPageSteps.createTicketPageShouldBeEnglish();
    }

    @Test
    @Description("Header section: Check the URL of the pages")
    public void checkValidUrlPages() {
        logStep(1, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logStep(2, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(3, "Check pages");
        orderHeaderPageSteps.checkPageUrl(orderId, CART);
        orderHeaderPageSteps.checkPageUrl(orderId, HISTORY);
        orderHeaderPageSteps.checkPageUrl(orderId, CHANGES);
        orderHeaderPageSteps.checkPageUrl(orderId, COSTCALCULATION);
        orderHeaderPageSteps.checkPageUrl(orderId, TRANSACTIONS);
        orderHeaderPageSteps.checkPageUrl(orderId, TICKETS);
        orderHeaderPageSteps.checkPageUrl(orderId, TRACK);
    }

    @Test
    @Description("Header section: Check that breadcrumbs are correctly displayed")
    public void checkBreadcrumbs() {
        logStep(1, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());
        orderHeaderPageSteps.isActiveTab(PROFILE);

        logStep(2, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(3, "Check that breadcrumbs are displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, CART);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, HISTORY);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, CHANGES);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, COSTCALCULATION);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TRANSACTIONS);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TICKETS);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TRACK);
    }

    @Test
    @Description("Header section: Check the transition to the 'Profile' page when clicking on orderId breadcrumbs from all tabs")
    public void checkTransitionToTheProfile() {
        logStep(1, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());
        orderHeaderPageSteps.isActiveTab(PROFILE);

        logStep(2, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(3, "Breadcrumbs should be displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayed();

        logStep(4, "Click on orderId breadcrumbs and check");
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, CART);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, HISTORY);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, CHANGES);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, COSTCALCULATION);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TRANSACTIONS);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TICKETS);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TRACK);
    }

    @Test
    @Description("Header section: Check the transition to the 'Delivery Orders' page after click on Orders breadcrumb")
    public void checkTransitionToDeliveryOrdersPage() {
        logStep(1, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logStep(2, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(3, "Breadcrumbs should be displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayed();

        logStep(4, "Click on Orders breadcrumbs");
        orderHeaderPageSteps.clickBreadCrumbTabName();

        logStep(5, "Check that 'Delivery orders' page is opened");
        ordersPageSteps.shouldBeDisplayedDeliveryOrdersEn();
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
