// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.modalPagesSteps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.modalPages.NotificationModalPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.log;
import static pages.modalPages.NotificationModalPage.*;
import static utils.constants.Locale.EN;

public class NotificationModalPageSteps extends BaseStep {

    private NotificationModalPage notificationModalPage = new NotificationModalPage();

    @Step
    public void notificationMessageShouldHaveText(String notificationMessages) {
        notificationModalPage.messageTxt.shouldHave(text(notificationMessages));
    }

    @Step
    public void notificationMessageShouldNotHaveText(String notificationMessages) {
        Assertions.assertFalse(notificationModalPage.messageTxt.getText().contains(notificationMessages));
    }

    @Step
    public void errorMessageShouldBeDisplayed() {
        notificationModalPage.error.shouldBe(visible);
    }

    @Step
    public void errorMessageShouldNotBeDisplayed() {
        notificationModalPage.error.shouldNotBe(visible);
    }

    @Step
    public void successMessageShouldBeDisplayed() {
        notificationModalPage.success.shouldBe(visible);
    }

    @Step
    public void infoMessageShouldBeDisplayed() {
        notificationModalPage.info.shouldBe(visible);
    }

    @Step
    public void modalTitleShouldHaveText(String text) {
        log(notificationModalPage.titleTxt.text());
        notificationModalPage.titleTxt.shouldHave(text(text));
    }

    @Step
    public void updateOrderLinesMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(ORDER);
        notificationMessageShouldHaveText(ORDER_LINES_HAS_BEEN_UPDATED);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void clickOnNotificationToMakeItDisappeared() {
        notificationModalPage.titleTxt.shouldBe(visible).click();
        sleep(1000);
        notificationModalPage.titleTxt.shouldNotBe(visible);
    }

    @Step
    public void deliveryOrderHaveNoItemMessageShouldBeDisplayed() {
        errorMessageShouldBeDisplayed();
        modalTitleShouldHaveText(ERROR_422_OK);
        notificationMessageShouldHaveText(DELIVERY_ORDER_HAVE_NO_ITEMS);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void switchToAnotherOrderMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(SWITCH_TO_ANOTHER_ORDER);
        notificationMessageShouldHaveText(YOU_SUCCESS_SWITCH_TO_ANOTHER_ORDER);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void costCorrectionsUpdatedMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(SUCCESS);
        notificationMessageShouldHaveText(COST_CORRECTIONS_UPDATED);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void updateDeliveryDateMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(UPDATE_DELIVERY_DATE);
        notificationMessageShouldHaveText(DELIVERY_DATE_WAS_SUCCESSFULLY_UPDATED);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void updateOrderStatusMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(ORDER_STATUS);
        notificationMessageShouldHaveText(ORDER_STATUS_WAS_SUCCESSFULLY_UPDATED);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void updateOrderLinesMessageShouldBeDisplayed(String locale) {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(locale == EN ? ORDER_EN : ORDER_AR);
        notificationMessageShouldHaveText(locale == EN ? ORDER_LINES_HAS_BEEN_UPDATED_EN : ORDER_LINES_HAS_BEEN_UPDATED_AR);
        clickOnNotificationToMakeItDisappeared();
    }

    @Step
    public void assignDriverMessageShouldBeDisplayed() {
        successMessageShouldBeDisplayed();
        modalTitleShouldHaveText(ASSIGN_DRIVER);
        notificationMessageShouldHaveText(DRIVER_ASSIGNED_SUCCESSFULLY);
        clickOnNotificationToMakeItDisappeared();
    }
}
