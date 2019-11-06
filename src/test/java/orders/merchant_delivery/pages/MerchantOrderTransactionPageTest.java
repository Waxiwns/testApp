// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderTransactionsPage;
import steps.UseAPISteps;
import utils.BaseTest;
import utils.objects.Order;

import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderTransactionsPage.*;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.PageIdentifiers.TRANSACTIONS;

@Log4j
public class MerchantOrderTransactionPageTest extends BaseTest {
    private static Order orderStatic;

    @BeforeAll
    public static void createOrder() {
        UseAPISteps useAPISteps = new UseAPISteps();
        orderStatic = useAPISteps.createCompletedRegularOrderByFirstDriver();
        useAPISteps.findTransactionRecords(orderStatic.getId(), TO_YOU, OUTPUT_VAT, OUTPUT_VAT, SUCCESS);
    }

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
    @Description("Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToCustomerDetails() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the Customer Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, CUSTOMER_CAPS, firstCustomer.getFullName());

        logStep(3, "Check that we are on the page of the selected client");
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Check the ability to proceed to the 'Driver Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToDriverDetails() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the Driver Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, DRIVER_CAPS, firstDriver.getFullName());

        logStep(3, "Check that we are on the page of the selected driver");
        driverDetailsSteps.driverFirstNameEnShouldHaveText(firstDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(firstDriver.getLastName());
    }

    @Test
    @Description("Check the ability to proceed to the 'Merchant Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToMerchantDetails() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the Merchant Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, MERCHANT_CAPS, merchant.getName());

        logStep(3, "Check that we are on the page of the selected merchant");
        merchantDetailsPageSteps.firstNameShouldHaveText(merchant.getName());
    }

    @Test
    @Description("Check the ability to proceed to 'System User Details' page via hyperlink in the 'Creator' field")
    public void checkTheAbilityToProceedToSystemUserDetails() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the System User in Creator column");
        orderTransactionsPageSteps.linkClick(CREATOR, admin.getFullName());

        logStep(3, "Check that we are on the page of the system user");
        systemUserDetailsPageSteps.systemUserFirstNameShouldHaveValue(admin.getFirstName());
        systemUserDetailsPageSteps.systemUserLastNameShouldHaveValue(admin.getLastName());
    }

    @Test
    @Description("Check the 'Account' column")
    public void checkAccountColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Account” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(ACCOUNT);
    }

    @Test
    @Description("Check the 'Operation' column")
    public void checkOperationColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Operation” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.OPERATION);
    }

    @Test
    @Description("Check the 'State' column")
    public void checkStateColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “State” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.STATE);
    }

    @Test
    @Description("Check the 'Description' column")
    public void checkDescriptionColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Description” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(DESCRIPTION);
    }

    @Test
    @Description("Check possibility to proceed to the 'Profile' tab of the order via Order Number in the 'Description' column")
    public void CheckPossibilityToProceedToTheProfileTabInTheDescriptionColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the order page in Description column");
        orderTransactionsPageSteps.linkClick(DESCRIPTION, orderNumber);

        logStep(3, "Check order title is visible");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Check the 'Amount' column")
    public void checkAmountColumn() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Amount” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.AMOUNT);
    }

    @Test
    @Description("check there are transactions with 'TOYOU' Beneficiary, 'INPUT_VAT', 'OUTPUT_VAT' Account and Operation for completed orders")
    public void CheckThereAreTransactionsWithToYouBeneficiary() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "check there are transactions with 'TOYOU' have success state");
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(OUTPUT_VAT, OUTPUT_VAT, STATE, SUCCESS);
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(INPUT_VAT, INPUT_VAT, STATE, SUCCESS);

        logStep(3, "Check description have order completed text ");
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(OUTPUT_VAT, OUTPUT_VAT, DESCRIPTION, ORDER_COMPLETED);
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(INPUT_VAT, INPUT_VAT, DESCRIPTION, ORDER_COMPLETED);
    }

    @Test
    @Description("Check there are transactions with 'Driver' Beneficiary, 'PRODUCT_RECEIVED', 'PAY_FOR_PRODUCTS', 'PRODUCT_TRANSFERED'," +
            " 'EXPENSES_COMPENSATION', 'ORDER_REWARD'(Driver received order reward), 'ORDER_REWARD'(VAT) for completed orders")
    public void CheckThereAreTransactionsWithDriverBeneficiary() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check there are transactions with 'Driver'");
        log("Check PRODUCT_RECEIVED transaction");
        orderTransactionsPageSteps.driverTransactionShouldHaveText(PRODUCT, PRODUCT_RECEIVED, STATE, SUCCESS);
        log("Check PAY_FOR_PRODUCTS transaction");
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, PAY_FOR_PRODUCTS, STATE, SUCCESS);
        log("Check PRODUCT_TRANSFERED transaction");
        orderTransactionsPageSteps.driverTransactionShouldHaveText(PRODUCT, PRODUCT_TRANSFERED, STATE, SUCCESS);
        log("Check EXPENSES_COMPENSATION transaction");
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, EXPENSES_COMPENSATION, STATE, SUCCESS);
        log("Check ORDER_REWARD transaction");
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, ORDER_REWARD, STATE, SUCCESS);
    }


    @Test
    @Description("Check possibility to open the 'External Transactions' window for external transactions in 'ID' column")
    public void checkExternalTransactions() {
        createOrderAndGoToProfilePage(orderStatic);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check possibility to open the 'External Transactions' window for external transactions in 'ID' column");
        orderTransactionsPageSteps.externalTransactionsButtonClick();
        orderTransactionsPageSteps.headerOfExternalTransactionsPanelShouldBe();
    }
}
