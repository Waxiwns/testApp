// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.modalPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ExternalTransactionsModalPage {

    public SelenideElement modal = $(".modal-content");
    public SelenideElement title = $(".modal-title");
    public SelenideElement closeButton = $(".close");

    public ElementsCollection tableHeaderCells() {
        return $$(By.xpath("//*[@class = 'modal-body']//table//thead//th"));
    }

    public ElementsCollection transactionTableLineCellById(String txId) {
        return $$(By.xpath("//table//tbody//td[contains(., '" + txId + "')]/parent::tr/td"));
    }

    public SelenideElement transactionTableColumnValueById(String txId, String column) {
        int columnNum = 0;

        switch (column) {
            case ID:
                columnNum = 0;
                break;
            case DATE:
                columnNum = 1;
                break;
            case UNIQUE_IDS:
                columnNum = 2;
                break;
            case STATE:
                columnNum = 3;
                break;
            case AMOUNT:
                columnNum = 4;
                break;
            case RESULT:
                columnNum = 5;
                break;
            case PENDING:
                columnNum = 7;
                break;
            case RETRY:
                columnNum = 8;
                break;
        }

        return transactionTableLineCellById(txId).get(columnNum);
    }

    //    page text
    public static String TITLE(String txId) {
        return "External Transactions (for internal transaction #" + txId + ")";
    }

    // Table header cells
    public static final String ID = "ID";
    public static final String DATE = "Date";
    public static final String UNIQUE_IDS = "Unique ID's";
    public static final String STATE = "State";
    public static final String AMOUNT = "Amount";
    public static final String RESULT = "Result";
    public static final String PENDING = "Pending";
    public static final String RETRY = "Retry";
}