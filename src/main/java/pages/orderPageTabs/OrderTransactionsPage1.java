// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utils.constants.Buttons.ADD_TRANSACTION;
import static utils.constants.Buttons.EXPORT_TO_EXCEL;

public class OrderTransactionsPage1 {

    public SelenideElement title = $(".card-title");
    public SelenideElement addTransactionButton = $(By.xpath("//button[contains(., '" + ADD_TRANSACTION + "')]"));
    public SelenideElement exportToExcelButton = $(By.xpath("//button[contains(., '" + EXPORT_TO_EXCEL.getValue() + "')]"));
    public SelenideElement operationFilterSelect = $(By.xpath("//label[text() = '" + OPERATION + "']/parent::*[@class = 'filter-order-form-group']//*[@name = 'txOperationType']"));
    public SelenideElement applyFilterBtn = $("[title='Apply filter']");
    public SelenideElement resetFilterBtn = $("[title='Reset filter']");

    public ElementsCollection tableHeaderCells() {
        return $$(By.xpath("//table//thead//th"));
    }

    public SelenideElement tableHeaderCellByText(String text) {
        return $(By.xpath("//table//thead//th[contains(., '" + text + "')]"));
    }

    public SelenideElement transactionTableHeaderSortUpArrow(String columnName) {
        return $(By.xpath("//table//thead//th[contains(., '" + columnName + "')]//*[@class = 'k-icon k-i-sort-asc-sm']"));
    }

    public SelenideElement transactionTableHeaderSortDownArrow(String columnName) {
        return $(By.xpath("//table//thead//th[contains(., '" + columnName + "')]//*[@class = 'k-icon k-i-sort-desc-sm']"));
    }

    private int getColumnNumByColumnName(String columnName) {
        switch (columnName) {
            case DATE:
                return 1;
            case BENEFICIARY:
                return 2;
            case CREATOR:
                return 3;
            case ACCOUNT:
                return 4;
            case OPERATION:
                return 5;
            case STATE:
                return 6;
            case DESCRIPTION:
                return 7;
            case AMOUNT:
                return 8;
            default:
                return 0;
        }
    }

    public ElementsCollection transactionTableColumn(String columnName) {
        int columnNum = getColumnNumByColumnName(columnName) + 1;

        return $$(By.xpath("//table//tbody//tr/td[" + columnNum + "]"));
    }

    public ElementsCollection transactionTableLines() {
        return $$(By.xpath("//table//tbody//tr"));
    }

    public ElementsCollection transactionTableColumnById(String txId) {
        return $$(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td"));
    }

    public SelenideElement transactionTableColumnValueById(String txId, String columnName) {
        int columnNum = getColumnNumByColumnName(columnName);

        if (columnName.equals(AMOUNT)) return transactionTableAmountById(txId);
        return transactionTableColumnById(txId).get(columnNum);
    }

    public SelenideElement transactionTableAmountById(String txId) {
        return $(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td[9]/span"));
    }

    public SelenideElement transactionTableBeneficiaryLinkById(String txId) {
        return $(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td[3]//a"));
    }

    public SelenideElement transactionTableCreatorLinkById(String txId) {
        return $(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td[4]//a"));
    }

    public SelenideElement transactionTableDescriptionLinkById(String txId) {
        return $(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td[8]//a"));
    }

    public SelenideElement openExternalTransactionsButtonById(String txId) {
        return $(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td[1]/button"));
    }


    // Tab title
    public static final String TITLE = "Transaction list";

    // Table header cells
    public static final String ID = "ID";
    public static final String DATE = "Date";
    public static final String BENEFICIARY = "Beneficiary";
    public static final String CREATOR = "Creator";
    public static final String ACCOUNT = "Account";
    public static final String OPERATION = "Operation";
    public static final String STATE = "State";
    public static final String DESCRIPTION = "Description";
    public static final String AMOUNT = "Amount";

    // any text
    public static final String SAR = "SAR";
    public static final String ZERO = "0";
    public static final String NO_RECORDS_AVAILABLE = "No records available";
    public static final String PENALTY_DESCRIPTION = "A fine was issued by ToYou to penalize the beneficiary";
}