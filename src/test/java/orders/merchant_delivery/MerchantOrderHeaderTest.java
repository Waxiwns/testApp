// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import static core.TestStepLogger.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.PageIdentifiers.PROFILE;

public class MerchantOrderHeaderTest extends BaseTest {

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create courier new order");
        order = useAPISteps.createRegularOrder(EMPTY_VALUE);
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }

    @Test
    @Description("Header section: Check the Order # is displayed in the 'Order Details' page header")
    public void checkThatOrderNumberIsDisplayed() {
        logStep(1, "Check order number");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Header section:Check that 'Create Ticket' button functionality and that one leads to 'New ticket' page")
    public void checkThatCreateTicketIsDisplayedEn() {
        logStep(1, "Check that 'Create Ticket' button is in English");
        orderHeaderPageSteps.createTicketBtnShouldBeTranslated();

        logStep(2, "Click on 'Create Ticket' button");
        orderHeaderPageSteps.createTicketBtnClick();

        logStep(3, "Check that 'Create Ticket' page is opened");
        orderCreateTicketPageSteps.checkThatCreateTicketPageIsOpened();
        orderCreateTicketPageSteps.createTicketPageShouldBeEnglish();
    }

    @Test
    @Description("Header section: Check the URL of the pages")
    public void checkValidUrlPagesEn() {
        logStep(1, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(2, "Check pages");
        orderHeaderPageSteps.checkPageUrl(orderId, PROFILE);
    }

    @Test
    @Description("Header section: Check fast shift to another order details page by Order ID or Order Number")
    public void checkFastShift() {
        logStep(1, "Check that we in needed order page");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logStep(2, "Create second order");
        orderSecond = useAPISteps.createRegularOrder(EMPTY_VALUE);

        logStep(3, "Switch  to the second orders page");
        switchToTheOrdersPage(orderSecond.getId(), PROFILE.getValue());

        logStep(4, "Check order page number");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderSecond.getNumber());

        logStep(5, "Cancel the second order");
        useAPISteps.cancelOrder(orderSecond);
    }

    @Test
    @Description("Header section: Check the 'Search by orderId or orderNumber' search field placeholder in the 'Order Details' page header")
    public void checkPlaceholderOfSearchField() {
        logStep(1, "Check search field");
        orderHeaderPageSteps.searchFieldShouldHavePlaceholder();
    }
}
