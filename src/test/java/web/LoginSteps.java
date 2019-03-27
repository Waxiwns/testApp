package web;

import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

public class LoginSteps {
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
    public String txtDashMsgTitleEn = "WARNING";
    public String txtDashMsgEn = "The sound notifications about new orders are enabled.";
    public String txtDashMsgOkBtnEN = "OK";
    public String languageEn = "English";

    public String txtErrorMsgTitleAr = "خطأ";
    public String txtErrorMsgAr = "خطأ في تسجيل الدخول";
    public String txtLoginBlockTitleAr = "بوابة المتجر";
    public String txtFormTitleAr = "دخول";
    public String txtFormIdTitleAr = "هوية المستخدم";
    public String txtFormPassTitleAr = "كلمة المرور";
    public String txtBtnLoginAr = "تسجيل الدخول";
    public String txtLocaleAr = "العربية";
    public String txtDashMsgTitleAr = "تحذير";
    public String txtDashMsgAr = "تم تفعيل اشعارات الصوت للطلبات الجديدة";
    public String txtDashMsgOkBtnAr = "متابعة";
    public String languageAr = "العربية";

    private LoginPage loginPage = new LoginPage();
    private DashboardPage dashboardPage= new DashboardPage();

    public void openLoginPage(){
        open(url);

        loginPage.loginBtnIsDisplayed();
    }

    public void logIn(String id, String pass){
//        fill values
        loginPage.fillValues(id, pass);
        loginPage.clickLogin();

    }

    public void fillValues(String id, String pass){
//        fill values
        loginPage.fillId(id);
        loginPage.fillPass(pass);
    }

    public void logInWithCorrectValues(String id, String pass){
        logIn(id, pass);

//        close message form
        closeMsgForm();

//        wait for logout button
        dashboardPage.logoutBtnIsDisplayed();    }

    public void logInWithIncorrectValues(String id, String pass){
        logIn(id, pass);

//        wait for error message
        loginPage.errorMsgIsDisplayed();
    }

    public void errorMsgDisappeared(){
        loginPage.errorMsgIsDisplayed();

//        error message should not be displayed
        Configuration.timeout = timeout;
        loginPage.errorMsgIsNotDisplayed();
    }

    public void changeLocaleTo(String lang){
        loginPage.languageDrop().click();
        loginPage.languageDropItem(lang).click();
    }

    public void loginPageEnglishLocale(){
        loginPage.loginBlockTitle().shouldHave(text(txtLoginBlockTitleEn));
        loginPage.formTitle().shouldHave(text(txtFormTitleEn));
        loginPage.formInputTitle(0).shouldHave(text(txtFormIdTitleEn));
        loginPage.formInputTitle(1).shouldHave(text(txtFormPassTitleEn));
        loginPage.languageDrop().shouldHave(text(txtLocaleEN));
        loginPage.loginBtn().shouldHave(text(txtBtnLoginEn));
    }

    public void loginPageArabicLocale(){
        loginPage.loginBlockTitle().shouldHave(text(txtLoginBlockTitleAr));
        loginPage.formTitle().shouldHave(text(txtFormTitleAr));
        loginPage.formInputTitle(0).shouldHave(text(txtFormIdTitleAr));
        loginPage.formInputTitle(1).shouldHave(text(txtFormPassTitleAr));
        loginPage.languageDrop().shouldHave(text(txtLocaleAr));
        loginPage.loginBtn().shouldHave(text(txtBtnLoginAr));
    }

    public void shouldBeLocale(String locale){
        switch (locale){
            case ("EN"):
                loginPageEnglishLocale();
                break;
            case ("AR"):
                loginPageArabicLocale();
                break;
        }
    }

    public void errorMsgEn(){
        loginPage.errorMsgIsDisplayed();
        loginPage.errorMsgTitle().shouldHave(text(txtErrorMsgTitleEn));
        loginPage.errorMsg().shouldHave(text(txtErrorMsgEn));
    }

    public void errorMsgAr(){
        loginPage.errorMsgIsDisplayed();
        loginPage.errorMsgTitle().shouldHave(text(txtErrorMsgTitleAr));
        loginPage.errorMsg().shouldHave(text(txtErrorMsgAr));
    }

    public void errorMsgShouldBeLocale(String locale){
        switch (locale){
            case ("EN"):
                errorMsgEn();
                break;
            case ("AR"):
                errorMsgAr();
                break;
        }
    }

    public void dashboardMsgEn(){
        dashboardPage.msgFormSuccessBtbDisplayed();
        dashboardPage.msgFormTitle().shouldHave(text(txtDashMsgTitleEn));
        dashboardPage.msgFormTxt().shouldHave(text(txtDashMsgEn));
        dashboardPage.msgFormSuccessBtb().shouldHave(text(txtDashMsgOkBtnEN));
        dashboardPage.langDropMenu().shouldHave(text(languageEn));
    }

    public void dashboardMsgAr(){
        dashboardPage.msgFormSuccessBtbDisplayed();
        dashboardPage.msgFormTitle().shouldHave(text(txtDashMsgTitleAr));
        dashboardPage.msgFormTxt().shouldHave(text(txtDashMsgAr));
        dashboardPage.msgFormSuccessBtb().shouldHave(text(txtDashMsgOkBtnAr));
        dashboardPage.langDropMenu().shouldHave(text(languageAr));
    }

    public void dashboardMsgShouldBeLocale(String locale){
        switch (locale){
            case ("EN"):
                dashboardMsgEn();
                break;
            case ("AR"):
                dashboardMsgAr();
                break;
        }
    }

    public void closeMsgForm(){
//        wait for message form
        dashboardPage.msgFormSuccessBtbDisplayed();
        dashboardPage.msgFormSuccessBtb().click();
        dashboardPage.msgFormSuccessBtbNotDisplayed();
    }

    public void logOut(){
        dashboardPage.logoutBtnIsDisplayed();
        dashboardPage.logoutBtn().click();
        loginPage.loginBtnIsDisplayed();
    }

    public void jsFillValues(String id, String pass){
//        fill values
        loginPage.jsFillId(id);
        loginPage.jsFillPass(pass);
    }

    public void jsLogIn(String id, String pass){
        jsFillValues(id, pass);
        loginPage.jsClickLogin();
    }

    public void jsClickLogin(){
        loginPage.jsClickLogin();
    }

    public void refreshPage(){
        loginPage.refreshPage();
    }

    public void openAlert(String msg){
        loginPage.openAlert(msg);
    }

    public void visitPage(String url){
        loginPage.visitPage(url);
    }
}
