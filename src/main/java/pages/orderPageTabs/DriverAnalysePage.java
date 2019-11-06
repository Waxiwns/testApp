// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DriverAnalysePage {

    public SelenideElement pageTitle = $(".card-header strong");
    public SelenideElement getButton = $(".input-group [type='button']");
    public SelenideElement idInputField = $(".input-group [type='text']");
    public SelenideElement resultBlock = $(".card-block pre");


    //    page text
    public static final String PAGE_TITLE = "Driver analyse";
}