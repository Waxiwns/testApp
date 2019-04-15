package steps.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.refresh;

public class Steps {

    public void refreshPage(){
        printStepName();

        refresh();
    }

    public void jsRefreshPage(){
        printStepName();

        executeJavaScript("history.go(0)");
    }

    public void jsVisitPage(String url){
        printStepName();

        executeJavaScript("window.location = '" + url + "'");
    }

    public void jsOpenAlert(String msg){
        printStepName();

        executeJavaScript("alert('" + msg + "');");
    }

    public void jsClickElement(SelenideElement element){
        printStepName();

        executeJavaScript("arguments[0].click();", element);
    }

//    for dynamic items
    public void getPageDom(){
        printStepName();
        executeJavaScript("return document.documentElement.outerHTML");
    }

    public ElementsCollection jsAllCssElements(String css){
        printStepName();

//        executeScript All css elements
        return executeJavaScript("document.querySelectorAll('" + css + "')[0].click();");
    }

    public String pageUrl(){
        printStepName();

        String text;
        text = executeJavaScript("return document.URL;").toString();

        System.out.println(text);
        return text;
    }

    public String pageTitle(){
        printStepName();

        String text;
        text = executeJavaScript("return document.title;").toString();

        System.out.println(text);
        return text;
    }

    public String pageDomain(){
        printStepName();

        String text;
        text = executeJavaScript("return document.domain;").toString();

        System.out.println(text);
        return text;
    }

    public void vertScrollByPx(int from, int to){
        printStepName();

        executeJavaScript("window.scrollBy(" + from + "," + to + ")");
    }

    public void scrollToElement(SelenideElement element){
        printStepName();

        executeJavaScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsFillValue(String ElementId, String value) {
        printStepName();

        executeJavaScript("document.getElementById('" + ElementId + "').value='" + value + "';");
    }

    public void jsFillValue(SelenideElement el, String value) {
        printStepName();

        executeJavaScript("arguments[0].value='" + value + "';", el);
    }

    public void printStepName(){
        StackTraceElement throwable = new Throwable().getStackTrace()[1];

        String className = throwable.getClassName().split("\\.")[throwable.getClassName().split("\\.").length - 1];
        String methodName = throwable.getMethodName();

        System.out.println(className + ": " + methodName);
    }
}
