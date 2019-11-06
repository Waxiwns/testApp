// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.modalPagesSteps;

import io.qameta.allure.Step;
import pages.modalPages.DialogModal;
import utils.BaseStep;
import utils.constants.Buttons;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.sleep;
import static pages.modalPages.DialogModal.*;
import static utils.constants.Buttons.CANCEL;
import static utils.constants.Buttons.SAVE;

public class DialogModalSteps extends BaseStep {

    private DialogModal dialogModal = new DialogModal();

    private String dateTimeFormat = "MMM/yyyy/d/HH/mm";
    private String dateFormat = "yyyy-MM-dd";
    private String timeFormat = "HH:mm";
    private long toFuture = 60 * 60 * 24 * 300 + 30 * 60 * 3; // 300 days + 180 min
    private String incorrectTime = "89";
    private String incorrectTimeChar = "fu";


    @Step
    public void titleShouldBe(String title) {
        dialogModal.title(title).shouldBe(visible);
    }

    @Step
    public void cancelOrderTitleShouldBeVisible() {
        titleShouldBe(CANCEL_ORDER);
    }

    @Step
    public void cancelOrderReasonTitleShouldBeVisible() {
        cancelOrderTitleShouldBeVisible();
        dialogModal.sectionTitle(CANCEL_ORDER, REASON).shouldBe(visible);
    }

    @Step
    public void cleanReasonInputShouldBeVisible() {
        dialogModal.reasonInputClean.shouldBe(visible);
    }

    @Step
    public void goodReasonInputShouldBeVisible() {
        dialogModal.reasonInput.shouldBe(visible);
    }

    @Step
    public void fillCancelReason(String reason) {
        sleep(1000); // wait to update field
        cleanReasonInputShouldBeVisible();
        dialogModal.reasonInputClean.sendKeys(reason);
        sleep(1000); // wait to update field
    }

    @Step
    public void changeDateTitleShouldBeVisible() {
        titleShouldBe(CHANGE_DELIVERY_DATE_TITLE);
    }

    @Step
    public void deliveryDateTitleShouldBeVisible() {
        changeDateTitleShouldBeVisible();
        dialogModal.sectionTitle(CHANGE_DELIVERY_DATE_TITLE, DELIVERY_TITLE).shouldBe(visible);
    }

    @Step
    public void modalShouldNotBeVisible1(String title) {
        dialogModal.modal(title).shouldNotBe(visible);
    }

    @Step
    public void changeDateModalShouldNotBeVisible() {
        modalShouldNotBeVisible1(CHANGE_DELIVERY_DATE_TITLE);
    }

    @Step
    public void cancelOrderModalShouldNotBeVisible() {
        modalShouldNotBeVisible1(CANCEL_ORDER);
    }

    @Step
    public void closeModal(String title) {
        dialogModal.closeModalBtn(title).shouldBe(visible).click();
    }

    public void footerButtonShouldBeEnabled(String title, Buttons buttons) {
        dialogModal.footerModalButton(title, buttons.getValue()).shouldBe(enabled);
    }

    public void footerButtonShouldBeDisabled(String title, Buttons buttons) {
        dialogModal.footerModalButton(title, buttons.getValue()).shouldBe(disabled);
    }

    public void cancelOrderSaveBtnShouldBeDisabled() {
        cleanReasonInputShouldBeVisible();
        footerButtonShouldBeDisabled(CANCEL_ORDER, SAVE);
    }

    public void cancelOrderSaveBtnShouldBeEnabled() {
        goodReasonInputShouldBeVisible();
        footerButtonShouldBeEnabled(CANCEL_ORDER, SAVE);
    }

    public void clickFooterButton(String title, Buttons buttons) {
        footerButtonShouldBeEnabled(title, buttons);
        dialogModal.footerModalButton(title, buttons.getValue()).shouldBe(visible).click();
    }

    @Step
    public void saveBtnClick(String title) {
        clickFooterButton(title, SAVE);
    }

    @Step
    public void cancelBtnClick(String title) {
        clickFooterButton(title, CANCEL);
    }

    @Step
    public void clearDate() {
        dialogModal.clearDate.click();
        dialogModal.dateField.shouldHave(value(""));
    }

    @Step
    public void clearTime() {
        dialogModal.clearTime.click();
        dialogModal.timeField.shouldHave(value(""));
    }

    // format: 2019-09-16
    @Step
    public void dateFieldShouldBe(String date) {
        dialogModal.dateField.shouldBe(value(date));
    }

    @Step
    public String getDateField() {
        return dialogModal.dateField.getValue();
    }

    // format: 23:05
    @Step
    public void timeFieldShouldBe(String time) {
        dialogModal.timeField.shouldBe(value(time));
    }

    @Step
    public String getTimeField() {
        return dialogModal.timeField.getValue();
    }

    @Step
    public void setDate(String month, String year, String day) {
        // open calendar
        dialogModal.calendar.shouldBe(visible).click();

        // set month
        dialogModal.monthDropDown.selectOptionContainingText(month);

        //set year
        dialogModal.yearDropDown.selectOptionContainingText(year);

        // set day
        dialogModal.dayOfMonth(day).shouldBe(visible).click();
    }

    @Step
    public void setTime(String hours, String minutes) {
        // open clock
        dialogModal.clock.shouldBe(visible).click();

        // set hours
        dialogModal.hoursInput.setValue(hours);

        // set minutes
        dialogModal.minutesInput.setValue(minutes);

        // close clock
        dialogModal.clock.shouldBe(visible).click();
    }

    @Step
    public void setAndCheckInvalidTime() {
        //numbers
        setTime(incorrectTime, incorrectTime);
        dialogModal.timeField.shouldHave(value(""));

        // chars
        setTime(incorrectTimeChar, incorrectTimeChar);
        dialogModal.timeField.shouldHave(value(""));
    }

    @Step
    public long setDateAndTime(long time) {
        long currentTime = currentTime();
        time = currentTime + time;
        Date dateTime = new Date(time * 1000);

        String date[] = (new SimpleDateFormat(dateTimeFormat, java.util.Locale.ENGLISH).format(dateTime)).split("/");
        String expectFormatDate = new SimpleDateFormat(dateFormat, java.util.Locale.ENGLISH).format(dateTime);
        String expectFormatTime = new SimpleDateFormat(timeFormat, java.util.Locale.ENGLISH).format(dateTime);
        // set date and time
        setDate(date[0], date[1], date[2]);
        setTime(date[3], date[4]);

        // expect date and time
        dateFieldShouldBe(expectFormatDate);
        timeFieldShouldBe(expectFormatTime);

        // date can't be past
        currentTime = currentTime();

        if (currentTime > time) return currentTime;
        else return time;
    }

    @Step
    public long setSuspendDate() {
        return setDateAndTime(toFuture);
    }

    @Step
    public long setPastDate() {
        return setDateAndTime(0 - toFuture);
    }

    @Step
    public long setNowDate() {
        return setDateAndTime(0);
    }

    @Step
    public void checkFormDateTime(long time) {
        String convertDate = new SimpleDateFormat(dateFormat).format(new Date(time * 1000));
        String convertTime = new SimpleDateFormat(timeFormat).format(new Date(time * 1000));

        dialogModal.dateField.shouldHave(value(convertDate));
        dialogModal.timeField.shouldHave(value(convertTime));
    }
}
