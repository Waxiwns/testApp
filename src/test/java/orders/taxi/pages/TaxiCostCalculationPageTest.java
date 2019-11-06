// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.taxi.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import utils.BaseTest;
import utils.objects.Order;

import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderCostCalculationPage.*;
import static pages.orderPageTabs.OrderCostCalculationPage.BALANCE;
import static pages.orderPageTabs.OrderCostCalculationPage.DELIVERY_FEE;
import static pages.taxonomy.serviceTypeTabs.CustomerTariffsPage.*;
import static pages.taxonomy.serviceTypeTabs.CustomerTariffsPage.COST_TEN;
import static utils.constants.PageIdentifiers.COSTCALCULATION;

public class TaxiCostCalculationPageTest extends BaseTest {

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
    @Description("Check calculation of the order in case of the CC+debt+Promo")
    public void checkCalculationOTheOrderInCaseOfTheCCDebtPromo() {
        double balance = useAPISteps.getUserBalance(testInitValues.secondCustomerId(), CUSTOMER, BALANCE);
        useAPISteps.setBalanceToCustomer(balance,32000, secondCustomer);
        createOrderAndGoToCustomerCostCalculationTab(useAPISteps.createCompletedTaxiCCPromoAndDebtOrderForSecondCustomerBySecondDriver(testInitValues.promoSmokeTaxi()));
        logStep(1, "Set Customer balance on 200000000");
        useAPISteps.setBalanceToCustomer(balance,200000000, secondCustomer);
        logStep(1, "Check calculation of the order(Delivery fee)");

        costCalcPageSteps.checkThatValueCorrectlyDisplayed(DELIVERY_FEE, SHOWN_AMOUNT, order.getDeliveryCost());//--------------------------!!!!!
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
