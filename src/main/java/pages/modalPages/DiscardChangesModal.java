// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.modalPages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class DiscardChangesModal {

    public SelenideElement buttonYes = $(xpath("//div[@class='modal-footer']//button[@class='btn btn-primary']"));
    public SelenideElement buttonNo = $(xpath("//div[@class='modal-footer']//button[1]"));
    public SelenideElement titleModalWindow = $(xpath("//div[@class='modal-footer']/parent::div/div[1]/h4"));
    public SelenideElement crossButton = $(xpath("//div[@class='modal-footer']/parent::div/div[1]/button"));
    public SelenideElement bodyContent = $(xpath("//div[@class='modal-footer']/parent::div/div[2]/p"));

    //    page text
    public static final String TITLE = "Discard Changes";
}