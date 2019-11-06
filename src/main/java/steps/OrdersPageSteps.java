// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import pages.OrdersPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.sleep;

public class OrdersPageSteps extends BaseStep {

    private OrdersPage ordersPage = new OrdersPage();


    @Step
    public void filterButtonShouldBeVisible() {
        ordersPage.filterButton.shouldBe(visible);
    }

    @Step
    public void filterButtonShouldBeDisplayed() {
        ordersPage.filterButton.shouldBe(enabled);
    }

    @Step
    public void filterButtonClick() {
        ordersPage.filterButton.click();
    }

    @Step
    public void inputOrderIdInField(String orderId) {
        ordersPage.orderEntryField.val(orderId);
        sleep(1000);
        ordersPage.orderEntryField.sendKeys(Keys.ARROW_DOWN);
        ordersPage.orderEntryField.sendKeys(Keys.ARROW_DOWN);
        ordersPage.orderEntryField.pressEnter();

    }

    @Step
    public void applyFilterButtonClick() {
        ordersPage.applyFilterButton.click();
    }

    @Step
    public void supportCompleteLinkClick() {
        ordersPage.supportCompleteLink.click();
    }

    @Step
    public void searchResultsShouldBeHaveText(String orderId) {
        ordersPage.searchResults.shouldHave(text(orderId));
    }

    @Step
    public void searchThroughOrderFilter(String orderId) {
        filterButtonShouldBeDisplayed();
        filterButtonShouldBeVisible();
        filterButtonClick();

        sleep(2000);
        inputOrderIdInField(orderId);

        sleep(3000);
        applyFilterButtonClick();
        supportCompleteLinkClick();
        searchResultsShouldBeHaveText(orderId);
    }

    @Step
    public void shouldBeDisplayedDeliveryOrdersEn() {
        ordersPage.title.shouldHave(text(OrdersPage.TITLE));
    }
}
