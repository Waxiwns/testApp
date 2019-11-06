// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.mdb;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.UseAPISteps;
import utils.BaseTest;
import utils.objects.Order;

import static core.TestStepLogger.logPreconditionStep;
import static core.TestStepLogger.logStep;
import static utils.constants.PageIdentifiers.*;

public class MdbOrderHeaderTest extends BaseTest {
    private static Order orderStatic;

    @BeforeAll
    static void createOrderBeforeTest() {
        orderStatic = new UseAPISteps().createMdbOrderWithKharkivPos();
    }

    @AfterAll
    static void cancelOrder() {
        new UseAPISteps().cancelOrder(orderStatic);
    }

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create mdb new order");
        order = orderStatic;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());
        orderHeaderPageSteps.isActiveTab(PROFILE);

        logPreconditionStep(6, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Check that breadcrumbs are correctly displayed")
    public void checkBreadcrumbs() {
        logStep(1, "Check that breadcrumbs are displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, HISTORY);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, CHANGES);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, COSTCALCULATION);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TRANSACTIONS);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TICKETS);
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, TRACK);
    }

    @Test
    @Description("Check the transition to the 'Profile' page when clicking on orderId breadcrumbs from all tabs")
    public void checkTransitionToTheProfile() {
        logStep(1, "Breadcrumbs should be displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayed();

        logStep(2, "Click on orderId breadcrumbs and check");
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, HISTORY);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, CHANGES);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, COSTCALCULATION);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TRANSACTIONS);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TICKETS);
        orderHeaderPageSteps.checkTransitionToTheProfilePage(orderId, TRACK);
    }

    @Test
    @Description("Check the transition to the 'Delivery Orders' page after click on Orders breadcrumb")
    public void checkTransitionToDeliveryOrdersPage() {
        logStep(1, "Breadcrumbs should be displayed");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayed();

        logStep(2, "Click on Orders breadcrumbs");
        orderHeaderPageSteps.clickBreadCrumbTabName();

        logStep(3, "Check that 'Delivery orders' page is opened");
        ordersPageSteps.shouldBeDisplayedDeliveryOrdersEn();
    }
}