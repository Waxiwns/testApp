package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.sleep;

public class LoginTests {
    String id = "ejisko@gmail.com";
    String pass = "0000";
    String incorrectValue = "incorrect";
    String localeEn = "EN";
    String localeAr = "AR";

    private LoginSteps step = new LoginSteps();

    @Before
    public void setConfiguration(){
//        Configuration.headless = true;
    }

    @After
    public void closeBrowser(){
        close();
    }

    @Test
    public void loginWithValidValues() {
        step.openLoginPage();
        step.logInWithCorrectValues(id, pass);
    }

    @Test
    public void loginWithUppercaseId() {
        step.openLoginPage();
        step.logInWithCorrectValues(id.toUpperCase(), pass);
    }

    @Test
    public void loginWithSpacesBeforeAndAfterId() {
        id = " " + id + " ";

        step.openLoginPage();
        step.logInWithCorrectValues(id, pass);
    }

    @Test
    public void loginWithIncorrectPass() {
        step.openLoginPage();
        step.logInWithIncorrectValues(id, incorrectValue);
        step.errorMsgDisappeared();
    }

    @Test
    public void loginWithIncorrectId() {
        step.openLoginPage();
        step.logInWithIncorrectValues(incorrectValue, pass);
    }

    @Test
    public void loginWithIncorrectValues() {
        step.openLoginPage();
        step.logInWithIncorrectValues(incorrectValue, incorrectValue);
    }

    @Test
    public void loginWithConverselyValues() {
        step.openLoginPage();
        step.logInWithIncorrectValues(pass, id);
    }

    @Test
    public void changeLocale() {
        step.openLoginPage();
        step.shouldBeLocale(localeEn);
        step.changeLocaleTo(localeAr);
        step.shouldBeLocale(localeAr);
        step.changeLocaleTo(localeEn);
        step.shouldBeLocale(localeEn);
    }

    @Test
    public void changeLocaleErrorMsg() {
        step.openLoginPage();
        step.shouldBeLocale(localeEn);

//        login with incorrect values to show error message
        step.logInWithIncorrectValues(incorrectValue, incorrectValue);
        step.errorMsgShouldBeLocale(localeEn);

        step.changeLocaleTo(localeAr);
        step.errorMsgShouldBeLocale(localeAr);
    }

    @Test
    public void correctDashboardLocale() {
        step.openLoginPage();
        step.shouldBeLocale(localeEn);

//        login with correct values
        step.logIn(id, pass);

//        expect locale
        step.dashboardMsgShouldBeLocale(localeEn);

        step.closeMsgForm();
        step.logOut();

//        change locale to arabic
        step.changeLocaleTo(localeAr);
        step.shouldBeLocale(localeAr);

//        login with correct values
        step.logIn(id, pass);

//        expect locale
        step.dashboardMsgShouldBeLocale(localeAr);
    }
    @Test
    public void jsExecute(){
        step.openLoginPage();
        step.visitPage("http://qaru.site/questions/16404796/what-is-javascriptexecutor-in-selenium");
sleep(5000);

    }
}
