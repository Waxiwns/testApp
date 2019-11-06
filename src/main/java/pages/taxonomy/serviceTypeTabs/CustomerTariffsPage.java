// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.taxonomy.serviceTypeTabs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class CustomerTariffsPage {

    public SelenideElement activePage = $(xpath("//pages//li//a[contains(@class,'nav-link active')]"));
    public SelenideElement operationAreaInputField = $(xpath("//span[contains(@class, 'k-dropdown-wrap k-state-default')]//input"));
    public SelenideElement saveButton = $(".save-cancel-btn-wrapper .btn-primary");

    public SelenideElement getCheckbox(String header, String nameField) {
        return $(xpath("//h4[text() = '" + header + "']/parent::*//label[contains(., '" + nameField + "')]/following-sibling::am-material-switch//input"));
    }

    public SelenideElement getFeeInputField(String header, String nameField) {
        return $(xpath("//h4[text() = '" + header + "']/parent::*//label[contains(., '" + nameField + "')]/following-sibling::input "));
    }

    public SelenideElement getOrderPageTab(String pageUrl) {
        return $(xpath("//article//a[contains(@href, '" + pageUrl + "')]"));
    }

    public SelenideElement getDeliveryTableHeader(String tableName) {
        return $(xpath("//h4[text() = '" + tableName + "']/parent::*"));
    }

    //Tabs
    public static final String GENERAL = "General";
    public static final String CATEGORIES = "Categories";
    public static final String MERCHANTS = "Merchants";
    public static final String PARAMETERS = "Parameters";
    public static final String CUSTOMER_TARIFFS = "Customer Tariffs";

    //Headers
    public static final String DELIVERY = "Delivery";
    public static final String CANCELLATION = "Cancellation";
    public static final String PRE_AUTHORIZATION_PAYMENT = "Pre-authorization payment";
    public static final String SERVICE_FEE = "Service Fee (from basket cost)";

    //Delivery fields
    public static final String MINIMAL_FEE = "Minimal Fee";
    public static final String FIXED_FEE = "Fixed Fee";
    public static final String FREE_TIME = "Free time";
    public static final String FREE_DISTANCE = "Free distance";
    public static final String RATE_PER_KM = "Rate per km";
    public static final String RATE_PER_MIN = "Rate per min";
    public static final String DRIVER_SERVICE_FEE = "Driver Service Fee Rate (Percent)";
    public static final String USE_PRE_ESTIMATED_DELIVERY_FEE = "Use pre estimated Delivery Fee";

    //Cancellation fields
    public static final String FREE_TIME_CANCELLATION = "Free time";
    public static final String PENALTY = "Penalty";

    //Pre-authorization payment fields
    public static final String USE_PRE_AUTHORIZATION_PAYMENT = "Use pre-authorization payment";
    public static final String DELIVERY_FEE = "Delivery fee";
    public static final String BASKET_COST = "Basket cost";

    //URL
    public static final String GENERAL_URL = "general";
    public static final String CUSTOMER_TARIFFS_URL = "customer-tariffs";
    public static final String DELIVERY_SERVICE_TYPE = "delivery";
    public static final String COURIER_SERVICE_TYPE = "courier";
    public static final String MERCHANT_DELIVERY_SERVICE_TYPE = "merchant-delivery-booking";
    public static final String TAXI_SERVICE_TYPE = "taxi-econom";

    //Operation area
    public static final String KHARKIV = "Kharkiv";

    //
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String MANDATORY_COST = "1";
    public static final String COST_TEN = "10";
    public static final String FIVE = "5";
    public static final String TWELVE = "12";
}
