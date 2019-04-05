package web;

import org.junit.Test;
import steps.web.DashboardSteps;
import steps.web.LoginSteps;

import static com.codeborne.selenide.Selenide.sleep;

public class DashboardTests extends WebTests {

    String id = "ejisko@gmail.com";

    String pass = "0000";

    String localeEn = "EN";

    String localeAr = "AR";

    int tabsSize = 10;

    private DashboardSteps dashboardSteps = new DashboardSteps();

    private LoginSteps logInSteps = new LoginSteps();


    @Test
    public void chooseNaviTabs() {
        printTestName();

        dashboardSteps.logInWithCorrectValues(id, pass);

        dashboardSteps.naviTabsSize(tabsSize);
        dashboardSteps.chooseNaviTab("POS");
        dashboardSteps.chooseNaviTab("PRODUCTS");
        dashboardSteps.chooseNaviTab("OPTIONS");
        dashboardSteps.chooseNaviTab("SCHEDULES");
        dashboardSteps.chooseNaviTab("USERS");
        dashboardSteps.chooseNaviTab("ORDERS");
        dashboardSteps.chooseNaviTab("TAGS");
        dashboardSteps.chooseNaviTab("REPORTS");
        dashboardSteps.chooseNaviTab("TRANSACTIONS");
        dashboardSteps.chooseNaviTab("PROFILE");
    }

    @Test
    public void correctDashboardLocale() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.shouldBeLocale(localeEn);

//        login with correct values
        logInSteps.logIn(id, pass);

//        expect locale
        dashboardSteps.dashboardMsgShouldBeLocale(localeEn);

        dashboardSteps.closeMsgForm();
        dashboardSteps.logOut();

//        change locale to arabic
        logInSteps.changeLocaleTo(localeAr);
        logInSteps.shouldBeLocale(localeAr);

//        login with correct values
        logInSteps.logIn(id, pass);

//        expect locale
        dashboardSteps.dashboardMsgShouldBeLocale(localeAr);
    }
}
