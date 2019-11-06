// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import io.qameta.allure.Step;
import pages.orderPageTabs.OrderCreateTicketPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static pages.orderPageTabs.OrderCreateTicketPage.TITLE;

public class OrderCreateTicketPageSteps extends BaseStep {

    private OrderCreateTicketPage orderCreateTicketPage = new OrderCreateTicketPage();

    @Step
    public void createTicketButtonShouldBeVisible() {
        orderCreateTicketPage.ticketSaveButtonIcon.shouldBe(visible);
    }

    @Step
    public void checkThatCreateTicketPageIsOpened() {
        orderCreateTicketPage.newTicketPanel.shouldBe(visible);
    }

    @Step
    public void createTicketPageShouldBeEnglish() {
        orderCreateTicketPage.newTicketPanel.shouldHave(text(TITLE));
    }
}
