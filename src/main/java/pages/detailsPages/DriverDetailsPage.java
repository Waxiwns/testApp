// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.detailsPages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class DriverDetailsPage {

    public SelenideElement firstNameEn = $(xpath("//input[@name='firstName']"));
    public SelenideElement lastNameEn = $(xpath("//input[@name='lastName']"));
}
