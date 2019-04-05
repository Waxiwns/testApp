package pages.web;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private String languageDropMenu = "//select[@name = 'uiLang']";

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
}
