// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.TablePage;
import utils.BaseStep;

public class TablePageSteps extends BaseStep {

    TablePage tablePage = new TablePage();

    private final String LINK_XPATH = "a";
    private final String BUTTON_XPATH = "button";
    private final String TEXT_XPATH = "self::*";

    @Step
    public void tableLinkShouldBeVisible(String titleName, String text) {
        tablePage.getTableElementCell(LINK_XPATH, titleName, "", text).shouldBe(Condition.visible);
    }

    @Step
    public void tableButtonShouldBeVisible(String titleName, String text) {
        tablePage.getTableElementCell(BUTTON_XPATH, titleName, "", text).shouldBe(Condition.visible);
    }

    @Step
    public void tableLinkClick(String titleName, String text) {
        tableLinkClick(titleName, "", text);
    }

    @Step
    public void tableButtonClick(String titleName, String text) {
        tableButtonClick(titleName, "", text);
    }

    @Step
    public void tableLinkClick(String titleName, String lineFilter, String text) {
        tableLinkShouldBeVisible(titleName, text);
        tablePage.getTableElementCell(LINK_XPATH, titleName, lineFilter, text).click();
    }

    @Step
    public void tableButtonClick(String titleName, String lineFilter, String text) {
        tableButtonShouldBeVisible(titleName, text);
        tablePage.getTableElementCell(BUTTON_XPATH, titleName, lineFilter, text).click();
    }

    @Step
    public void tableTextShouldHaveText(String titleName, String text) {
        tableTextShouldHaveText(titleName, "", text);
    }

    @Step
    public void tableTextShouldHaveText(String titleName, String lineFilter, String text) {
        tablePage.getTableElementCell(TEXT_XPATH, titleName, lineFilter, text).shouldBe(Condition.visible);
    }

    @Step
    public void tableTitleShouldBe(String titleName) {
        tablePage.getXpathForTableHeaderTitleInTable(titleName).shouldBe(Condition.visible);
    }

}
