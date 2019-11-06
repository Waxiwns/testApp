// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.modalPages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class NotificationModalPage {

    public SelenideElement error = $(".simple-notification-wrapper .error");

    public SelenideElement success = $(xpath("//simple-notification//div[contains(@class,'success')]"));

    public SelenideElement info = $(xpath("//simple-notification//div[contains(@class,'info')]"));

    public SelenideElement titleTxt = $(xpath("//simple-notification//div[contains(@class,'sn-title')]"));

    public SelenideElement messageTxt = $(xpath("//simple-notification//div[contains(@class,'sn-content')]"));

    //    page text
    public static final String SWITCH_TO_ANOTHER_ORDER = "Switch to another order";
    public static final String YOU_SUCCESS_SWITCH_TO_ANOTHER_ORDER = "You success switch to another order";
    public static final String UPDATE_DELIVERY_DATE = "Update delivery date";
    public static final String DELIVERY_DATE_WAS_SUCCESSFULLY_UPDATED = "Delivery date was successfully updated.";
    public static final String ORDER_STATUS = "Order status";
    public static final String ORDER_EN = "Order";
    public static final String ORDER_AR = "طلب";
    public static final String ASSIGN_DRIVER = "Assign driver";
    public static final String DRIVER_ASSIGNED_SUCCESSFULLY = "Driver assigned successfully.";
    public static final String ORDER_LINES_HAS_BEEN_UPDATED_EN = "Order lines has been updated";
    public static final String ORDER_LINES_HAS_BEEN_UPDATED_AR = "تم تحديث خطوط الطلب";
    public static final String ORDER_STATUS_WAS_SUCCESSFULLY_UPDATED = "Order status was successfully updated.";
    public static final String COST_CORRECTIONS_UPDATED = "Cost Corrections updated";
    public static final String SUCCESS = "Success";
    public static final String ERROR_422_OK = "Error 422 OK";
    public static final String DELIVERY_ORDER_HAVE_NO_ITEMS = "delivery.order.have.no.items";
    public static final String ORDER = "Order";
    public static final String ORDER_LINES_HAS_BEEN_UPDATED = "Order lines has been updated";
}
