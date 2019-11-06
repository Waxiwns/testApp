// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import io.qameta.allure.Step;
import pages.orderPageTabs.OrderHeaderPage;
import utils.BaseStep;
import utils.constants.PageIdentifiers;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertEquals;
import static utils.constants.Buttons.CREATE_TICKET;
import static utils.constants.Buttons.GO;
import static utils.constants.PageIdentifiers.PROFILE;

public class OrderHeaderPageSteps extends BaseStep {

    private OrderHeaderPage orderHeaderPage = new OrderHeaderPage();


    @Step
    public void orderTitleShouldHaveNumber(String orderNumber) {
        orderHeaderPage.titleOrderNumber.shouldHave(text(orderHeaderPage.TITLE + orderNumber));
    }

    @Step
    public void searchFieldShouldBeVisible() {
        orderHeaderPage.searchField.shouldBe(visible);
    }

    @Step
    public void createTicketShouldBeVisible() {
        orderHeaderPage.createTicket.shouldBe(visible);
    }

    @Step
    public void searchFieldShouldHavePlaceholder() {
        searchFieldShouldBeVisible();
        elementShouldHaveAttribute(orderHeaderPage.searchField, placeholder, orderHeaderPage.SEARCH_PLACEHOLDER);
    }

    @Step
    public void searchFieldSetValue(String value) {
        searchFieldShouldBeVisible();
        sleep(2000);    // wait for download
        orderHeaderPage.searchField.sendKeys(value);
        sleep(1000);    // wait for update value
    }

    // search order by id or number
    @Step
    public void searchOrder(String value) {
        searchFieldSetValue(value);
        searchGoBtnClick();
    }

    @Step
    public void searchGoBtnShouldBeVisible() {
        orderHeaderPage.searchGoBtn.shouldBe(visible);
    }

    @Step
    public void searchGoBtnShouldBeTranslated() {
        orderHeaderPage.searchGoBtn.shouldHave(text(GO.getValue()));
    }

    @Step
    public void createTicketBtnShouldBeTranslated() {
        orderHeaderPage.createTicket.shouldHave(text(CREATE_TICKET.getValue()));
    }

    @Step
    public void searchGoBtnClick() {
        searchGoBtnShouldBeVisible();
        orderHeaderPage.searchGoBtn.click();
    }

    @Step
    public void createTicketBtnClick() {
        createTicketShouldBeVisible();
        orderHeaderPage.createTicket.click();
    }

    @Step
    public void checkPageUrl(String orderId, PageIdentifiers pageIdentifiers) {
        clickOnTab(orderId, pageIdentifiers);
        assertEquals(getOrderPageUrl(orderId, pageIdentifiers.getValue()), url());
    }

    @Step
    public void isActiveTab(PageIdentifiers pageIdentifiers) {
        orderHeaderPage.activePage.shouldHave(text(pageIdentifiers.getValue()));
    }

    @Step
    public void clickOnTab(String orderId, PageIdentifiers pageIdentifiers) {
        orderHeaderPage.getOrderPageTab(orderId, pageIdentifiers.getUrlValue()).click();
        isActiveTab(pageIdentifiers);
    }

    @Step
    public void checkOpenedTab(String orderId, PageIdentifiers pageIdentifiers) {
        orderHeaderPage.getOrderPageTab(orderId, pageIdentifiers.getUrlValue()).shouldBe(visible);
    }

    @Step
    public void breadcrumbsShouldBeDisplayed() {
        orderHeaderPage.breadcrumbsSector.shouldBe(visible);
    }

    @Step
    public void breadcrumbMessengerPageShouldBeCorrectlyDisplayed(PageIdentifiers pageIdentifiers, String activeTab) {
        orderHeaderPage.breadCrumbHome.shouldBe(visible);
        orderHeaderPage.breadCrumbTab.shouldHave(text(pageIdentifiers.getValue()));
        orderHeaderPage.activeBreadcrumb.shouldHave(text(activeTab));
    }

    @Step
    public void breadcrumbsServiceTypesTabShouldBeCorrectlyDisplayed(String serviceType, String pageUrl) {
        orderHeaderPage.getOrderPageTabServiceTypeTab(serviceType, pageUrl).shouldBe(visible);
    }

    @Step
    public void breadcrumbsShouldBeDisplayedOnTab(String orderId, PageIdentifiers pageIdentifiers) {
        clickOnTab(orderId, pageIdentifiers);
        breadcrumbsShouldBeDisplayed();
        isActiveTab(pageIdentifiers);
    }

    @Step
    public void checkTransitionToTheProfilePage(String orderId, PageIdentifiers pageIdentifiers) {
        clickOnTab(orderId, pageIdentifiers);
        breadcrumbsShouldBeDisplayed();
        clickOnOrderIdBreadCrumb(orderId);
        shouldBeDisplayedProfilePage(PROFILE);
    }

    @Step
    public void clickOnOrderIdBreadCrumb(String orderId) {
        orderHeaderPage.breadCrumbOrderId(orderId).click();
    }

    @Step
    public void shouldBeDisplayedProfilePage(PageIdentifiers pageIdentifiers) {
        orderHeaderPage.breadCrumbTabPage(pageIdentifiers.getValue());
    }

    @Step
    public void clickBreadCrumbTabName() {
        orderHeaderPage.breadCrumbTab.click();
    }
}
