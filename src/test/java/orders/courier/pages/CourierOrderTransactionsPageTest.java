// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.modalPages.ExternalTransactionsModalPage;
import pages.orderPageTabs.OrderTransactionsPage1;
import utils.BaseTest;
import utils.constants.TransactionConstants;
import utils.objects.Order;
import utils.objects.Transactions;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderTransactionsPage1.OPERATION;
import static pages.orderPageTabs.OrderTransactionsPage1.PENALTY_DESCRIPTION;
import static utils.constants.ApiConstants.*;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.PageIdentifiers.TRANSACTIONS;
import static utils.constants.TransactionConstants.*;

public class CourierOrderTransactionsPageTest extends BaseTest {

    Transactions transactionRecords;
    String transactionId;
    String transactionCreationDate;
    String transactionBeneficiaryType;
    String transactionCreator = "";
    String transactionAccountType;
    String transactionOperationType;
    String transactionRecordState;
    String transactionTxHeadDescription;
    String transactionAmount;

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create courier order");
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
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);

        logStep(2, "Open external transactions modal by id #" + transactionId);
        orderTransactionsPageSteps1.openExternalTransactionsButtonByIdClick(transactionId);
        externalTransactionsModalPageSteps.titleShouldBeVisible(transactionId);

        logStep(3, "Check external transactions table header");
        externalTransactionsModalPageSteps.checkTableHeaderCells();

        logStep(4, "Check close external transactions modal");
        externalTransactionsModalPageSteps.closeButtonClick();
    }

    @Test
    @Description("Check the 'Date' column")
    public void checkTheDateColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " date column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.DATE, transactionCreationDate);
    }

    @Test
    @Description("Check the 'Beneficiary' column")
    public void checkTheBeneficiaryColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " beneficiary column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, transactionBeneficiaryType);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.BENEFICIARY, firstCustomer.getFullName());

        logStep(2, "Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column");
        orderTransactionsPageSteps1.transactionTableBeneficiaryLinkByIdClick(transactionId);
        customerDetailsPageSteps.pageTitleShouldBeVisible();
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Check the External transactions state")
    public void checkTheExternalTransactionsState() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), CUSTOMER, CC, PAY_FOR_ORDER, SUCCESS);

        String extTransactionId = transactionRecords.getExtId();

        logStep(1, "Open external transactions modal by id #" + transactionId);
        orderTransactionsPageSteps1.openExternalTransactionsButtonByIdClick(transactionId);
        externalTransactionsModalPageSteps.titleShouldBeVisible(transactionId);

        logStep(2, "Check external transactions table header");
        externalTransactionsModalPageSteps.checkTableHeaderCells();

        logStep(3, "Check external transaction #" + extTransactionId + " values");
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.ID, extTransactionId);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.STATE, SUCCESS);
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.STATE, transactionRecords.getExtState());
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, transactionRecords.getExtCreationDate());
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, transactionRecords.getExtProcessingDate());
        externalTransactionsModalPageSteps.transactionTableColumnValueByIdShouldHaveText(extTransactionId, ExternalTransactionsModalPage.DATE, transactionRecords.getExtFinishedDate());
    }

    @Test
    @Description("Check the ability to proceed to the 'Driver Details' page via hyperlink in 'Beneficiary' column")
    public void checkTheAbilityToProceedToTheDriverDetailsPageViaHyperlinkInBeneficiaryColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.DRIVER, TransactionConstants.BALANCE, TransactionConstants.ORDER_REWARD, SUCCESS);

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
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " beneficiary column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.DESCRIPTION, orderNumber);

        logStep(2, "Check the ability to proceed to the 'Customer Details' page via hyperlink in 'Beneficiary' column");
        orderTransactionsPageSteps1.transactionTableDescriptionLinkByIdClick(transactionId);
        orderHeaderPageSteps.isActiveTab(PROFILE);
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Check possibility to proceed to the 'Profile' tab of the order via Order Number in the 'Description' column")
    public void checkTheAmountColumn() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " amount column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.AMOUNT, transactionAmount);

        logStep(2, "Check the color of negative amount in the 'Amount' column");
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(3, "Get Driver positive transaction values");
        Transactions transactionRecordsDriver = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.DRIVER, TransactionConstants.BALANCE, TransactionConstants.ORDER_REWARD);
        String transactionIdDriver = transactionRecordsDriver.getId();
        String transactionAmountDriver = transactionRecordsDriver.getAmount();

        logStep(4, "Check transaction #" + transactionIdDriver + " amount column");
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionIdDriver, OrderTransactionsPage1.AMOUNT, transactionAmountDriver);

        logStep(5, "Check the color of positive amount in the 'Amount' column");
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionIdDriver);
    }

    @Test
    @Description("Check possibility to sort grid via columns (ID, Date, Amount)")
    public void checkPossibilityToSortGridViaColumns() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Check default sort down by Date column");
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.DATE);

        logStep(2, "Check possibility to sort grid via ID column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.ID);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.ID);

        logStep(3, "Check possibility to sort grid via Date column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.DATE);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.DATE);

        logStep(4, "Check possibility to sort grid via Amount column");
        // click to sort
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(true, OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.tableHeaderCellByTextClickToSort(OrderTransactionsPage1.AMOUNT);
        orderTransactionsPageSteps1.transactionsTableShouldBeSortUpByColumn(false, OrderTransactionsPage1.AMOUNT);
    }

    @Test
    @Description("Check possibility to filter records with 'Operation' filter (no value should be preselected by default)")
    public void checkPossibilityToFilterRecordsWithOperationFilter() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

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
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierOrderBySecondDriver(), TransactionConstants.CUSTOMER, TransactionConstants.CC, TransactionConstants.PAY_FOR_ORDER, SUCCESS);

        logStep(1, "Create manual PENALTY transaction");
        Transactions manualTransaction = useAPISteps.createManualTransactionForCustomer(firstCustomer, PENALTY, AMOUNT_200, orderId);
        transactionId = manualTransaction.getId();
        transactionCreationDate = manualTransaction.getCreationDate();
        transactionBeneficiaryType = manualTransaction.getBeneficiaryType();
        transactionCreator = admin.getFullName();
        transactionAccountType = manualTransaction.getAccountType();
        transactionOperationType = manualTransaction.getOperationType();
        transactionRecordState = manualTransaction.getRecordState();
        transactionTxHeadDescription = manualTransaction.getHeadDescription();
        transactionAmount = manualTransaction.getAmount();
        refresh();

        logStep(2, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OPERATION, PENALTY);
        orderTransactionsPageSteps1.transactionTableColumnValueByIdShouldHaveText(transactionId, OrderTransactionsPage1.DESCRIPTION, PENALTY_DESCRIPTION);

        logStep(3, "Check 'System User Details' hyperlink");
        orderTransactionsPageSteps1.transactionTableCreatorLinkByIdClick(transactionId);
        systemUserDetailsPageSteps.systemUserFirstNameShouldHaveValue(admin.getFirstName());
        systemUserDetailsPageSteps.systemUserLastNameShouldHaveValue(admin.getLastName());
    }

    @Test
    @Description("Check functionality of the 'Delivery fee' promo code with percentage discount")
    public void checkFunctionalityOfTheDeliveryFeePromoCodeWithPercentageDiscount() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        logStep(2, "Get TOYOU PROMO_PAY_FOR_DELIVERY transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_DELIVERY);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(3, "Check that the 'PROMO_PAY_FOR_DELIVERY' transaction #" + transactionId + " is displayed if complete order with the 'Delivery fee' promo code");
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
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourierDeliveryValue()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_DELIVERY, SUCCESS);

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
    @Description("Check functionality of the 'Service fee' promo code with percentage discount")
    public void checkFunctionalityOfTheServiceFeePromoCodeWithPercentageDiscount() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourierServicePercentDiscount()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        logStep(2, "Get TOYOU PROMO_PAY_FOR_SERVICE_FEE transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_SERVICE_FEE);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();
        transactionAmount = transactionRecords.getAmount();

        logStep(3, "Check that the 'PROMO_PAY_FOR_SERVICE_FEE' transaction #" + transactionId + " is displayed if complete order with the 'Service fee' promo code");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);
    }

    @Test
    @Description("Check functionality of the 'Service fee' promo code with value discount")
    public void checkFunctionalityOfTheServiceFeePromoCodeWithValueDiscount() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourierServiceDiscount()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_SERVICE_FEE, SUCCESS);

        logStep(1, "Check that TOYOU beneficiaryId is displayed in the 'PROMO_PAY_FOR'.     Check transaction #" + transactionId + " values");
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

        logStep(3, "Check SUCCESS status transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that the 'PROMO_BUDGET' transaction is displayed as first transaction if place order with valid promo code and known price")
    public void checkThatThePROMO_BUDGETTransactionIsDisplayedAsFirstTransactionIfPlaceOrderWithValidPromoCodeSndKnownPrice() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderByFirstCustomer(testInitValues.promoCourierDeliveryValue()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that the old 'PROMO_BUDGET' transaction is displayed in the 'Canceled status if the new 'PROMO_BUDGET' transaction was created")
    public void checkThatTheOldPROMO_BUDGETTransactionIsDisplayedInTheCanceledStatusIfTheNewPROMO_BUDGETTransactionWasCreated() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

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
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

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
    @Description("Check that the one 'PROMO_PAY_FOR...' transaction is displayed if complete order with valid promo code, for which ToYou/Merchant is 100% responsible")
    public void checkThatTheOnePROMO_PAY_FORTransactionIsDisplayedIfCompleteOrderWithValidPromoCodeForWhichToYouIs100Responsible() {
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourier100Discount()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_DELIVERY, SUCCESS);

        logStep(1, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);
    }

    @Test
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_DELIVERY' transaction if change pick-up/drop-off to the further one for order with the 'Delivery fee' promo code")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_DELIVERYTransactionIfChangeDropOffToTheFurtherOneForOrderWithTheDeliveryFeePromoCode() {
        int deliveryFee = 100;
        String promoDeliveryFee = String.valueOf(deliveryFee / 2);

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

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
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_DELIVERY' transaction if change pick-up/drop-off to the closer one for order with the 'Delivery fee' promo code")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_DELIVERYTransactionIfChangeDropOffToTheCloserOneForOrderWithTheDeliveryFeePromoCode() {
        int deliveryFee = 50;
        String promoDeliveryFee = String.valueOf(deliveryFee / 2);

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Check RESERVED status for transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.transactionsTableShouldHaveSize(1);
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, transactionAmount);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);

        logStep(2, "Change dropOff address to the further and set smaller delivery fee");
        useAPISteps.updateOrderAddressByAdmin(DROP_OFF, orderId, locationKharkivPushkinska10);
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
    @Description("Check that debt/ToYou credits don't affect the 'Delivery fee' promo code discount if place order with debt or bonus")
    public void checkThatToYouCreditsDontAffectTheDeliveryFeePromoCodeDiscountIfPlaceOrderWithBonus() {
        int promoAmount = 10;
        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCUseBalancePromoOrderBySecondDriver(testInitValues.promoCourierDeliveryValue()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_DELIVERY, SUCCESS);

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
    @Description("Check that debt/ToYou credits don't affect the 'Service fee' promo code discount if place order with debt or bonus")
    public void checkThatToYouCreditsDontAffectTheServiceFeePromoCodeDiscountIfPlaceOrderWithBonus() {
        int promoAmount = 5;

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCompletedCourierCCPromoOrderBySecondDriver(testInitValues.promoCourierServiceDiscount()), TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_SERVICE_FEE, SUCCESS);

        logStep(1, "Check that TOYOU beneficiaryId is displayed in the 'PROMO_PAY_FOR'.     Check transaction #" + transactionId + " values");
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

        logStep(3, "Check SUCCESS status transaction #" + transactionId + " and other values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, String.valueOf(promoAmount));
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_SERVICE_FEE' transaction if change service fee by the 'Cost calculation' tab for order with the'Service fee' promo code")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_SERVICE_FEETransactionIfChangeServiceFeeByTheCostCalculationTabForOrderWithTheServiceFeePromoCode() {
        int serviceFee = 50;
        String promoServiceFee = "5";

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierServiceDiscount()), "", "", "", "");

        logStep(1, "Update service fee value For Order");
        useAPISteps.updateCostCorrectionsForOrder(orderId, SERVICE_FEE, serviceFee, ABSOLUTE);

        logStep(2, "Complete order");
        useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);

        logStep(3, "Get TOYOU PROMO_PAY_FOR_SERVICE_FEE transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.TOYOU, TransactionConstants.BALANCE, TransactionConstants.PROMO_PAY_FOR_SERVICE_FEE);
        refresh();
        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(4, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, promoServiceFee);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(5, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(6, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, promoServiceFee);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @Test
    @Description("Check that correct amount is displayed in the 'PROMO_PAY_FOR_DELIVERY' transaction if change Fixed fee/Waiting fee/Delivery duration fee/Delivery")
    public void checkThatCorrectAmountIsDisplayedInThePROMO_PAY_FOR_DELIVERYTransactionIfChangeFixedFeeWaitingFeeDeliveryDurationFeeDelivery() {
        int deliveryFee = 50;
        int feesCount = 4;
        String promoDeliveryFee = String.valueOf(deliveryFee * feesCount * 0.5);

        createOrderGetTransactionAndGoToTransactionsPage(useAPISteps.createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(testInitValues.promoCourierDeliveryPercent()), TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET, RESERVED);

        logStep(1, "Update delivery Fees values For Order");
        useAPISteps.updateSeveralCostCorrectionsForOrder(orderId, ABSOLUTE, deliveryFee, FIXED_FEE, WAITING_FEE, DELIVERY_DURATION_FEE, DELIVERY_DISTANCE_FEE);

        logStep(2, "Complete order");
        order = useAPISteps.changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);

        logStep(3, "Get TOYOU PROMO_PAY_FOR_SERVICE_FEE transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.TOYOU, TransactionConstants.BALANCE, PROMO_PAY_FOR_DELIVERY);
        refresh();
        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(4, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, promoDeliveryFee);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeRed(transactionId);

        logStep(5, "Get CAMPAIGN PROMO_BUDGET transaction values");
        transactionRecords = useAPISteps.findSuccessTransactionRecords(orderId, TransactionConstants.CAMPAIGN, TransactionConstants.BALANCE, TransactionConstants.PROMO_BUDGET);

        transactionId = transactionRecords.getId();
        transactionCreationDate = transactionRecords.getCreationDate();
        transactionBeneficiaryType = transactionRecords.getBeneficiaryType();
        transactionAccountType = transactionRecords.getAccountType();
        transactionOperationType = transactionRecords.getOperationType();
        transactionRecordState = transactionRecords.getRecordState();
        transactionTxHeadDescription = transactionRecords.getHeadDescription();

        logStep(6, "Check transaction #" + transactionId + " values");
        orderTransactionsPageSteps1.checkTransactionTableLineById(transactionId, transactionCreationDate, transactionBeneficiaryType, transactionCreator, transactionAccountType, transactionOperationType, transactionRecordState, transactionTxHeadDescription, promoDeliveryFee);
        orderTransactionsPageSteps1.transactionAmountTextShouldBeGreen(transactionId);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
