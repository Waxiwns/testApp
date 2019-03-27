package web;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage {

    private String languageDropMenu = "//select[@name = 'uiLang']";

    public JavascriptExecutor getJSDriver(){
        return (JavascriptExecutor) getWebDriver();
    }

    public void jsClickElement(SelenideElement element){
//        getJSDriver().executeScript("arguments[0].click();", element);

//        executeScript All css elements
        String cssEl = "#submit";
        getJSDriver().executeScript("document.querySelectorAll('" + cssEl + "')[0].click();");
    }

    public SelenideElement loginBlockTitle(){
        return $(".login-block__title");
    }

    public SelenideElement formTitle(){
        return $(".card-header");
    }

//    id = 0, password = 1
    public SelenideElement formInputTitle(int i){
        return $(".form-group label", i);
    }

    public SelenideElement languageDrop(){
        return $(By.xpath(languageDropMenu));
    }

    public SelenideElement languageDropItem(String lang){
        return $(By.xpath(languageDropMenu + "//*[text() = '" + lang + "']"));
    }

    public SelenideElement idInput(){
        return $("#fm-login__email");
    }

    public SelenideElement passInput(){
        return $("#fm-login__password");
    }

    public SelenideElement loginBtn(){
        return $("#submit");
    }

    public SelenideElement errorMsgTitle(){
        return $(".simple-notification-wrapper .sn-title");
    }

    public SelenideElement errorMsg(){
        return $(".simple-notification-wrapper .sn-content");
    }

    public void errorMsgIsDisplayed(){
        errorMsg().isDisplayed();
    }

    public void errorMsgIsNotDisplayed(){
        errorMsg().shouldNot(visible);
    }

    public void fillId(String id) {
        idInput().setValue(id);
    }

    public void fillPass(String pass) {
        passInput().setValue(pass);
    }

    public void jsFillId(String id) {
        getJSDriver().executeScript("document.getElementById('fm-login__email').value='" + id + "';");
//        or
//        getJSDriver().executeScript("arguments[0].value='" + id + "';", idInput());

    }

    public void jsFillPass(String pass) {
//        getJSDriver().executeScript("document.getElementById('fm-login__password').value='" + pass + "';");
//        or
        getJSDriver().executeScript("arguments[0].value='" + pass + "';", passInput());
    }

    public void clickLogin() {
        loginBtn().hover();
        loginBtn().click();
    }

    public void jsClickLogin(){
        jsClickElement(loginBtn());
    }

    public void fillValues(String id, String pass) {
        fillId(id);
        fillPass(pass);
    }

    public void loginBtnIsDisplayed(){
//        wait for login button
        loginBtn().isDisplayed();
    }

    public void refreshPage(){
        getJSDriver().executeScript("history.go(0)");
    }

    public void visitPage(String url){
        getJSDriver().executeScript("window.location = '" + url + "'");
    }

    public void openAlert(String msg){
        getJSDriver().executeScript("alert('" + msg + "');");
    }

    public void pageText(){
        String text;
//        title
//        text = getJSDriver().executeScript("return document.title;").toString();

//        domain
//        text = getJSDriver().executeScript("return document.domain;").toString();

//        URL
        text = getJSDriver().executeScript("return document.URL;").toString();
        System.out.println(text);
    }

    public void vertScrollByPx(int from, int to){
        getJSDriver().executeScript("window.scrollBy(" + from + "," + to + ")");
    }

    public void scrollToElement(SelenideElement element){
        getJSDriver().executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
