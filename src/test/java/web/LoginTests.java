package web;

import org.junit.Test;
import steps.web.DashboardSteps;
import steps.web.LoginSteps;

public class LoginTests extends WebTests {

    String id = "ejisko@gmail.com";

    String pass = "0000";

    String incorrectValue = "incorrect";

    String localeEn = "EN";

    String localeAr = "AR";

    private LoginSteps logInSteps = new LoginSteps();
    private DashboardSteps dashboardSteps = new DashboardSteps();


    @Test
    public void logInWithCorrectValues() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.logInWithCorrectValues(id, pass);

        dashboardSteps.closeMsgForm();
        dashboardSteps.logoutBtnIsDisplayed();
    }

    @Test
    public void loginWithUppercaseId() {
        printTestName();

        logInSteps.logInWithCorrectValues(id.toUpperCase(), pass);

        dashboardSteps.closeMsgForm();
        dashboardSteps.logoutBtnIsDisplayed();
    }

    @Test
    public void loginWithSpacesBeforeAndAfterId() {
        printTestName();

        id = " " + id + " ";

        logInSteps.logInWithCorrectValues(id, pass);

        dashboardSteps.closeMsgForm();
        dashboardSteps.logoutBtnIsDisplayed();
    }

    @Test
    public void loginWithIncorrectPass() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.logInWithIncorrectValues(id, incorrectValue);
        logInSteps.errorMsgDisappeared();
    }

    @Test
    public void loginWithIncorrectId() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.logInWithIncorrectValues(incorrectValue, pass);
    }

    @Test
    public void loginWithIncorrectValues() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.logInWithIncorrectValues(incorrectValue, incorrectValue);
    }

    @Test
    public void loginWithConverselyValues() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.logInWithIncorrectValues(pass, id);
    }

    @Test
    public void changeLocale() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.shouldBeLocale(localeEn);
        logInSteps.changeLocaleTo(localeAr);
        logInSteps.shouldBeLocale(localeAr);
        logInSteps.changeLocaleTo(localeEn);
        logInSteps.shouldBeLocale(localeEn);
    }

    @Test
    public void changeLocaleErrorMsg() {
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.shouldBeLocale(localeEn);

//        login with incorrect values to show error message
        logInSteps.logInWithIncorrectValues(incorrectValue, incorrectValue);
        logInSteps.errorMsgShouldBeLocale(localeEn);

        logInSteps.changeLocaleTo(localeAr);
        logInSteps.errorMsgShouldBeLocale(localeAr);
    }

    @Test
    public void jsExecute(){
        printTestName();

        logInSteps.openLoginPage();
        logInSteps.jsVisitPage("http://qaru.site/questions/16404796/what-is-javascriptexecutor-in-selenium");
    }
}
