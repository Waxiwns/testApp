package steps.web;

import com.codeborne.selenide.Configuration;
import pages.web.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class LoginSteps extends Steps {
    public long timeout = 6000;
    public String url = "https://merchant.regres.toyou.io/#/login";

    public String txtErrorMsgTitleEn = "Error";
    public String txtErrorMsgEn = "Login failed";
    public String txtLoginBlockTitleEn = "Merchant Portal";
    public String txtFormTitleEn = "LOGIN";
    public String txtFormIdTitleEn = "USER ID";
    public String txtFormPassTitleEn = "PASSWORD";
    public String txtBtnLoginEn = "LOG IN";
    public String txtLocaleEN = "EN";


    public String txtErrorMsgTitleAr = "خطأ";
    public String txtErrorMsgAr = "خطأ في تسجيل الدخول";
    public String txtLoginBlockTitleAr = "بوابة المتجر";
    public String txtFormTitleAr = "دخول";
    public String txtFormIdTitleAr = "هوية المستخدم";
    public String txtFormPassTitleAr = "كلمة المرور";
    public String txtBtnLoginAr = "تسجيل الدخول";
    public String txtLocaleAr = "العربية";

    private LoginPage loginPage = new LoginPage();

    public void openLoginPage() {
        printStepName();

        open(url);

        loginBtnIsDisplayed();
    }

    public void logIn(String id, String pass) {
        printStepName();

//        fill values
        fillValues(id, pass);
        clickLogin();
    }

    public void fillId(String id) {
        loginPage.idInput().setValue(id);
    }

    public void fillPass(String pass) {
        loginPage.passInput().setValue(pass);
    }

    public void fillValues(String id, String pass) {
        fillId(id);
        fillPass(pass);
    }

    public void loginBtnIsDisplayed() {
        printStepName();

//        wait for login button
        loginPage.loginBtn().isDisplayed();
    }

    public void clickLogin() {
        printStepName();

        loginPage.loginBtn().hover();
        loginPage.loginBtn().click();
    }

    public void jsClickLogin() {
        printStepName();

        jsClickElement(loginPage.loginBtn());
    }

    public void errorMsgIsDisplayed() {
        printStepName();

        loginPage.errorMsg().isDisplayed();
    }

    public void errorMsgIsNotDisplayed() {
        printStepName();

        loginPage.errorMsg().shouldNot(visible);
    }

    public void logInWithCorrectValues(String id, String pass) {
        printStepName();

        openLoginPage();

        logIn(id, pass);
    }

    public void logInWithIncorrectValues(String id, String pass) {
        printStepName();

        logIn(id, pass);

//        wait for error message
        errorMsgIsDisplayed();
    }

    public void errorMsgDisappeared() {
        printStepName();

        errorMsgIsDisplayed();

//        error message should not be displayed
        Configuration.timeout = timeout;
        errorMsgIsNotDisplayed();
    }

    public void changeLocaleTo(String lang) {
        printStepName();

        loginPage.languageDrop().click();
        loginPage.languageDropItem(lang).click();
    }

    public void shouldBeLocale(String locale) {
        printStepName();

        switch (locale) {
            case ("EN"):
                loginPage.loginBlockTitle().shouldHave(text(txtLoginBlockTitleEn));
                loginPage.formTitle().shouldHave(text(txtFormTitleEn));
                loginPage.formInputTitle(0).shouldHave(text(txtFormIdTitleEn));
                loginPage.formInputTitle(1).shouldHave(text(txtFormPassTitleEn));
                loginPage.languageDrop().shouldHave(text(txtLocaleEN));
                loginPage.loginBtn().shouldHave(text(txtBtnLoginEn));
                break;
            case ("AR"):
                loginPage.loginBlockTitle().shouldHave(text(txtLoginBlockTitleAr));
                loginPage.formTitle().shouldHave(text(txtFormTitleAr));
                loginPage.formInputTitle(0).shouldHave(text(txtFormIdTitleAr));
                loginPage.formInputTitle(1).shouldHave(text(txtFormPassTitleAr));
                loginPage.languageDrop().shouldHave(text(txtLocaleAr));
                loginPage.loginBtn().shouldHave(text(txtBtnLoginAr));
                break;
        }
    }

    public void errorMsgEn() {
        printStepName();

        errorMsgIsDisplayed();
        loginPage.errorMsgTitle().shouldHave(text(txtErrorMsgTitleEn));
        loginPage.errorMsg().shouldHave(text(txtErrorMsgEn));
    }

    public void errorMsgAr() {
        printStepName();

        errorMsgIsDisplayed();
        loginPage.errorMsgTitle().shouldHave(text(txtErrorMsgTitleAr));
        loginPage.errorMsg().shouldHave(text(txtErrorMsgAr));
    }

    public void errorMsgShouldBeLocale(String locale) {
        printStepName();

        switch (locale) {
            case ("EN"):
                errorMsgEn();
                break;
            case ("AR"):
                errorMsgAr();
                break;
        }
    }

    public void jsFillValues(String id, String pass) {
        printStepName();

//        fill values
        jsFillValue("fm-login__email", id);
        jsFillValue(loginPage.passInput(), pass);
    }

    public void jsLogIn(String id, String pass) {
        printStepName();

        jsFillValues(id, pass);
        jsClickLogin();
    }

    public void jsOpenAlert(String msg) {
        printStepName();

        jsOpenAlert(msg);
    }
}
