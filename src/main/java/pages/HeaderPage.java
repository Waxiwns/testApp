// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class HeaderPage {

    public SelenideElement usersPhoto = $(xpath(".//i[@class='fa fa-user hidden-sm-up']"));

    public SelenideElement inputOrderSearchField = $(xpath("//div[@class='form-group'][1]/div[@class='input-group']/input"));

    public SelenideElement orderSearchButton = $(xpath("//div[@class='form-group'][1]/div[@class='input-group']/span/button"));

    public SelenideElement languageSwitcher = $("am-lang-dropdown .dropdown-toggle");

    public SelenideElement languageDropItem = $(xpath("//button[@class='dropdown-item']"));
}