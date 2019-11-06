// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.detailsPages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class MerchantUserDetailsPage {

    public SelenideElement pageTitle = $(".ml-2");
    public SelenideElement firstName = $(xpath("//input[@name='firstName']"));
    public SelenideElement lastName = $(xpath("//input[@name='lastName']"));

}
