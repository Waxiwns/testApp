// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.orderPageTabs.OrderTransactionsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static pages.orderPageTabs.OrderTransactionsPage.*;
import static utils.constants.Locale.EN;

public class OrderTransactionsPageSteps extends BaseStep {

    private OrderTransactionsPage orderTransactionsPage = new OrderTransactionsPage();

    @Step
    public void linkShouldBeVisible(String titleName, String text) {
        orderTransactionsPage.getTableElementCell(orderTransactionsPage.LINK_XPATH, titleName, EMPTY_VALUE, text).shouldBe(Condition.visible);
    }

    @Step
    public void buttonShouldBeVisible(String titleName, String text) {
        orderTransactionsPage.getTableElementCell(orderTransactionsPage.BUTTON_XPATH, titleName, EMPTY_VALUE, text).shouldBe(Condition.visible);
    }

    @Step
    public void linkClick(String titleName, String text) {
        linkClick(titleName, EMPTY_VALUE, text);
    }

    @Step
    public void buttonClick(String titleName, String text) {
        buttonClick(titleName, EMPTY_VALUE, text);
    }

    @Step
    public void linkClick(String titleName, String lineFilter, String text) {
        linkShouldBeVisible(titleName, text);
        orderTransactionsPage.getTableElementCell(orderTransactionsPage.LINK_XPATH, titleName, lineFilter, text).click();
    }

    @Step
    public void buttonClick(String titleName, String lineFilter, String text) {
        buttonShouldBeVisible(titleName, text);
        orderTransactionsPage.getTableElementCell(orderTransactionsPage.BUTTON_XPATH, titleName, lineFilter, text).click();
    }

    @Step
    public void tableTitleShouldBeVisible(String titleName) {
        orderTransactionsPage.getXpathForTableHeaderTitleInTable(titleName).shouldBe(Condition.visible);
    }

    @Step
    public void externalTransactionsButtonClick() {
        orderTransactionsPage.externalTransactionsButton.should(exist).click();
    }

    @Step
    public void headerOfExternalTransactionsPanelShouldBe() {
        orderTransactionsPage.headerOfExternalTransactionsPanel.should(exist);
    }

    @Step
    public void checkValueColorForAmount() {
        ElementsCollection result = orderTransactionsPage.getAllColumnFieldsByColumnName(AMOUNT);
        for (int i = 0; i != result.size(); i++) {
            SelenideElement cell = result.get(i);
            double value = Double.parseDouble(cell.getText().replaceAll(" SAR", EMPTY_VALUE));
            if (value > 0) {
                elementShouldHaveCssValue(cell, color, colorGreen);
                cell.shouldHave(cssClass("text-success"));
            } else {
                elementShouldHaveCssValue(cell, color, colorRed);
                cell.shouldHave(cssClass("text-danger"));
            }

        }
    }

    @Step
    public void checkValueColorForStatus() {
        ElementsCollection result = orderTransactionsPage.getAllColumnFieldsByColumnName(STATE);
        for (int i = 0; i != result.size(); i++) {
            SelenideElement cell = result.get(i);
            String value = cell.getText();
            if (value.equals(SUCCESS)) {
                elementShouldHaveCssValue(cell, color, colorGreen);
                cell.shouldHave(cssClass("text-success"));
            } else if (value.equals(FAIL)) {
                elementShouldHaveCssValue(cell, color, colorRed);
                cell.shouldHave(cssClass("text-danger"));
            }

        }
    }

    @Step
    public void noRecordsVariableShouldBe(String text) {
        orderTransactionsPage.getNoRecordsVariable(text);
    }

    @Step
    public void driverTransactionShouldHaveText(String accountValue, String operationValue, String titleName, String containedText) {
        orderTransactionsPage.getTransactionLineCell(DRIVER_CAPS, accountValue, operationValue, titleName).shouldBe(visible).shouldHave(text(containedText));
    }

    @Step
    public void merchantTransactionShouldHaveText(String accountValue, String operationValue, String titleName, String containedText) {
        orderTransactionsPage.getTransactionLineCell(MERCHANT_CAPS, accountValue, operationValue, titleName).shouldBe(visible).shouldHave(text(containedText));
    }

    @Step
    public void customerTransactionShouldHaveText(String accountValue, String operationValue, String titleName, String containedText) {
        orderTransactionsPage.getTransactionLineCell(CUSTOMER_CAPS, accountValue, operationValue, titleName).shouldBe(visible).shouldHave(text(containedText));
    }

    @Step
    public void toYouTransactionShouldHaveText(String accountValue, String operationValue, String titleName, String containedText) {
        orderTransactionsPage.getTransactionLineCell(TO_YOU, accountValue, operationValue, titleName).shouldBe(visible).shouldHave(text(containedText));
    }

    @Step
    public void checkTableTitleTranslation(String locale) {
        if (locale.equals(EN)) {
            tableTitleShouldBeVisible(ACCOUNT);
            tableTitleShouldBeVisible(OPERATION);
            tableTitleShouldBeVisible(STATE);
            tableTitleShouldBeVisible(DESCRIPTION);
            tableTitleShouldBeVisible(AMOUNT);
            tableTitleShouldBeVisible(ID);
            tableTitleShouldBeVisible(DATE);
            tableTitleShouldBeVisible(BENEFICIARY);
            tableTitleShouldBeVisible(CREATOR);
        } else {
            tableTitleShouldBeVisible(ACCOUNT_AR);
            tableTitleShouldBeVisible(OPERATION_AR);
            tableTitleShouldBeVisible(STATE_AR);
            tableTitleShouldBeVisible(DESCRIPTION_AR);
            tableTitleShouldBeVisible(AMOUNT_AR);
            tableTitleShouldBeVisible(ID_AR);
            tableTitleShouldBeVisible(DATE_AR);
            tableTitleShouldBeVisible(BENEFICIARY_AR);
            tableTitleShouldBeVisible(CREATOR_AR);
        }
    }

    @Step
    public void checkFilterBlockTranslation(String locale) {
        if (locale.equals(EN)) {
            getFilterFieldLabelShouldHaveText(DATE_FROM_FIELD_NAME, CREATED_FROM_TO_label);
            getFilterFieldLabelShouldHaveText(OPERATION_FIELD_NAME, OPERATION_label);
            applyFilterShouldHaveText(FILTER);
        } else {
            getFilterFieldLabelShouldHaveText(DATE_FROM_FIELD_NAME, CREATED_FROM_TO_label_AR);
            getFilterFieldLabelShouldHaveText(OPERATION_FIELD_NAME, OPERATION_label_AR);
            applyFilterShouldHaveText(FILTER_AR);
        }
    }


    @Step
    public void exportToExcelButtonShouldHaveText(String text) {
        orderTransactionsPage.exportToExcelButton.shouldBe(visible).shouldHave(text(text));
    }

    @Step
    public void applyFilterShouldHaveText(String text) {
        orderTransactionsPage.applyFilter.shouldBe(visible).shouldHave(text(text));
    }

    @Step
    public void addTransactionButtonShouldHaveText(String text) {
        orderTransactionsPage.addTransactionButton.shouldBe(visible).shouldHave(text(text));
    }

    @Step
    public void cardTitleButtonShouldHaveText(String text) {
        orderTransactionsPage.cardTitle.shouldBe(visible).shouldHave(text(text));
    }

    @Step
    public void getFilterFieldLabelShouldHaveText(String elementName, String text) {
        orderTransactionsPage.getFilterFieldLabel(elementName).shouldBe(visible).shouldHave(text(text));
    }

}

