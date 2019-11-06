// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.modalPagesSteps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.modalPages.ExternalTransactionsModalPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static pages.modalPages.ExternalTransactionsModalPage.*;

public class ExternalTransactionsModalPageSteps extends BaseStep {

    private ExternalTransactionsModalPage externalTransactionsModalPage = new ExternalTransactionsModalPage();

    @Step
    public void modalShouldBePresent() {
        externalTransactionsModalPage.modal.shouldBe(exist);
    }

    @Step
    public void modalShouldNotBePresent() {
        externalTransactionsModalPage.modal.shouldNotBe(exist);
    }

    @Step
    public void titleShouldBeVisible(String txId) {
        modalShouldBePresent();
        externalTransactionsModalPage.title.shouldBe(visible);
        externalTransactionsModalPage.title.shouldBe(text(ExternalTransactionsModalPage.TITLE(txId)));
    }

    @Step
    public void closeButtonShouldBeVisible() {
        externalTransactionsModalPage.closeButton.shouldBe(visible);
    }

    @Step
    public void closeButtonClick() {
        closeButtonShouldBeVisible();
        externalTransactionsModalPage.closeButton.click();
        modalShouldNotBePresent();
    }

    @Step
    public void checkTableHeaderCells() {
        externalTransactionsModalPage.tableHeaderCells().shouldHaveSize(8);
        externalTransactionsModalPage.tableHeaderCells().get(0).shouldHave(Condition.text(ID));
        externalTransactionsModalPage.tableHeaderCells().get(1).shouldHave(Condition.text(DATE));
        externalTransactionsModalPage.tableHeaderCells().get(2).shouldHave(Condition.text(UNIQUE_IDS));
        externalTransactionsModalPage.tableHeaderCells().get(3).shouldHave(Condition.text(STATE));
        externalTransactionsModalPage.tableHeaderCells().get(4).shouldHave(Condition.text(AMOUNT));
        externalTransactionsModalPage.tableHeaderCells().get(5).shouldHave(Condition.text(RESULT));
        externalTransactionsModalPage.tableHeaderCells().get(6).shouldHave(Condition.text(PENDING));
        externalTransactionsModalPage.tableHeaderCells().get(7).shouldHave(Condition.text(RETRY));
    }

    @Step
    public void transactionTableColumnValueByIdShouldHaveText(String txId, String columnName, String text) {
        externalTransactionsModalPage.transactionTableColumnValueById(txId, columnName).shouldHave(Condition.text(text));
    }
}