// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.modalPagesSteps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.modalPages.ChangeDriverModal;
import utils.BaseStep;
import utils.constants.Buttons;
import utils.constants.Statuses;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.actions;
import static pages.modalPages.ChangeDriverModal.*;
import static pages.modalPages.DialogModal.CHANGE_DELIVERY_DATE_TITLE;
import static pages.modalPages.DialogModal.DELIVERY_TITLE;
import static pages.taxonomy.serviceTypeTabs.CustomerTariffsPage.*;
import static utils.constants.Buttons.CANCEL;
import static utils.constants.Buttons.SAVE;

public class ChangeDriverModalSteps extends BaseStep {

    ChangeDriverModal changeDriverModal = new ChangeDriverModal();

    @Step
    public void modalTitleShouldBeDisplayed() {
        changeDriverModal.modalMapTitle.shouldBe(visible);
        changeDriverModal.modalMapTitle.shouldBe(text(TITLE));
    }

    @Step
    public void modalTitleShouldNotBeDisplayed() {
        changeDriverModal.modalMapTitle.shouldNotBe(visible);
    }

    @Step
    public void modalMapTitleShouldNotBeVisible() {
        changeDriverModal.modalMapTitle.shouldNotBe(visible);
    }

    @Step
    public void showMapButtonClick() {
        changeDriverModal.showMapButton.shouldBe(visible).click();
    }

    @Step
    public void showTableButtonClick() {
        changeDriverModal.showTableButton.shouldBe(visible).click();
    }

    @Step
    public void crossModelMapClick() {
        changeDriverModal.crossModelMap.shouldBe(visible).click();
    }

    @Step
    public void modalMapCloseButtonClick() {
        changeDriverModal.modalMapСloseButton.shouldBe(visible).click();
    }

    @Step
    private void filterCancelButtonShouldBeVisible() {
        changeDriverModal.filterCancelButton.shouldBe(visible);
    }

    @Step
    public void filterCancelButtonClick() {
        filterCancelButtonShouldBeVisible();
        changeDriverModal.filterCancelButton.click();
    }

    @Step
    public void applyFilterButtonClick() {
        applyFilterButtonShouldBeVisible();
        changeDriverModal.applyFilterButton.should(exist).click();
    }

    @Step
    public void applyFilterButtonShouldBeVisible() {
        changeDriverModal.applyFilterButton.shouldBe(visible).shouldNotBe(disabled);
    }

    @Step
    public void userPhoneOrEmailFieldShouldBeVisible() {
        changeDriverModal.userPhoneOrEmailField.shouldBe(visible);
    }

    @Step
    public void inputInTheUserPhoneOrEmailField(String value) {
        userPhoneOrEmailFieldShouldBeVisible();
        changeDriverModal.userPhoneOrEmailField.setValue(value);
    }

    @Step
    public void userFirstNameFieldShouldBeVisible() {
        changeDriverModal.userFirstNameField.shouldBe(visible);
    }

    @Step
    public void inputInTheUserFirstNameField(String value) {
        userFirstNameFieldShouldBeVisible();
        changeDriverModal.userFirstNameField.setValue(value);
    }

    @Step
    public void userFirstNameFieldShouldHaveText(String text) {
        userFirstNameFieldShouldBeVisible();
        changeDriverModal.userFirstNameField.shouldHave(value(text));
    }

    @Step
    public void userLastNameFieldShouldHaveText(String text) {
        userFirstNameFieldShouldBeVisible();
        changeDriverModal.userLastNameField.shouldHave(value(text));
    }

    @Step
    public void userPhoneOrEmailFieldShouldHaveText(String text) {
        userFirstNameFieldShouldBeVisible();
        changeDriverModal.userPhoneOrEmailField.shouldHave(value(text));
    }

    @Step
    public void cityFieldShouldHaveText(String text) {
        userFirstNameFieldShouldBeVisible();
        changeDriverModal.cityField.shouldHave(value(text));
    }

    @Step
    public void userLastNameFieldShouldBeVisible() {
        changeDriverModal.userLastNameField.shouldBe(visible);
    }

    @Step
    public void inputInTheUserLastNameField(String value) {
        userLastNameFieldShouldBeVisible();
        changeDriverModal.userLastNameField.setValue(value);
    }

    @Step
    public void inputInTheDriverStatusField(String value) {
        changeDriverModal.driverStatusField.should(exist).selectOptionByValue(value);
    }

    @Step
    public void cityFieldShouldBeVisible() {
        changeDriverModal.cityField.shouldBe(visible);
    }

    @Step
    public void inputInTheCityField(String value) {
        cityFieldShouldBeVisible();
        changeDriverModal.cityField.setValue(value);
    }

    @Step
    public void searchResultsShouldBe() {
        changeDriverModal.searchResults.should(exist);
    }

    @Step
    public void checkResultBlockForSelectedStatus(String Status, int quantity) {
        if (quantity > 0) {
            changeDriverModal.getSearchLineFromResultBlock(Status).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        } else {
            changeDriverModal.getEmptyResultBlock().shouldHave(text(NO_RECORDS_AVAILABLE));
        }
    }

    @Step
    public void checkResultByDriverProperties(String properties) {
        changeDriverModal.getSearchLineFromResultBlock(properties).filter(Condition.text(properties)).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
    }

    @Step
    public void checkStatusFieldEnglish(Statuses status, int quantity) {
        inputInTheDriverStatusField(status.getValue());
        applyFilterButtonClick();
        checkResultBlockForSelectedStatus(status.getEnValue(), quantity);
    }

    @Step
    public void checkStatusFieldArabic(String status, int quantity) {
        inputInTheDriverStatusField(status);
        applyFilterButtonClick();
        checkResultBlockForSelectedStatus(status, quantity);
    }

    @Step
    public void selectDriverButtonClick() {
        selectDriverButtonShouldBeVisible();
        changeDriverModal.selectDriverButton.shouldNotBe(disabled).click();
    }

    @Step
    public void selectDriverButtonShouldBeVisible() {
        changeDriverModal.selectDriverButton.should(visible);
    }

    @Step
    public void driverStatusFieldShouldByVisible() {
        changeDriverModal.driverStatusField.shouldBe(visible);
    }

    @Step
    public void driverStatusFieldShouldHaveText(String value) {
        driverStatusFieldShouldByVisible();
        changeDriverModal.driverStatusField.shouldHave(value(value));
    }

    @Step
    public void orderServiceTypeFieldShouldByVisible() {
        changeDriverModal.serviceTypeField.shouldBe(visible);
    }

    @Step
    public void orderServiceTypeFieldShouldBeDisabled() {
        changeDriverModal.serviceTypeField.shouldBe(disabled);
    }

    @Step
    public void orderServiceTypeFieldShouldHaveValue(String value) {
        orderServiceTypeFieldShouldByVisible();
        changeDriverModal.serviceTypeField.shouldHave(text(value));
    }

    @Step
    public void orderServiceTypeFieldShouldHaveValueAndDisabled(String value) {
        orderServiceTypeFieldShouldByVisible();
        orderServiceTypeFieldShouldBeDisabled();
        changeDriverModal.serviceTypeField.shouldHave(text(value));
    }

    @Step
    public void cityMapGoogleLinkShouldBeVisible(String lat, String lon) {
        changeDriverModal.cityMapGoogleLink(lat).shouldBe(visible);
        changeDriverModal.cityMapGoogleLink(lon).shouldBe(visible);
    }

    @Step
    public void onlineCarShouldBeVisibleOnTheMap(int num) {
        changeDriverModal.onlineCarMap(num).shouldBe(visible);
    }

    @Step
    public void firstOnlineCarShouldBeVisibleOnTheMap() {
        onlineCarShouldBeVisibleOnTheMap(0);
    }

    @Step
    public void secondOnlineCarShouldBeVisibleOnTheMap() {
        onlineCarShouldBeVisibleOnTheMap(1);
    }

    @Step
    private void carOnTheMapClick(int num) {
        onlineCarShouldBeVisibleOnTheMap(num);
        actions().moveToElement(changeDriverModal.onlineCarMap(num), 1, 1).click().build().perform();
    }

    @Step
    public void driverDetailsLinkInInfoWindowShouldBeVisible(String driverName) {
        changeDriverModal.driverDetailsLinkInInfoWindow(driverName).shouldBe(visible);
    }

    @Step
    public void driverDetailsLinkInInfoWindowShouldNotBeVisible(String driverName) {
        changeDriverModal.driverDetailsLinkInInfoWindow(driverName).shouldNotBe(visible);
    }

    @Step
    public void driverDetailsLinkInInfoWindowClick(String driverName) {
        driverDetailsLinkInInfoWindowShouldBeVisible(driverName);
        changeDriverModal.driverDetailsLinkInInfoWindow(driverName).click();
    }

    @Step
    private void driverDetailsSelectButtonInInfoWindowShouldBeVisible(String driverName) {
        changeDriverModal.driverDetailsSelectButtonInInfoWindow(driverName).shouldBe(visible);
    }

    @Step
    public void driverDetailsSelectButtonInInfoWindowClick(String driverName) {
        driverDetailsSelectButtonInInfoWindowShouldBeVisible(driverName);
        changeDriverModal.driverDetailsSelectButtonInInfoWindow(driverName).hover().click();
    }

    @Step
    public void firstCarOnTheMapClick() {
        carOnTheMapClick(0);
    }

    @Step
    public void secondCarOnTheMapClick() {
        carOnTheMapClick(1);
    }

    @Step
    public void cityMapShouldBeVisible() {
        changeDriverModal.cityMap.shouldBe(visible);
    }

    @Step
    public void userPhoneOrEmailLabelShouldBeVisible() {
        changeDriverModal.userPhoneOrEmailLabel.shouldBe(visible);
    }

    @Step
    public void userPhoneOrEmailLabelShouldHaveText() {
        userPhoneOrEmailLabelShouldBeVisible();
        changeDriverModal.userPhoneOrEmailLabel.shouldHave(text(PHONE_OR_EMAIL_LABEL));
    }

    @Step
    public void userFirstNameLabelShouldBeVisible() {
        changeDriverModal.userFirstNameLabel.shouldBe(visible);
    }

    @Step
    public void userFirstNameLabelShouldHaveText() {
        userFirstNameLabelShouldBeVisible();
        changeDriverModal.userFirstNameLabel.shouldHave(text(FIRST_NAME_LABEL));
    }

    @Step
    public void userLastNameLabelShouldBeVisible() {
        changeDriverModal.userLastNameLabel.shouldBe(visible);
    }

    @Step
    public void userLastNameLabelShouldHaveText() {
        userLastNameLabelShouldBeVisible();
        changeDriverModal.userLastNameLabel.shouldHave(text(LAST_NAME_LABEL));
    }

    @Step
    public void driverStatusLabelShouldBeVisible() {
        changeDriverModal.driverStatusLabel.shouldBe(visible);
    }

    @Step
    public void driverStatusLabelShouldHaveText() {
        driverStatusLabelShouldBeVisible();
        changeDriverModal.driverStatusLabel.shouldHave(text(DRIVER_STATUS_LABEL));
    }

    @Step
    public void cityLabelShouldBeVisible() {
        changeDriverModal.cityLabel.shouldBe(visible);
    }

    @Step
    public void cityLabelShouldHaveText() {
        cityLabelShouldBeVisible();
        changeDriverModal.cityLabel.shouldHave(text(CITY_LABEL));
    }

    @Step
    public void serviceTypeLabelShouldBeVisible() {
        changeDriverModal.serviceTypeLabel.shouldBe(visible);
    }

    @Step
    public void serviceTypeLabelShouldHaveText() {
        serviceTypeLabelShouldBeVisible();
        changeDriverModal.serviceTypeLabel.shouldHave(text(SERVICE_TYPE_LABEL));
    }

    @Step
    public void titleShouldBeTranslated() {
        changeDriverModal.title(CHANGE_DELIVERY_DATE_TITLE).shouldBe(visible);
    }

    @Step
    public void deliveryDateTitleShouldBeTranslated() {
        changeDriverModal.deliveryDateTitle(CHANGE_DELIVERY_DATE_TITLE, DELIVERY_TITLE).shouldBe(visible);
    }

    @Step
    public void modalShouldNotBeVisible() {
        changeDriverModal.modal(CHANGE_DELIVERY_DATE_TITLE).shouldNotBe(visible);
    }

    @Step
    public void closeModal() {
        changeDriverModal.closeModalBtn(CHANGE_DELIVERY_DATE_TITLE).shouldBe(visible).click();
    }

    private void clickFooterButton(Buttons buttons) {
        changeDriverModal.footerModalButton(CHANGE_DELIVERY_DATE_TITLE, buttons.getValue()).shouldBe(visible).click();
    }

    @Step
    public void saveBtnClick() {
        clickFooterButton(SAVE);
    }

    @Step
    public void cancelBtnClick() {
        clickFooterButton(CANCEL);
    }

    @Step
    public void driversTableHeaderCellsShouldBeVisible() {
        changeDriverModal.driversTableHeaderCell(ID).shouldBe(visible);
        changeDriverModal.driversTableHeaderCell(NAME).shouldBe(visible);
        changeDriverModal.driversTableHeaderCell(PHONE).shouldBe(visible);
        changeDriverModal.driversTableHeaderCell(TRANSPORT).shouldBe(visible);
        changeDriverModal.driversTableHeaderCell(STATUS).shouldBe(visible);
        changeDriverModal.driversTableHeaderCell(SELECT).shouldBe(visible);
    }

    @Step
    public void driversTableLineByIdCellsShouldHaveText(String... values) {
        changeDriverModal.driversTableLineByIdCells(values[0]).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(values.length));

        for (int i = 0; i < values.length; i++) {
            changeDriverModal.driversTableLineByIdCells(values[0]).get(i).shouldHave(text(values[i]));
        }
    }

    @Step
    private void driversTableSelectButtonByIdShouldBeVisible(String driverId) {
        changeDriverModal.driversTableSelectButtonById(driverId).shouldHave(text(SELECT)).shouldBe(visible);
    }

    @Step
    public void driversTableSelectButtonByIdClick(String driverId) {
        driversTableSelectButtonByIdShouldBeVisible(driverId);
        changeDriverModal.driversTableSelectButtonById(driverId).click();
    }

    @Step
    private void filterFieldShouldBeVisible(String filterName) {
        changeDriverModal.filterField(filterName).shouldBe(visible);
    }

    @Step
    private void filterFieldShouldNotBeVisible(String filterName) {
        changeDriverModal.filterField(filterName).shouldNotBe(visible);
    }

    @Step
    public void allFilterFieldsShouldBeVisible() {
        filterFieldShouldBeVisible(PHONE_OR_EMAIL);
        filterFieldShouldBeVisible(FIRST_NAME);
        filterFieldShouldBeVisible(LAST_NAME);
        filterFieldShouldBeVisible(SERVICE_TYPE);
        filterFieldShouldBeVisible(CITY);
        filterFieldShouldBeVisible(DRIVER_STATUS);
    }

    @Step
    public void allFilterFieldsShouldNotBeVisible() {
        filterFieldShouldNotBeVisible(PHONE_OR_EMAIL);
        filterFieldShouldNotBeVisible(FIRST_NAME);
        filterFieldShouldNotBeVisible(LAST_NAME);
        filterFieldShouldNotBeVisible(SERVICE_TYPE);
        filterFieldShouldNotBeVisible(CITY);
        filterFieldShouldNotBeVisible(DRIVER_STATUS);
    }

    @Step
    public void filterFieldSetValue(String filterName, String value) {
        filterFieldShouldBeVisible(filterName);

        if (filterName.equals(DRIVER_STATUS) || filterName.equals(SERVICE_TYPE))
            changeDriverModal.filterField(filterName).selectOptionByValue(value);
        else changeDriverModal.filterField(filterName).setValue(value);
    }

    @Step
    public void filterFieldValueShouldBe(String filterName, String value) {
        changeDriverModal.filterField(filterName).shouldBe(value(value));
    }

    @Step
    public void serviceTypeFilterFieldValueShouldBeCourier() {
        filterFieldValueShouldBe(SERVICE_TYPE, COURIER_SERVICE_TYPE);
    }

    public void serviceTypeFilterFieldValueShouldBeDelivery() {
        filterFieldValueShouldBe(SERVICE_TYPE, DELIVERY_SERVICE_TYPE);
    }

    @Step
    public void serviceTypeFilterFieldValueShouldBeTaxi() {
        filterFieldValueShouldBe(SERVICE_TYPE, TAXI_SERVICE_TYPE);
    }

    @Step
    public void serviceTypeFilterShouldBeInactive() {
        changeDriverModal.filterField(SERVICE_TYPE).shouldBe(disabled);
    }
}
