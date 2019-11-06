// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.taxi.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.modalPages.ExternalTransactionsModalPage;
import pages.orderPageTabs.OrderTransactionsPage;
import pages.orderPageTabs.OrderTransactionsPage1;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.constants.ApiConstants;
import utils.constants.TransactionConstants;
import utils.objects.Order;
import utils.objects.Transactions;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.logPreconditionStep;
import static core.TestStepLogger.logStep;
import static pages.orderPageTabs.OrderTransactionsPage.BALANCE;
import static pages.orderPageTabs.OrderTransactionsPage.DESCRIPTION;
import static pages.orderPageTabs.OrderTransactionsPage.ORDER_REWARD;
import static pages.orderPageTabs.OrderTransactionsPage.*;
import static pages.orderPageTabs.OrderTransactionsPage1.PENALTY_DESCRIPTION;
import static utils.constants.ApiConstants.STATE;
import static utils.constants.ApiConstants.*;
import static utils.constants.Locale.AR;
import static utils.constants.Locale.EN;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.PageIdentifiers.TRANSACTIONS;
import static utils.constants.Statuses.AT_PICK_UP;
import static utils.constants.TransactionConstants.RESERVED;
import static utils.constants.TransactionConstants.SUCCESS;
import static utils.constants.TransactionConstants.*;

public class TaxiOrderTransactionsPageTest extends BaseTest {

    String transaction;
    String transactionId;
    String transactionCreationDate;
    String transactionBeneficiaryType;
    String transactionCreator = "";
    String transactionAccountType;
    String transactionOperationType;
    String transactionRecordState;
    String transactionTxHeadDescription;
    String transactionAmount;
    Transactions transactionRecords;

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create taxi order");
    }


    private void createOrderGetTransactionAndGoToTransactionsPage(Order order, String beneficiaryType, String accountType, String txOperationType, String recordStates) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Wait for transaction");
        transactionRecords = useAPISteps.findTransactionRecords(orderId, beneficiaryType, accountType, txOperationType, recordStates);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logPreconditionStep(6, "Go to Transactions page");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logPreconditionStep(7, "Check Transactions page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(8, "Check Transactions page is active");
        orderHeaderPageSteps.isActiveTab(TRANSACTIONS);
        orderTransactionsPageSteps1.tabTileShouldBeVisible();

        logPreconditionStep(9, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();
    }

    @Test
    @Description("Check the possibility to open the 'External Transactions' window for external transactions in 'ID' column")
    public void checkThePossibilityToOpenTheExternalTransactionsWindowForExternalTransactionsInIDColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);

        logStep(3, "Open external transactions modal by id #" + transactionId);
        orderTransactionsPageSteps1.openExternalTransactionsButtonByIdClick(transactionId);
        externalTransactionsModalPageSteps.titleShouldBeVisible(transactionId);

        logStep(4, "Check external transactions table header");
        externalTransactionsModalPageSteps.checkTableHeaderCells();

        logStep(4, "Check close external transactions modal");
        externalTransactionsModalPageSteps.closeButtonClick();
    }

    @Test
    @Description("Check the 'Date' column")
    public void checkTheDateColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " date column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.DATE, transactionCreationDate);
    }

    @Test
    @Description("Check the 'Beneficiary' column")
    public void checkTheBeneficiaryColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " beneficiary column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, transactionBeneficiaryType);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, firstCustomer.getFullName());

        logStep(3, "Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column");
        orderTransactionsPageSteps1.transactionTableBeneficiaryLinkByIdClick(transactionId);
        customerDetailsPageSteps.pageTitleShouldBeVisible();
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Check the External transactions state")
    public void checkTheExternalTransactionsState() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        String extTransactionId = transactionRecords.getExtTxKey(ApiConstants.ID);
        String extTransactionRecordState = transactionRecords.getExtTxKey(STATE);
        String extTransactionCreationDate = transactionRecords.getExtTxDateKey(ApiConstants.CREATION_DATE);
        String extTransactionProcessingDate = transactionRecords.getExtTxDateKey(ApiConstants.PROCESSING_DATE);
        String extTransactionFinishedDate = transactionRecords.getExtTxDateKey(ApiConstants.FINISHED_DATE);

        logStep(1, "Open external transactions modal by id #" + transactionId);
        orderTransactionsPageSteps1.openExternalTransactionsButtonByIdClick(transactionId);
        externalTransactionsModalPageSteps.titleShouldBeVisible(transactionId);

        logStep(2, "Check external transactions table header");
        externalTransactionsModalPageSteps.checkTableHeaderCells();

        logStep(3, "Check external transaction #" + extTransactionId + " values");
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.ID, extTransactionId);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.STATE, SUCCESS);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.STATE, extTransactionRecordState);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, extTransactionCreationDate);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, extTransactionProcessingDate);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, extTransactionFinishedDate);
    }

    @Test
    @Description("Check the ability to proceed to the 'Driver Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToTheDriverDetailsPageViaHyperlinkInBeneficiaryColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.DRIVER, TransactionConstants.BALANCE, TransactionConstants.ORDER_REWARD, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " beneficiary column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, transactionBeneficiaryType);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, secondDriver.getFullName());

        logStep(2, "Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column");
        orderTransactionsPageSteps1.transactionTableBeneficiaryLinkByIdClick(transactionId);

        driverDetailsSteps.driverFirstNameEnShouldHaveText(secondDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(secondDriver.getLastName());
    }

    @Test
    @Description("Check possibility to proceed to the 'Profile' tab of the order via Order Number in the 'Description' column")
    public void checkPossibilityToProceedToTheProfileTabOfTheOrderViaOrderNumberInTheDescriptionColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " beneficiary column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.DESCRIPTION, orderNumber);

        logStep(3, "Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column");
        orderTransactionsPageSteps1.transactionTableDescriptionLinkByIdClick(transactionId);
        orderHeaderPageSteps.isActiveTab(PROFILE);
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Check the amount column")
    public void checkTheAmountColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " amount column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.AMOUNT, transactionAmount);

        logStep(3, "Check the color of negative amount in the 'Amount' column");
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(4, "Get Driver positive transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.DRIVER, TransactionConstants.BALANCE, TransactionConstants.ORDER_REWARD);
        transactionId = transactionRecords.getTxKey(ApiConstants.ID);
        transactionAmount = transactionRecords.getTxKey(ApiConstants.AMOUNT);

        logStep(5, "Check transaction #" + transactionId + " amount column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.AMOUNT, transactionAmount);

        logStep(6, "Check the color of positive amount in the 'Amount' column");
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check possibility to sort grid via columns (ID, Date, Amount)")
    public void checkPossibilityToSortGridViaColumns() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check default sort down by Date column");
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.DATE);

        logStep(3, "Check possibility to sort grid via ID column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.ID);

        logStep(4, "Check possibility to sort grid via Date column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.DATE);

        logStep(5, "Check possibility to sort grid via Amount column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.AMOUNT);
    }

    @Test
    @Description("Check possibility to filter records with 'Operation' filter (no value should be preselected by default)")
    public void checkPossibilityToFilterRecordsWithOperationFilter() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check no value should be preselected by default");
        orderTransactionsPageSteps1.operationFilterShouldBeEmpty();

        logStep(2, "Check selected filter option");
        orderTransactionsPageSteps1.operationFilterSelectOption(transactionOperationType);
        orderTransactionsPageSteps1.applyFilterBtnClick();
        orderTransactionsPageSteps1.operationFilterShouldHaveSelectedOption(transactionOperationType);

        logStep(3, "Check table Operation column");
        orderTransactionsPageSteps1.tableLinesOperationColumnShouldHaveText(transactionOperationType);

        logStep(4, "Check selected filter option");
        orderTransactionsPageSteps1.operationFilterSelectOption(PENALTY);
        orderTransactionsPageSteps1.applyFilterBtnClick();
        orderTransactionsPageSteps1.operationFilterShouldHaveSelectedOption(PENALTY);

        logStep(5, "Check empty table");
        orderTransactionsPageSteps1.tableShouldBeEmpty();
    }

    @Test
    @Description("Check the ability to proceed to 'System User Details' page via hyperlink in the 'Creator' field")
    public void checkTheAbilityToProceedToSystemUserDetailsPageViaHyperlinkInTheCreatorField() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Create manual PENALTY transaction");
        Transactions manualTransaction = useAPISteps.createManualTransactionForCustomer(firstCustomer, PENALTY, AMOUNT_200, orderId);
        transactionId = manualTransaction.getTxKey(ApiConstants.ID);
        transactionCreationDate = manualTransaction.getTxDateKey(ApiConstants.CREATION_DATE);
        transactionBeneficiaryType = manualTransaction.getTxKey(ApiConstants.BENEFICIARY_TYPE);
        transactionCreator = admin.getFullName();
        transactionAccountType = manualTransaction.getTxKey(ApiConstants.ACCOUNT_TYPE);
        transactionOperationType = manualTransaction.getTxKey(ApiConstants.TX_OPERATION_TYPE);
        transactionRecordState = manualTransaction.getTxKey(ApiConstants.RECORD_STATE);
        transactionTxHeadDescription = manualTransaction.getTxKey(ApiConstants.TX_HEAD_DESCRIPTION);
        transactionAmount = manualTransaction.getTxKey(ApiConstants.AMOUNT);
        refresh();

        logStep(3, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OPERATION, PENALTY);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, DESCRIPTION, PENALTY_DESCRIPTION);

        logStep(4, "Check 'System User Details' hyperlink");
        orderTransactionsPageSteps1.transactionTableCreatorLinkByIdClick(transactionId);
        systemUserDetailsPageSteps.systemUserFirstNameShouldHaveValue(admin.getFirstName());
        systemUserDetailsPageSteps.systemUserLastNameShouldHaveValue(admin.getLastName());
    }

    @Test
    @Description("Check the 'Account' column")
    public void checkAccountColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Account” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(ACCOUNT);
    }

    @Test
    @Description("Check the 'Description' column")
    public void checkDescriptionColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Description” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(DESCRIPTION);
    }

    @Test
    @Description("Check the 'Operation' column")
    public void checkOperationColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Make sure that the column “Operation” contains the correct text");
        orderTransactionsPageSteps.tableTitleShouldBeVisible(OrderTransactionsPage.OPERATION);
    }

    @Test
    @Description("Check the success statuses")
    public void checkSuccessStatuses() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Check the success statuses");
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(OUTPUT_VAT, OUTPUT_VAT, OrderTransactionsPage.STATE, OrderTransactionsPage.SUCCESS);
        orderTransactionsPageSteps.driverTransactionShouldHaveText(BALANCE, ORDER_REWARD, OrderTransactionsPage.STATE, OrderTransactionsPage.SUCCESS);
    }

    @Test
    @Description("Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToCustomerDetails() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);
        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check link to the Customer Details in Beneficiary column");
        orderTransactionsPageSteps.linkClick(BENEFICIARY, CUSTOMER_CAPS, firstCustomer.getFullName());

        logStep(3, "Check that we are on the page of the selected client");
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Check there is a transaction with \"TOYOU\" Beneficiary, \"OUTPUT_VAT\" Account and Operation for completed orders")
    public void checkThereAreTransactionsWithToYouBeneficiary() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Go to the order description page transaction tab");
        switchToTheOrdersPage(orderId, TRANSACTIONS.getValue());

        logStep(2, "Check description have order completed text ");
        orderTransactionsPageSteps.toYouTransactionShouldHaveText(OUTPUT_VAT, OUTPUT_VAT, DESCRIPTION, ORDER_COMPLETED);
    }

    @Test
    @Description("Check that the 'PROMO_BUDGET' transaction is displayed as first transaction if place order with valid promo code and known price")
    public void checkThatThePROMO_BUDGETTransactionIsDisplayedAsFirstTransactionIfPlaceOrderWithValidPromoCodeSndKnownPrice() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderByFirstCustomer(testInitValues.promoSmokeTaxi()), CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, TransactionConstants.RESERVED);

        logStep(1, "Check transactions table header");
        orderTransactionsPageSteps1.checkTableHeaderCells();

        logStep(2, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that the old 'PROMO_BUDGET' transaction is displayed in the 'Canceled status if the new 'PROMO_BUDGET' transaction was created")
    public void checkThatTheOldPROMO_BUDGETTransactionIsDisplayedInTheCanceledStatusIfTheNewPROMO_BUDGETTransactionWasCreated() {

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderAcceptedBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check RESERVED status for transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);

        logStep(2, "Get CAMPAIGN PROMO_BUDGET transaction values");
        Transactions transactionRecordsSecond = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);
        refresh();

        String transactionIdSecond = transactionRecordsSecond.getId();
        String transactionCreationDateSecond = transactionRecordsSecond.getCreationDate();
        String transactionBeneficiaryTypeSecond = transactionRecordsSecond.getBeneficiaryType();
        String transactionAccountTypeSecond = transactionRecordsSecond.getAccountType();
        String transactionOperationTypeSecond = transactionRecordsSecond.getOperationType();
        String transactionRecordStateSecond = transactionRecordsSecond.getRecordState();
        String transactionTxHeadDescriptionSecond = transactionRecordsSecond.getHeadDescription();
        String transactionAmountSecond = transactionRecordsSecond.getAmount();

        logStep(3, "Check transaction #" + transactionIdSecond + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionIdSecond, transactionCreationDateSecond, transactionBeneficiaryTypeSecond, transactionCreator, transactionAccountTypeSecond, transactionOperationTypeSecond, transactionRecordStateSecond, transactionTxHeadDescriptionSecond, transactionAmountSecond);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionIdSecond);

        logStep(4, "Check CAMPAIGN PROMO_BUDGET CANCELED transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, CANCELED, transactionTxHeadDescription, transactionAmount);
    }

    @Test
    @Description("Check that the 'Cancelled' status is displayed in the 'PROMO_BUDGET' transaction if cancel order with promo code")
    public void checkThatTheCancelledStatusIsDisplayedInThePROMO_BUDGETTransactionIfCancelOrderWithPromoCode() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderAcceptedBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check RESERVED status for transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        useAPISteps.cancelOrder(order);

        logStep(2, "Wait CAMPAIGN PROMO_BUDGET CANCELED transaction values");
        transactionRecords = useAPISteps.findTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, CANCELED);
        String transactionRecordState = transactionRecords.getRecordState();
        refresh();

        logStep(3, "Check CAMPAIGN PROMO_BUDGET CANCELED transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
    }

    @Test
    @Description("Check functionality of the 'Delivery fee' promo code with percentage discount")
    public void checkFunctionalityOfTheDeliveryFeePromoCodeWithPercentageDiscount() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiCCPromoOrderBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        logStep(2, "Get TOYOU PROMO_PAY_FOR_DELIVERY transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_ORDER);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(3, "Check that the 'PROMO_PAY_FOR_ORDER' transaction #" + transactionId + " is displayed if complete order with the 'Delivery fee' promo code");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(4, "Get CAMPAIGN PROMO_BUDGET CANCELED transaction values");
        transactionRecords = useAPISteps.findTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, TransactionConstants.CANCELED);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(5, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check functionality of the 'Delivery fee' promo code with value discount")
    public void checkFunctionalityOfTheDeliveryFeePromoCodeWithValueDiscount() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiCCPromoOrderBySecondDriver(testInitValues.promoSmokeTaxi()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(2, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(3, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that correct beneficiaryId is displayed in the 'PROMO_PAY_FOR_ORDER' if place order with promo code")
    public void checkBeneficiaryIdIsDisplayedWithPromoCode() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiCCPromoOrderBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check that TOYOU beneficiaryId is displayed in the 'PROMO_PAY_FOR'.     Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
    }

    @Test
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_ORDER' transaction if change pick-up/drop-off to the further one for order with the 'Delivery fee' promo code")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_ORDERTransactionIfChangeDropOffToTheFurtherOneForOrderWithTheDeliveryFeePromoCode() {
        int deliveryFee = 100;
        String promoDeliveryFee = String.valueOf(deliveryFee / 2);

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderAcceptedBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check RESERVED status for transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        logStep(2, "Change dropOff address to the further and set larger delivery fee");
        useAPISteps.updateOrderAddressByAdmin(DROP_OFF, orderId, useAPISteps.locationKharkivShevchenka334);
        useAPISteps.updateCostCorrectionsForOrder(orderId, DELIVERY_FEE, deliveryFee, ABSOLUTE);

        logStep(3, "Complete order");
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);

        logStep(4, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);
        refresh();
        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(5, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, promoDeliveryFee);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that the one 'PROMO_PAY_FOR_ORDER' transaction is displayed if complete order with valid promo code, for which ToYou/Merchant is 100% responsible")
    public void checkThatTheOnePROMO_PAY_FORTransactionIsDisplayedIfCompleteOrderWithValidPromoCodeForWhichToYouIs100Responsible() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiCCPromoOrderBySecondDriver(testInitValues.promoTaxi100Discount()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);
    }

    @Test
    @Description("Check that debt/ToYou credits don't affect the '100%' promo code discount if place order with debt or bonus")
    public void checkThatToYouCreditsDontAffectThe100PromoCodeDiscountIfPlaceOrderWithBonus() {
        int promoAmount = 10;
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiCCUseBalancePromoOrderBySecondDriver(testInitValues.promoTaxi100Discount()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, String.valueOf(promoAmount));
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(2, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(3, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, String.valueOf(promoAmount));
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_ORDER' if change data on the 'Cost calculation' tab for order with the 'Order total cost' promo code")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_ORDERTransactionIfChangeDataInCostCalculationTabForOrderWithThePromoCode100percent() {
        int serviceFee = 50;
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderAcceptedBySecondDriver(testInitValues.promoTaxi100Discount()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(2, "Change dropOff address");
        useAPISteps.updateOrderAddressByAdmin(DROP_OFF, orderId, useAPISteps.locationKharkivShevchenka334);
        useAPISteps.updateCostCorrectionsForOrder(orderId, DELIVERY_FEE, serviceFee, ABSOLUTE);

        logStep(3, "Complete order");
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);

        logStep(2, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);
        refresh();
        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(3, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check the 'Order total cost' promo code functionality if cancel order with promo code after driver arrived on pick-up")
    public void checkOrderTotalCostIfCancelOrderWithPromoAfterDriverArrivedOnPickUp() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createTaxiCCPromoOrderAcceptedBySecondDriver(testInitValues.promoTaxiPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check RESERVED status for transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        secondDriver.updateActiveOrderStatus(AT_PICK_UP.getValue());

        useAPISteps.cancelOrder(order);

        logStep(2, "Wait CAMPAIGN PROMO_BUDGET CANCELED transaction values");
        transactionRecords = useAPISteps.findTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, CANCELED);
        String transactionRecordState = transactionRecords.getRecordState();
        refresh();

        logStep(3, "Check CAMPAIGN PROMO_BUDGET CANCELED transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
    }

    //-----------------------------------------------------------------------------------
    @Test
    @Description("Check possibility to export order transactions to Exel")
    public void checkThePossibilityToExportOrderTransactionsToExel() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check Excel button and click it ");
        orderTransactionsPageSteps1.exportToExcelButtonShouldBeVisible();
        orderTransactionsPageSteps1.exportToExcelButtonClick();
        sleep(5000);
    }
    //------------------------------------------------------------------------------------

    @Bug
    @Issue("BACK-5200")
    @Test
    @Description("Check the translation English/Arabic")
    public void checkTheTranslationEnglishArabic() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedTaxiOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Change translation EN");
        orderTransactionsPageSteps.checkTableTitleTranslation(EN);
        orderTransactionsPageSteps.checkFilterBlockTranslation(EN);
        orderTransactionsPageSteps.cardTitleButtonShouldHaveText(CARD_TITLE);
        orderTransactionsPageSteps.addTransactionButtonShouldHaveText(ADD_TRANSACTION);
        orderTransactionsPageSteps.exportToExcelButtonShouldHaveText(EXPORT_TO_EXCEL);

        logStep(2, "Change language to the AR");
        headerPageSteps.changeLanguage();

        logStep(3, "Check translation AR");
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
}