// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.detailsPages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class SystemUserDetailsPage {

    public SelenideElement systemUserRoles = $(xpath("//am-multiselect-dropdown"));

    public SelenideElement systemUserFirstName = $(xpath("//input[@name=\"firstName\"]"));

    public SelenideElement systemUserLastName = $(xpath("//input[@name=\"lastName\"]"));

    public SelenideElement systemUserEmail = $(xpath("//input[@name=\"email\"]"));

    public SelenideElement systemUserPhone = $(xpath("//input[@name=\"phone\"]"));


    public SelenideElement getLabelXpath(String name) {
        return $(xpath("//input[@name=\"" + name + "\"]/parent::div/label"));
    }

    //input name
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    //label
    public static final String FIRST_NAME_LABEL = "First Name";
    public static final String LAST_NAME_LABEL = "Last Name";
    public static final String EMAIL_LABEL = "Email";
    public static final String PHONE_LABEL = "Phone";
}
