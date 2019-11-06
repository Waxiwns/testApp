// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.orderPageTabs.OrderTransactionsPage1;
import utils.BaseStep;
import utils.constants.Buttons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.orderPageTabs.OrderTransactionsPage1.*;

public class OrderTransactionsPageSteps1 extends BaseStep {

    private OrderTransactionsPage1 orderTransactionsPage1 = new OrderTransactionsPage1();

    @Step
    public void tabTileShouldBeVisible() {
        orderTransactionsPage1.title.shouldBe(Condition.visible).shouldBe(Condition.text(TITLE));
    }

    @Step
    public void addTransactionButtonShouldBeVisible() {
        orderTransactionsPage1.addTransactionButton.shouldBe(Condition.visible);
    }

    @Step
    public void addTransactionButtonClick() {
        addTransactionButtonShouldBeVisible();
        orderTransactionsPage1.addTransactionButton.click();
    }

    private void operationFilterShouldBeVisible() {
        orderTransactionsPage1.operationFilterSelect.shouldBe(Condition.visible);
    }

    @Step
    public void operationFilterShouldHaveSelectedOption(String option) {
        operationFilterShouldBeVisible();
        orderTransactionsPage1.operationFilterSelect.shouldHave(Condition.value(option));
    }

    @Step
    public void operationFilterShouldBeEmpty() {
        operationFilterShouldBeVisible();
        orderTransactionsPage1.operationFilterSelect.shouldHave(Condition.value(ZERO));
    }

    @Step
    public void operationFilterSelectOption(String option) {
        operationFilterShouldBeVisible();
        orderTransactionsPage1.operationFilterSelect.selectOptionByValue(option);
    }

    @Step
    public void applyFilterBtnShouldBeVisible() {
        orderTransactionsPage1.applyFilterBtn.shouldBe(Condition.visible);
        orderTransactionsPage1.applyFilterBtn.shouldHave(Condition.text(Buttons.FILTER.getValue()));
    }

    @Step
    public void applyFilterBtnClick() {
        applyFilterBtnShouldBeVisible();
        orderTransactionsPage1.applyFilterBtn.click();
    }

    @Step
    public void resetFilterBtnShouldBeVisible() {
        orderTransactionsPage1.resetFilterBtn.shouldBe(Condition.visible);
    }

    @Step
    public void resetFilterBtnClick() {
        applyFilterBtnShouldBeVisible();
        orderTransactionsPage1.resetFilterBtn.click();
    }

    @Step
    public void tableShouldBeEmpty() {
        orderTransactionsPage1.transactionTableColumn(ID).shouldHave(CollectionCondition.size(1));
        orderTransactionsPage1.transactionTableColumn(ID).get(0).shouldHave(Condition.text(NO_RECORDS_AVAILABLE));
    }

    @Step
    public void tableLinesOperationColumnShouldHaveText(String option) {
        int size = orderTransactionsPage1.transactionTableColumn(OPERATION).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1)).size();

        //expect table values by OPERATION column
        for (int i = 0; i < size; i++) {
            orderTransactionsPage1.transactionTableColumn(OPERATION).get(0).shouldHave(Condition.text(option));
        }
    }

    @Step
    public void exportToExcelButtonShouldBeVisible() {
        orderTransactionsPage1.exportToExcelButton.shouldBe(Condition.visible);
    }

    @Step
    public void exportToExcelButtonClick() {
        exportToExcelButtonShouldBeVisible();
        orderTransactionsPage1.exportToExcelButton.click();
    }

    @Step
    public void checkTableHeaderCells() {
        orderTransactionsPage1.tableHeaderCells().shouldHaveSize(9);
        orderTransactionsPage1.tableHeaderCells().get(0).shouldHave(Condition.text(ID));
        orderTransactionsPage1.tableHeaderCells().get(1).shouldHave(Condition.text(DATE));
        orderTransactionsPage1.tableHeaderCells().get(2).shouldHave(Condition.text(BENEFICIARY));
        orderTransactionsPage1.tableHeaderCells().get(3).shouldHave(Condition.text(CREATOR));
        orderTransactionsPage1.tableHeaderCells().get(4).shouldHave(Condition.text(ACCOUNT));
        orderTransactionsPage1.tableHeaderCells().get(5).shouldHave(Condition.text(OPERATION));
        orderTransactionsPage1.tableHeaderCells().get(6).shouldHave(Condition.text(STATE));
        orderTransactionsPage1.tableHeaderCells().get(7).shouldHave(Condition.text(DESCRIPTION));
        orderTransactionsPage1.tableHeaderCells().get(8).shouldHave(Condition.text(AMOUNT));
    }

    @Step
    public void tableHeaderCellByTextClickToSort(String columnName) {
        orderTransactionsPage1.tableHeaderCellByText(columnName).shouldBe(Condition.visible).click();
    }

    @Step
    public void transactionsTableShouldHaveSize(int num) {
        sleep(2000); // wait for download all transactions
        orderTransactionsPage1.transactionTableLines().shouldHaveSize(num);
    }

    @Step   // first value should be transaction id
    public void checkTransactionTableLineById(String... values) {
        for (int i = 1; i < values.length; i++) {
            if (i == 8) {// for amount field
                String tableAmount = Double.parseDouble(orderTransactionsPage1.transactionTableColumnById(values[0]).get(i).getText().split(" ")[0]) + " " + SAR;
                assertTrue(tableAmount.contains(values[i]));
            } else
                orderTransactionsPage1.transactionTableColumnById(values[0]).get(i).shouldHave(Condition.text(values[i]));
        }
    }

    @Step
    public void transactionTableColumnValueByIdShouldHaveText(String txId, String columnName, String text) {
        if (columnName.equals(AMOUNT)) {// for amount field
            String tableAmount = Double.parseDouble(orderTransactionsPage1.transactionTableColumnValueById(txId, columnName).getText().split(" ")[0]) + " " + SAR;
            assertTrue(tableAmount.contains(text));
        } else
            orderTransactionsPage1.transactionTableColumnValueById(txId, columnName).shouldHave(Condition.text(text));
    }

    @Step
    public void transactionTableBeneficiaryLinkByIdClick(String txId) {
        orderTransactionsPage1.transactionTableBeneficiaryLinkById(txId).shouldBe(Condition.visible).click();
    }

    @Step
    public void transactionTableCreatorLinkByIdClick(String txId) {
        orderTransactionsPage1.transactionTableCreatorLinkById(txId).shouldBe(Condition.visible).click();
    }

    @Step
    public void transactionTableDescriptionLinkByIdClick(String txId) {
        orderTransactionsPage1.transactionTableDescriptionLinkById(txId).shouldBe(Condition.visible).click();
    }

    @Step
    public void openExternalTransactionsButtonByIdShouldBeVisible(String txId) {
        orderTransactionsPage1.openExternalTransactionsButtonById(txId).shouldBe(Condition.visible);
    }

    @Step
    public void openExternalTransactionsButtonByIdClick(String txId) {
        openExternalTransactionsButtonByIdShouldBeVisible(txId);
        orderTransactionsPage1.openExternalTransactionsButtonById(txId).click();
    }

    @Step
    public void transactionAmountTextShouldBeRed(String txId) {
        elementShouldHaveCssValue(orderTransactionsPage1.transactionTableColumnValueById(txId, orderTransactionsPage1.AMOUNT), color, colorRed);
    }

    @Step
    public void transactionAmountTextShouldBeGreen(String txId) {
        elementShouldHaveCssValue(orderTransactionsPage1.transactionTableColumnValueById(txId, orderTransactionsPage1.AMOUNT), color, colorGreen);
    }

    @Step
    public void transactionTableHeaderSortUpArrowShouldBeVisible(String columnName) {
        orderTransactionsPage1.transactionTableHeaderSortUpArrow(columnName);
    }

    @Step
    public void transactionTableHeaderSortDownArrowShouldBeVisible(String columnName) {
        orderTransactionsPage1.transactionTableHeaderSortDownArrow(columnName);
    }

    @Step
    public void transactionsTableShouldBeSortUpByColumn(boolean sortUp, String columnName) {
        orderTransactionsPage1.transactionTableColumn(columnName).shouldHave(CollectionCondition.sizeGreaterThan(1));

        // expect arrow up/down
        if (sortUp) transactionTableHeaderSortUpArrowShouldBeVisible(columnName);
        else transactionTableHeaderSortDownArrowShouldBeVisible(columnName);
        sleep(2000);

        //expect table values by column name
        for (int i = 0; i < orderTransactionsPage1.transactionTableColumn(columnName).size() - 1; i++) {
            String firstElementText = orderTransactionsPage1.transactionTableColumn(columnName).get(i).getText();
            String secondElementText = orderTransactionsPage1.transactionTableColumn(columnName).get(i + 1).getText();

            if (columnName.equals(ID)) {
                int first = Integer.parseInt(firstElementText);
                int second = Integer.parseInt(secondElementText);

                if (sortUp) assertTrue(first < second, "Id column not sorted");
                else assertTrue(first > second, "Id column not sorted");
            } else if (columnName.equals(DATE)) {
                try {
                    long first = new SimpleDateFormat(txDateTimeFormat, Locale.ENGLISH).parse(firstElementText).getTime();
                    long second = new SimpleDateFormat(txDateTimeFormat, Locale.ENGLISH).parse(secondElementText).getTime();

                    if (sortUp) assertTrue(first <= second, "Date column not sorted");
                    else assertTrue(first >= second, "Date column not sorted");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (columnName.equals(AMOUNT)) {
                double first = Double.parseDouble(firstElementText.split(" ")[0]);
                double second = Double.parseDouble(secondElementText.split(" ")[0]);

                if (sortUp) assertTrue(first <= second, "Amount column not sorted");
                else assertTrue(first >= second, "Amount column not sorted");
            }
        }
    }
}