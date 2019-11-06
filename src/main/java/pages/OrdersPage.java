// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class OrdersPage {

    public SelenideElement filterButton = $(xpath("//div[@class='col-md-7']/div/button"));

    public SelenideElement orderEntryField = $(xpath("//am-order-combobox/kendo-combobox/span/kendo-searchbar/input"));

    public SelenideElement applyFilterButton = $(xpath("//button[@class='btn btn-primary']"));

    public SelenideElement supportCompleteLink = $(xpath("//a[@class='nav-link' and text()='Support']"));

    public SelenideElement searchResults = $(xpath("//tr/td/a[@class='link']"));

    public SelenideElement title = $(".card-title");

    //    page text
    public static final String TITLE = "Delivery Orders";
}
