// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import steps.*;
import steps.detailsPagesSteps.*;
import steps.modalPagesSteps.*;
import steps.orderPageTabsSteps.*;
import steps.taxonomyPageSteps.serviceTypeTabsSteps.CustomerTariffsPageSteps;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BaseTest extends Base {
    //    admin portal pageSteps
    protected LoginPageSteps loginPageSteps = new LoginPageSteps();
    protected HeaderPageSteps headerPageSteps = new HeaderPageSteps();
    protected LeftNavigationPanelSteps leftNavigationPanelSteps = new LeftNavigationPanelSteps();
    protected DiscardChangesModalSteps discardChangesModalSteps = new DiscardChangesModalSteps();
    protected DashboardPageSteps dashboardPageSteps = new DashboardPageSteps();
    protected MerchantDetailsPageSteps merchantDetailsPageSteps = new MerchantDetailsPageSteps();
    protected ProductDetailsPageSteps productDetailsPageSteps = new ProductDetailsPageSteps();
    protected CustomerDetailsPageSteps customerDetailsPageSteps = new CustomerDetailsPageSteps();
    protected OrdersPageSteps ordersPageSteps = new OrdersPageSteps();
    protected DriverDetailsSteps driverDetailsSteps = new DriverDetailsSteps();
    protected SystemUserDetailsPageSteps systemUserDetailsPageSteps = new SystemUserDetailsPageSteps();
    protected NotificationModalPageSteps notificationModalPageSteps = new NotificationModalPageSteps();
    protected MerchantUserDetailsPageSteps merchantUserDetailsPageSteps = new MerchantUserDetailsPageSteps();

    //    order details tabs pageSteps
    protected BaseStep baseStep = new BaseStep();
    protected CustomerTariffsPageSteps customerTariffsPageSteps = new CustomerTariffsPageSteps();
    protected OrderHeaderPageSteps orderHeaderPageSteps = new OrderHeaderPageSteps();
    protected OrderCreateTicketPageSteps orderCreateTicketPageSteps = new OrderCreateTicketPageSteps();
    protected OrderProfilePageSteps orderProfilePageSteps = new OrderProfilePageSteps();
    protected OrderCartPageSteps orderCartPageSteps = new OrderCartPageSteps();
    protected OrderCostCalculationPageSteps costCalcPageSteps = new OrderCostCalculationPageSteps();
    protected OrderTransactionsPageSteps orderTransactionsPageSteps = new OrderTransactionsPageSteps();
    protected OrderTransactionsPageSteps1 orderTransactionsPageSteps1 = new OrderTransactionsPageSteps1();
    protected ExternalTransactionsModalPageSteps externalTransactionsModalPageSteps = new ExternalTransactionsModalPageSteps();
    protected DialogModalSteps dialogModalSteps = new DialogModalSteps();
    protected ChangeDriverModalSteps changeDriverModalSteps = new ChangeDriverModalSteps();
    protected DriverAnalysePageSteps driverAnalysePageSteps = new DriverAnalysePageSteps();
    protected PaginationPageSteps paginationPageSteps = new PaginationPageSteps();

    // API steps
    protected UseAPISteps useAPISteps = new UseAPISteps();

    @BeforeAll
    public static void setupDriver() {
        SelenideLogger.addListener(testProperties.selenideListener(), new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterEach
    public void closeDriver() {
//        close();

        clearBrowserCache();
        clearBrowserCookies();
    }

    public void configure() {
        Configuration.browserSize = testProperties.browserSize();
        Configuration.timeout = testProperties.timeout();
        Configuration.headless = testProperties.headless();

        open(testProperties.loginPagUrl());
        refresh();  // to close opened modal window
        loginPageSteps.switchLanguageTo("en");
    }

    public void switchToTheOrdersPage(String orderId, String pageName) {
        open(baseStep.getOrderPageUrl(orderId, pageName));
        sleep(2000); // wait for page downloading
    }
}