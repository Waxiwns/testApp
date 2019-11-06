// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderCostCalculationPage.*;
import static utils.constants.ApiConstants.DROP_OFF;
import static utils.constants.ApiConstants.PICKUP;
import static utils.constants.PageIdentifiers.COSTCALCULATION;

public class CourierOrderCostCalculationPageTest extends BaseTest {

    @BeforeEach
    public void enablePreAuth() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create courier new order");
    }

    public void createOrderAndGoToCustomerCostCalculationTab(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to 'Cost calculation' tab");
        switchToTheOrdersPage(orderId, COSTCALCULATION.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Cost calculation page: Check calculation of the order in case of the CC+Promo (Delivery fee)")
    public void checkCalculationOfTheOrderDeliveryFee() {
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCourierCCPromoOrderByFirstCustomer(testInitValues.promoCourierDeliveryValue()));

        logStep(1, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatCostCorrectlyDisplayedWithTwoCompositions(DELIVERY_FEE, SHOWN_AMOUNT, order.getOriginalCost(), order.getDeliveryFeePromo());
    }

    @Test
    @Description("Check calculation of the order in case of the CC+debt+Promo (Delivery fee)")
    public void checkCalculationOfTheOrderInCaseOfCCDebtPromo() {
        double balance = useAPISteps.getUserBalance(testInitValues.secondCustomerId(), CUSTOMER, BALANCE);
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCompletedCourierCCPromoAndDebtOrderForSecondCustomerBySecondDriver(testInitValues.promoCourierDeliveryValue(), balance));

        logStep(1, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(DELIVERY_FEE, SHOWN_AMOUNT, order.getDeliveryCost());
    }

    @Test
    @Description("Cost calculation page: Check calculation of the order in case of the CC+Promo (Service fee)")
    public void checkCalculationOfTheOrderServiceFee() {
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierServiceDiscount()));
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
        refresh();

        logStep(1, "Check that ServiceFee promo is displayed");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(SERVICE_FEE, PROMO_DISCOUNT, order.getServiceFeePromoValue());

        logStep(2, "Check calculation of the order(Service fee)");
        costCalcPageSteps.checkThatCostSubtractItFromTotalCostCorrectlyDisplayed(TOTAL_COSTS, SHOWN_AMOUNT, order.getServiceFeePromoValue());
    }

    @Test
    @Description("Cost calculation page: Check calculation of the order in case of the CC+debt+Promo (Service fee)")
    public void checkCalculationOTheOrderInaCaseOfTheCCDebtPromo() {
        double balance = useAPISteps.getUserBalance(testInitValues.secondCustomerId(), CUSTOMER, BALANCE);
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCompletedCourierCCPromoAndDebtOrderForSecondCustomerBySecondDriver(testInitValues.promoCourierServiceDiscount(), balance));

        logStep(1, "Check that ServiceFee promo is displayed");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(SERVICE_FEE, SHOWN_AMOUNT, order.getServiceFee());
    }

    @Test
    @Description("Cost calculation page: Check calculation of the order in case of the CC+Promo (Delivery fee) if change pick-up/drop-off location")
    public void checkCalculationOfTheOrderInCaseOfTheCCPromoIfChangeLocation() {
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCourierCCPromoOrderByFirstCustomer(testInitValues.promoCourierDeliveryValue()));

        logStep(1, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatCostCorrectlyDisplayedWithTwoCompositions(DELIVERY_FEE, SHOWN_AMOUNT, order.getOriginalCost(), order.getDeliveryFeePromo());

        logStep(2, "Change pick_up location");
        useAPISteps.updateOrderAddressByAdmin(PICKUP, orderId, locationKharkivPushkinska10);
        order = useAPISteps.updateOrderDetails(orderId);
        refresh();

        logStep(3, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(DELIVERY_FEE, SHOWN_AMOUNT, order.getDeliveryCost());
    }

    @Test
    @Description("Cost calculation page: Check calculation of the order in case of the CC+debt+Promo (Delivery fee) if change pick-up/drop-off location")
    public void checkCalculationOfTheOrderInCaseOfTheCCDebtPromoIfChangeLocation() {
        double balance = useAPISteps.getUserBalance(testInitValues.secondCustomerId(), CUSTOMER, BALANCE);
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCourierCCUseBalancePromoAndDebtOrderForSecondCustomerBySecondDriver(testInitValues.promoCourierDeliveryValue(), balance));

        logStep(1, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(DELIVERY_FEE, SHOWN_AMOUNT, order.getDeliveryCost());

        logStep(2, "Change drop_off location");
        useAPISteps.updateOrderAddressByAdmin(DROP_OFF, orderId, locationKharkivPushkinska10);
        order = useAPISteps.updateOrderDetails(orderId);
        refresh();

        logStep(3, "Check calculation of the order(Delivery fee)");
        costCalcPageSteps.checkThatValueCorrectlyDisplayed(DELIVERY_FEE, SHOWN_AMOUNT, order.getDeliveryCost());
    }

    @Test
    @Description("Order pre authorization test: Check all fields (always '0' if PA is not applied)")
    public void checkDebtField() {
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Turn off pre-auth");
        useAPISteps.updateServiceTypeCustomerTariffsDeliveryKharkiv(false, "courier", false);

        logStep(2, "Check all fields");
        costCalcPageSteps.checkPreAuthTableValues(NUMBER_0, NUMBER_0, NUMBER_0, NUMBER_0, NUMBER_0);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
