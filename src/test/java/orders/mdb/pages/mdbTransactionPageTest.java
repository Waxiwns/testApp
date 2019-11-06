// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.mdb.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderTransactionsPage;
import steps.UseAPISteps;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderTransactionsPage.*;
import static utils.constants.Locale.AR;
import static utils.constants.Locale.EN;
import static utils.constants.PageIdentifiers.TRANSACTIONS;
import static utils.constants.Statuses.*;
import static utils.constants.TransactionConstants.PENALTY;

@Log4j
public class mdbTransactionPageTest extends BaseTest {
    private static Order orderStatic;

    @BeforeAll
    static void createOrderBeforeTest() {
        UseAPISteps useAPISteps = new UseAPISteps();
        orderStatic = useAPISteps.createCompletedMdbOrderByFirstDriver();
        useAPISteps.findTransactionRecords(orderStatic.getId(), TO_YOU, BALANCE, OUTPUT_VAT, SUCCESS);
    }

    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create mdb new order");
        this.order = orderStatic;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(7, "Check order profile page is active");
        orderHeaderPageSteps.isActiveTab(TRANSACTIONS);
    }

    @Test
    @Description("Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToCustomerDetails() {
        logStep(1, "Check link to the Customer Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, CUSTOMER_CAPS, firstCustomer.getFullName());

        logStep(2, "Check that we are on the page of the selected client");
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Check the ability to proceed to the 'Driver Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToDriverDetails() {
        logStep(1, "Check link to the Driver Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, DRIVER_CAPS, firstDriver.getFullName());

        logStep(2, "Check that we are on the page of the selected driver");
        driverDetailsSteps.driverFirstNameEnShouldHaveText(firstDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(firstDriver.getLastName());
    }

    @Test
    @Description("Check the ability to proceed to the 'Merchant Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToMerchantDetails() {
        logStep(1, "Check link to the Merchant Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, MERCHANT_CAPS, merchant.getName());

        logStep(2, "Check that we are on the page of the selected merchant");
        merchantDetailsPageSteps.firstNameShouldHaveText(merchant.getName());
    }

    @Test
    @Description("Check the ability to proceed to 'System User Details' page via hyperlink in the 'Creator' field")
    public void checkTheAbilityToProceedToSystemUserDetails() {
        logStep(1, "Check link to the System User in Creator column");
        orderTransactionsPageSteps.linkClick(CREATOR, admin.getFullName());

        logStep(2, "Check that we are on the page of the system user");
        systemUserDetailsPageSteps.systemUserFirstNameShouldHaveValue(admin.getFirstName());
        systemUserDetailsPageSteps.systemUserLastNameShouldHaveValue(admin.getLastName());
    }

    @Test
    @Description("Check table titles")
    public void checkAccountColumn() {
        logStep(1, "Check the 'Account' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(ACCOUNT);

        logStep(2, "Check the 'Operation' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.OPERATION);

        logStep(3, "Check the 'State' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.STATE);

        logStep(4, "Check the 'Description' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(DESCRIPTION);

        logStep(5, "Check the 'Amount' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.AMOUNT);

        logStep(6, "Check the 'ID' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.ID);

        logStep(7, "Check the 'Date' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(DATE);

        logStep(8, "Check the 'Beneficiary' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.BENEFICIARY);

        logStep(9, "Check the 'Creator' column");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(CREATOR);
    }

    @Test
    @Description("Check possibility to proceed to the 'Profile' tab of the order via Order Number in the 'Description' column")
    public void checkPossibilityToProceedToTheProfileTabInTheDescriptionColumn() {
        logStep(1, "Check link to the order page in Description column");
        orderTransactionsPageSteps.linkClick(DESCRIPTION, orderNumber);

        logStep(2, "Check order title is visible");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Check there is transaction with 'TOYOU' Beneficiary, 'BALANCE' Account, 'OUTPUT_VAT' Operation for completed orders")
    public void checkThereAreTransactionsWithToYouBeneficiary() {
        logStep(1, "Account INPUT_VAT and OUTPUT_VAT");
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(BALANCE, OUTPUT_VAT, STATE, SUCCESS);

    }

    @Test
    @Description("Check the color of positive/negative amount in the 'Amount' column")
    public void checkPositiveNegativeAmountColor() {
        logStep(1, "Check the color of positive/negative amount in the 'Amount' column");
        orderTransactionsPageSteps.checkValueColorForAmount();
    }

    @Test
    @Description("Check state")
    public void checkSuccessFailState() {
        logStep(1, "Check the success statuses and Check the success statuses");
        orderTransactionsPageSteps.checkValueColorForStatus();
    }

    @Test
    @Description("Check the 'Transactions' tab name")
    public void checkTheTransactionTabName() {
        logStep(1, "Check the 'Transactions' tab name");
        orderHeaderPageSteps.isActiveTab(TRANSACTIONS);
    }

    @Test
    @Description("Check the list of the transactions in each step of the order and check the status of each transaction")
    public void CheckTransactionsInEachSteps() {
        logStep(1, "Create mdb order and check that transaction table is empty");
        firstDriver.makeOnline(locationKharkivPushkinskaProvulok4);
        Order secondOrder = useAPISteps.createMdbOrderWithKharkivPos();
        switchToTheOrdersPage(secondOrder.getId(), TRANSACTIONS.getValue());
        refresh();
        orderTransactionsPageSteps.noRecordsVariableShouldBe(NO_RECORD_AVAILABLE);

        logStep(2, "Accept order by driver and check transaction table");
        firstDriver.acceptOrder();
        refresh();
        orderTransactionsPageSteps.noRecordsVariableShouldBe(NO_RECORD_AVAILABLE);

        logStep(3, "Update order status to AT_PICK_UP and check table");
        firstDriver.updateActiveOrderStatus(AT_PICK_UP.getValue());
        refresh();
        orderTransactionsPageSteps.noRecordsVariableShouldBe(NO_RECORD_AVAILABLE);

        logStep(4, "Update order status to ON_THE_WAY_TO_DROP_OFF and check table");
        firstDriver.updateActiveOrderStatus(ON_THE_WAY_TO_DROP_OFF.getValue());
        refresh();

        logStep(5, "Update order status to AT_DROP_OFF and check table");
        firstDriver.updateActiveOrderStatus(AT_DROP_OFF.getValue());
        useAPISteps.findTransactionRecords(secondOrder.getId(), DRIVER_CAPS, PRODUCT, PRODUCT_RECEIVED, SUCCESS);
        refresh();
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, PAY_FOR_PRODUCTS, STATE, RESERVED);
        orderTransactionsPageSteps.merchantTransactionShouldHaveText(PRODUCT, PRODUCT_TRANSFERED, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(PRODUCT, PRODUCT_RECEIVED, STATE, SUCCESS);

        logStep(6, "Complete order by first driver and check table");
        firstDriver.completeOrder();
        useAPISteps.findTransactionRecords(secondOrder.getId(), TO_YOU, BALANCE, OUTPUT_VAT, SUCCESS);
        refresh();

        orderTransactionsPageSteps.toYouTransactionShouldHaveText(BALANCE, OUTPUT_VAT, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, OUTPUT_VAT, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, ORDER_REWARD, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, PAY_FOR_ORDER, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(CASH, ORDER_REWARD, STATE, SUCCESS);
        orderTransactionsPageSteps.customerTransactionShouldHaveText(CASH, PAY_FOR_ORDER, STATE, SUCCESS);
        orderTransactionsPageSteps.merchantTransactionShouldHaveText(BALANCE, ORDER_REWARD, STATE, SUCCESS);
        orderTransactionsPageSteps.customerTransactionShouldHaveText(PRODUCT, PRODUCT_RECEIVED, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(PRODUCT, PRODUCT_TRANSFERED, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, PAY_FOR_PRODUCTS, STATE, FAIL);
        orderTransactionsPageSteps.merchantTransactionShouldHaveText(PRODUCT, PRODUCT_TRANSFERED, STATE, SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(PRODUCT, PRODUCT_RECEIVED, STATE, SUCCESS);
    }

    @Test
    @Description("Check the pagination")
    public void checkPagination() {
        try {
            logStep(1, "Add penalty transactions");
            for (int i = 0; i != 30; i++) {
                useAPISteps.createManualTransactionForCustomer(firstCustomer, PENALTY, "200", order.getId());
            }

            logStep(2, "Reload current page");
            refresh();

            logStep(3, "Check pagination block");
            log("Check items per page title");
            paginationPageSteps.checkItemsPerPageTitleShouldBeEnglish();
            log("Check quantity of items");
            paginationPageSteps.checkQtyOfItemsStepShouldHaveText("1 - 20 of 74 items");

            log("Check Drop down value");
            paginationPageSteps.checkThatItemsPerPageDropDownFirstValueExist();
            log("Check navigation buttons functionality");
            paginationPageSteps.checkVisibilityOfGoFirstPageButtonStep();
            paginationPageSteps.checkVisibilityOfGoToThePreviousPageButtonStep();
            paginationPageSteps.checkVisibilityOfGoToTheLastPageButtonStep();
            paginationPageSteps.checkVisibilityOfGoToTheNextPageButtonStep();
            log("Check the number of the active button if we change the number of the list of items");
            paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("1");
            paginationPageSteps.clickNextPageButtonStep();
            paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("2");
            paginationPageSteps.clickNextPageButtonStep();
            paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("3");
        } finally {
            log("Create new completed mdb order");
            orderStatic = useAPISteps.createCompletedMdbOrderByFirstDriver();
        }
    }

    @Bug
    @Issue("BACK-5200")
    @Test
    @Description("Check the translation English/Arabic")
    public void checkTheTranslationEnglishArabic() {
        logStep(1, "Change translation EN");
        orderTransactionsPageSteps.checkTableTitleTranslation(EN);
        orderTransactionsPageSteps.checkFilterBlockTranslation(EN);
        orderTransactionsPageSteps.cardTitleButtonShouldHaveText(CARD_TITLE);
        orderTransactionsPageSteps.addTransactionButtonShouldHaveText(ADD_TRANSACTION);
        orderTransactionsPageSteps.exportToExcelButtonShouldHaveText(EXPORT_TO_EXCEL);

        logStep(2, "Change language to the AR");
        headerPageSteps.changeLanguage();

        logStep(3, "Change translation AR");
        orderTransactionsPageSteps.checkTableTitleTranslation(AR);
        orderTransactionsPageSteps.checkFilterBlockTranslation(AR);
        orderTransactionsPageSteps.cardTitleButtonShouldHaveText(CARD_TITLE_AR);
        orderTransactionsPageSteps.addTransactionButtonShouldHaveText(ADD_TRANSACTION_AR);
        orderTransactionsPageSteps.exportToExcelButtonShouldHaveText(EXPORT_TO_EXCEL_AR);

        logStep(4, "Change language to the EN");
        headerPageSteps.changeLanguage();

        logStep(5, "Change translation EN");
        orderTransactionsPageSteps.checkTableTitleTranslation(EN);
        orderTransactionsPageSteps.checkFilterBlockTranslation(EN);
        orderTransactionsPageSteps.cardTitleButtonShouldHaveText(CARD_TITLE);
        orderTransactionsPageSteps.addTransactionButtonShouldHaveText(ADD_TRANSACTION);
        orderTransactionsPageSteps.exportToExcelButtonShouldHaveText(EXPORT_TO_EXCEL);
    }

    @Test
    @Description("Check possibility to sort grid via columns (ID, Date, Amount)")
    public void checkPossibilityToSortViaColumns() {
        logStep(1, "Change sorting by column ID and check it");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(ID);
        orderTransactionsPageSteps1.transactionTableHeaderSortUpArrowShouldBeVisible(ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, ID);

        logStep(2, "Change sort down for selected column");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(ID);
        orderTransactionsPageSteps1.transactionTableHeaderSortDownArrowShouldBeVisible(ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, ID);

        logStep(3, "Change sorting by column DATE and check it");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(DATE);
        orderTransactionsPageSteps1.transactionTableHeaderSortUpArrowShouldBeVisible(DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, DATE);

        logStep(4, "Change sort down for selected column");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(DATE);
        orderTransactionsPageSteps1.transactionTableHeaderSortDownArrowShouldBeVisible(DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, DATE);

        logStep(5, "Change sorting by column AMOUNT and check it");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(AMOUNT);
        orderTransactionsPageSteps1.transactionTableHeaderSortUpArrowShouldBeVisible(AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, AMOUNT);

        logStep(6, "Change sort down for selected column");
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(AMOUNT);
        orderTransactionsPageSteps1.transactionTableHeaderSortDownArrowShouldBeVisible(AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, AMOUNT);
    }


    @Test
    @Description("Check possibility to filter records with 'Operation' filter (no value should be preselected by default)")
    public void checkTransactionFilter() {
        logStep(1, "Check no value should be preselected by default");
        orderTransactionsPageSteps1.operationFilterShouldBeEmpty();

        logStep(2, "Check selected filter option");
        orderTransactionsPageSteps1.operationFilterSelectOption(OUTPUT_VAT);
        orderTransactionsPageSteps1.applyFilterBtnClick();
        orderTransactionsPageSteps1.operationFilterShouldHaveSelectedOption(OUTPUT_VAT);

        logStep(3, "Check table Operation column");
        orderTransactionsPageSteps1.tableLinesOperationColumnShouldHaveText(OUTPUT_VAT);

        logStep(4, "Check selected filter option");
        orderTransactionsPageSteps1.operationFilterSelectOption(INPUT_VAT);
        orderTransactionsPageSteps1.applyFilterBtnClick();
        orderTransactionsPageSteps1.operationFilterShouldHaveSelectedOption(INPUT_VAT);

        logStep(5, "Check empty table");
        orderTransactionsPageSteps.noRecordsVariableShouldBe(NO_RECORD_AVAILABLE);
    }

}
