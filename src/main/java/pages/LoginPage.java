// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class LoginPage {
    public SelenideElement email = $("#email");

    public SelenideElement password = $("#password");

    public SelenideElement submit = $("#submit");

    public SelenideElement languageSwitcher = $(xpath("//select"));

    public SelenideElement emailFieldLabel = $(xpath("//div[@class='form-group']/*[1]"));

    public SelenideElement passFieldLabel = $(xpath(".//div[@class='form-group mb-4']/label"));

    public SelenideElement errorMessageInLogInBlock = $(xpath(".//div[@class='alert alert-danger']"));

    public SelenideElement loginPageTitle = $(cssSelector(".login-block__title"));

    public SelenideElement loginBlockTitle = $(xpath("//div[@class='card-header']"));

    //    page text
    public static final String EMAIL_FIELD_TITLE_AR = "البريد الالكتروني";
    public static final String PASSWORD_FIELD_TITLE_AR = "كلمه السر";
    public static final String SUBMIT_BUTTON_AR = "تسجيل الدخول";
    public static final String LANGUAGE_SWITCHER_AR = "العربية";
    public static final String EMAIL_PLACEHOLDER = "Email";
    public static final String PASSWORD_PLACEHOLDER = "Password";
    public static final String ERROR_FIELD_TITLE_AR = "خطأ تسجيل الدخول وكلمة المرور";
    public static final String ERROR_FIELD_TITLE_EN = "Wrong login and password";
}
