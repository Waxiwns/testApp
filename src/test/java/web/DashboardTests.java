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

    private DashboardSteps dashboardSteps = new DashboardSteps();

    private LoginSteps logInSteps = new LoginSteps();


    @Test
    public void loginWithValidValues() {
        printTestName();

        dashboardSteps.logInWithCorrectValues(id, pass);
        sleep(5000);

        dashboardSteps.chooseNaviTab("POS");
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
