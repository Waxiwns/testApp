// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class LeftNavigationPanel {

    public SelenideElement dashboardBtn = $(xpath("//nav[@class='menu']/ul[@class='nav metismenu']/li/a[@href='#/pages/dashboard']"));
    public SelenideElement ordersBtn = $(xpath("//nav[@class='menu']/ul[@class='nav metismenu']/li/a[@href='#/pages/orders']"));
    public SelenideElement customersBtn = $(xpath("//nav[@class='menu']/ul[@class='nav metismenu']/li/a[@href='#/pages/customers']"));
    public SelenideElement transactionBtn = $(xpath("//nav[@class='menu']/ul[@class='nav metismenu']/li/a[@href='#/pages/transactions']"));
}
