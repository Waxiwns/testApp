// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.objects.Order;

import static core.TestStepLogger.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.PageIdentifiers.PROFILE;

@Log4j
public class MerchantOrderCreateTicketTest extends BaseTest {

    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();
        firstDriver.updateLocation(locationKharkivPushkinska1);
        logPreconditionStep(4, "Create courier new order");
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
    @Description("Check ticket creation page")
    public void checkTicketCreationPage() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));
        logStep(1, "Go to the page for creating tickets and checking its validity");
        orderHeaderPageSteps.createTicketBtnClick();
        orderCreateTicketPageSteps.createTicketButtonShouldBeVisible();
    }
}
