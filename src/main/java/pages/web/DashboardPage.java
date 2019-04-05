package pages.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DashboardPage {
    private String msgForm = ".modal-content";

    public JavascriptExecutor getJSDriver(){
        return (JavascriptExecutor) getWebDriver();
    }

    public void refreshPage(){
        getJSDriver().executeScript("history.go(0)");
    }

    public SelenideElement msgFormTitle(){
        return $(msgForm + " .card-header");
    }

    public SelenideElement msgFormTxt(){
        return $(msgForm + " .card-block");
    }

    public SelenideElement msgFormSuccessBtb(){
        return $(msgForm + " .btn-success");
    }

    public SelenideElement langDropMenu() {
        return $(".header-menu__lang");
    }

    public SelenideElement logoutBtn() {
        return $(".header-menu__logout");
    }

    public String tabs(){
        return ".aside-nav__text";
    }

    public ElementsCollection naviTabs(){
        return $$(tabs());
    }

    public SelenideElement naviTab(String tabName){
        return naviTabs().findBy(text(tabName));
    }

    public SelenideElement activeNaviTab(){
        return $(".active " + tabs());
    }
}
