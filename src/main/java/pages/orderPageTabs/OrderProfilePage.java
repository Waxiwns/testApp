// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class OrderProfilePage {

    public SelenideElement paymentMethod = $(xpath("//span[@class='font-bold']"));
    public SelenideElement vatValue = $$(xpath("//*[@class = 'card-title' and text() = 'FINANCES']/parent::*//p[contains(., 'VAT')]")).get(2);
    public ElementsCollection historyTable = $$(xpath("//tbody/tr"));
    public SelenideElement orderTrackingTable = $(".card-group");
    public SelenideElement activeBlockOrderTrackingTable = $(xpath("//div[contains(@class, 'text-white')]"));

    //change payment method pop-uo
    public SelenideElement paymentPopUpSelect = $("#paymentMethod");
    public SelenideElement paymentPopUpCrossButton = $(xpath("//am-order-change-payment-type-popup//button[@class='close']"));
    public SelenideElement paymentPopUpCancelButton = $(xpath("//am-order-change-payment-type-popup//button[@class='btn btn-secondary']"));
    public SelenideElement paymentPopUpSaveButton = $(xpath("//am-order-change-payment-type-popup//button[@class='btn btn-primary']"));
    public SelenideElement paymentPopUpTitle = $(xpath("//am-order-change-payment-type-popup//*[@class='modal-title']"));

    //    New element by Max
    public String sectionTitleLocator(String section) {
        // bypass incorrect locator in UI
        switch (section) {
            case CUSTOMER:
                return "//*[@class = 'card-title']/span[text() = '" + section + "']/parent::*";
            case CUSTOMER_CASHIER:
                return "//*[@class = 'card-title']/span[text()= 'CUSTOMER']/following-sibling::span[contains( text(),'(CASHIER)')]/parent::*";
            default:
                return "//*[@class = 'card-title' and text() = '" + section + "']";
        }
    }

    public SelenideElement sectionTitle(String section) {
        return $(xpath(sectionTitleLocator(section)));
    }

    public SelenideElement sectionParameter(String section, String parameter) {
        return $(xpath(sectionTitleLocator(section) + "/parent::*//p[contains(., '" + parameter + "')]"));
    }

    public SelenideElement sectionParameterMessageIcon(String section, String parameter) {
        return $(xpath(sectionTitleLocator(section) + "/parent::*//p[contains(., '" + parameter + "')]//i[contains(@class, 'fa-comment-o')]"));
    }

    public SelenideElement sectionParameterTooltip(String section, String parameter) {
        return $(xpath(sectionTitleLocator(section) + "/parent::*//p[contains(., '" + parameter + "')]"));
    }

    public SelenideElement sectionButton(String section, String textButton) {
        return $(xpath(sectionTitleLocator(section) + "/ancestor::div[@class = 'card-block'][1]//*[contains(@class, 'btn-primary') and contains(., '" + textButton + "')]"));
    }

    public SelenideElement sectionParameterButton(String section, String parameter, String textButton) {
        return $(xpath(sectionTitleLocator(section) + "/ancestor::div[@class = 'card-block'][1]/p[contains(., '" + parameter + "')]//*[contains(text(), '" + textButton + "')]"));
    }


    //    page text
    //    section titles
    public static final String ORDER = "ORDER";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String CUSTOMER_CASHIER = "CUSTOMER (CASHIER)";
    public static final String DRIVER = "DRIVER";
    public static final String FINANCES = "FINANCES";
    public static final String AREA = "AREA";
    public static final String ORDER_TRACKING = "ORDER TRACKING";
    public static final String MAP = "MAP";
    public static final String HISTORY = "HISTORY";
    public static final String LOCATIONS = "LOCATIONS";
    public static final String PICK_UP = "Pick-up";
    public static final String DROP_OFF = "Drop-off";
    public static final String ACTIONS = "ACTIONS";
    public static final String ORDER_INFO = "ORDER INFO";
    public static final String ORDER_NOTES = "ORDER NOTES";
    public static final String CONTACT = "CONTACT";
    public static final String ORDERS = "Orders";

    //   order parameters
    public static final String ID = "ID:";
    public static final String STATUS = "Status:";
    public static final String MERCHANT_STATUS = "Merchant status:";
    public static final String TO_SUPPORT = "To Support:";
    public static final String SERVICE_GROUP = "Service Group:";
    public static final String CREATED = "Created:";
    public static final String REQUIRED_VEHICLE_TYPE = "Required vehicle type:";
    public static final String REQUIRED_DELIVERY_TIME = "Required delivery time:";
    public static final String FINISHED_TIME = "Finished time:";
    public static final String DRIVER_RATING_FOR_THIS_ORDER = "Driver rating for this order:";
    public static final String ASAP = "ASAP";
    public static final String SURPRISE = "Surprise";

    //  customer and driver parameters
    public static final String NAME = "Name:";
    public static final String PHONE = "Phone:";

    //  driver parameters
    public static final String CAR = "Car:";
    public static final String FLEET = "Fleet:";
    public static final String NA = "n/a";

    //  finances parameters
    public static final String BASKET = "Basket:";
    public static final String SERVICE_FEE = "Service fee:";
    public static final String DELIVERY_FEE = "Delivery fee:";
    public static final String TOTAL_COST = "Total Cost (Including VAT):";
    public static final String TOTAL = "Total (Including VAT):";
    public static final String PAYMENT = "PAYMENT:";
    public static final String DRIVER_PARAMETER = "Driver:";
    public static final String CUSTOMER_PARAMETER = "Customer:";
    public static final String CUSTOMER_PARAMETER_DROP_OFF = "Customer (DROP OFF):";
    public static final String CUSTOMER_PARAMETER_CASHIER = "(CASHIER)";
    public static final String PROMO_CODE = "Promo code";
    public static final String COURIER = "COURIER";
    public static final String SMOKETAXI = "SMOKETAXI";
    public static final String VAT = "VAT:";
    public static final String CANCELLATION_FEE = "Cancellation Fee:";
    public static final String DISCOUNT = "Discount:";

    public static final String PRICE_0_AND_00_SAR = "0.00 SAR";
    public static final String PRICE_40_AND_00_SAR = "40.00 SAR";
    public static final String PRICE_132_AND_73_SAR = "132.73 SAR";
    public static final String PRICE_13_AND_27_SAR = "13.27 SAR";
    public static final String PRICE_10_AND_00_SAR = "10.00 SAR";
    public static final String PRICE_146_AND_00_SAR = "146.00 SAR";
    public static final String PRICE_109_AND_40_SAR = "109.40 SAR";
    public static final String NOT_AVAILABLE_YET = "not available yet";
    public static final String PRICE_69_AND_40_SAR = "69.40 SAR";
    public static final String NEW_DELIVERY_FEE = "4";
    public static final String PRICE_9_AND_95_SAR = "9.95 SAR";

    public static final String VAT_COST_TAXI = "0.67 SAR";
    public static final String DELIVERY_FEE_COST_TAXI = "12.33 SAR";
    public static final String TOTAL_COST_PARAMETER_TAXI = "7.33 SAR";
    public static final String TOTAL_PARAMETER_TAXI = "7.33 SAR";

    public static final String CHANGE_PAYMENT_TYPE_FOR_ORDER_TITLE = "Change payment type for order";


    //  area parameters
    public static final String COUNTRY = "Country:";
    public static final String CITY = "City:";

    public static final String YES = "YES";
    public static final String NO = "No";
    public static final String CASH = "CASH";
    public static final String NO_SETTLEMENTS = "NO_SETTLEMENTS";
    public static final String CREDIT_CARD = "CC";
}