// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class OrderHeaderPage {

    public SelenideElement titleOrderNumber = $(".order-md-1 .mt-1");

    public SelenideElement searchField = $(".order-id-go input");

    public SelenideElement createTicket = $(".order-id-go button");

    public SelenideElement searchGoBtn = $(".order-id-go .input-group-append button");

    public SelenideElement activePage = $(By.xpath("//pages//li//a[contains(@class,'nav-link active')]"));

    public SelenideElement breadcrumbsSector = $(".header");

    public SelenideElement breadCrumbHome = $(".breadcrumb-item [href='#/pages']");

    public SelenideElement breadCrumbTab = $$(xpath("//*[contains(@class, 'breadcrumb-item')]/a")).get(1);

    public SelenideElement activeBreadcrumb = $(xpath("//span[contains(@class, 'active')]//strong"));

    public SelenideElement breadCrumbOrderId(String orderId) {
        return $(".breadcrumb-item a[title='" + orderId + "']");
    }

    public SelenideElement breadCrumbTabPage(String tab) {
        return $(".breadcrumb-item a[title='" + tab + "']");
    }

    public SelenideElement getOrderPageTab(String orderId, String pageName) {
        return $(By.xpath("//article//a[contains(@href, '#/pages/orders/" + orderId + "/" + pageName + "')]"));
    }

    public SelenideElement getOrderPageTabServiceTypeTab(String serviceType, String pageUrl) {
        return $(xpath("//article//a[contains(@href, '#/pages/category-manage/service-types/" + serviceType + "/" + pageUrl + "?serviceTypeId=" + serviceType + "')]"));
    }


    //    page text
    public static final String TITLE = "Order #";
    public static final String SEARCH_PLACEHOLDER = "Search by orderId or orderNumber";
    public static final String DRIVERS = "Drivers";
    public static final String CUSTOMERS = "Customers";
    public static final String MERCHANTS = "Merchants";
}