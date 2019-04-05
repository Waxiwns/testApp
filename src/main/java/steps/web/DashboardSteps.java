package steps.web;

import pages.web.DashboardPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class DashboardSteps extends Steps {

    public String txtDashMsgTitleEn = "WARNING";
    public String txtDashMsgEn = "The sound notifications about new orders are enabled.";
    public String txtDashMsgOkBtnEN = "OK";
    public String languageEn = "English";

    public String txtDashMsgTitleAr = "تحذير";
    public String txtDashMsgAr = "تم تفعيل اشعارات الصوت للطلبات الجديدة";
    public String txtDashMsgOkBtnAr = "متابعة";
    public String languageAr = "العربية";

    private LoginSteps loginSteps = new LoginSteps();

    private DashboardPage dashboardPage= new DashboardPage();

    public void logInWithCorrectValues(String id, String pass){
        printStepName();

        loginSteps.logInWithCorrectValues(id, pass);

//        close message form
        closeMsgForm();

//        wait for logout button
        logoutBtnIsDisplayed();
    }

    public void dashboardMsgShouldBeLocale(String locale){
        printStepName();

        switch (locale){
            case ("EN"):
                msgFormSuccessBtbDisplayed();
                dashboardPage.msgFormTitle().shouldHave(text(txtDashMsgTitleEn));
                dashboardPage.msgFormTxt().shouldHave(text(txtDashMsgEn));
                dashboardPage.msgFormSuccessBtb().shouldHave(text(txtDashMsgOkBtnEN));
                dashboardPage.langDropMenu().shouldHave(text(languageEn));
                break;
            case ("AR"):
                msgFormSuccessBtbDisplayed();
                dashboardPage.msgFormTitle().shouldHave(text(txtDashMsgTitleAr));
                dashboardPage.msgFormTxt().shouldHave(text(txtDashMsgAr));
                dashboardPage.msgFormSuccessBtb().shouldHave(text(txtDashMsgOkBtnAr));
                dashboardPage.langDropMenu().shouldHave(text(languageAr));
                break;
        }
    }

    public void logoutBtnIsDisplayed(){
        printStepName();

        dashboardPage.logoutBtn().isDisplayed();
    }

    public void closeMsgForm(){
        printStepName();

//        wait for message form
        msgFormSuccessBtbDisplayed();
        dashboardPage.msgFormSuccessBtb().click();
        msgFormSuccessBtbNotDisplayed();
    }

    public void logOut(){
        printStepName();

        logoutBtnIsDisplayed();
        dashboardPage.logoutBtn().click();
        loginSteps.loginBtnIsDisplayed();
    }

    public void chooseNaviTab(String tabName){
        printStepName();

        dashboardPage.naviTab(tabName).click();

        System.out.println(dashboardPage.activeNaviTab().text());
        dashboardPage.activeNaviTab().shouldHave(text(tabName));
    }

    public void naviTabsSize(int size){
        printStepName();

        dashboardPage.naviTabs().shouldHaveSize(size);
    }

    public void msgFormSuccessBtbDisplayed(){
        printStepName();

        dashboardPage.msgFormSuccessBtb().isDisplayed();
    }

    public void msgFormSuccessBtbNotDisplayed(){
        printStepName();

        dashboardPage.msgFormSuccessBtb().shouldNot(visible);
    }
}
