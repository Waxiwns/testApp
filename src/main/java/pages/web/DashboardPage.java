package pages.web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private String msgForm = ".modal-content";

    public SelenideElement msgFormTitle(){
        return $(msgForm + " .card-header");
    }

    public SelenideElement msgFormTxt(){
        return $(msgForm + " .card-block");
    }

    public SelenideElement msgFormSuccessBtb(){
        return $(msgForm + " .btn-success");
    }

    public void msgFormSuccessBtbDisplayed(){
        msgFormSuccessBtb().isDisplayed();
    }

    public void msgFormSuccessBtbNotDisplayed(){
        msgFormSuccessBtb().shouldNot(visible);
    }

    public SelenideElement langDropMenu() {
        return $(".header-menu__lang");
    }

    public SelenideElement logoutBtn() {
        return $(".header-menu__logout");
    }

    public void logoutBtnIsDisplayed(){
        logoutBtn().isDisplayed();
    }
}
