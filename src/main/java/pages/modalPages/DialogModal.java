// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.modalPages;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class DialogModal {

    public SelenideElement calendar = $(".fa-calendar");
    public SelenideElement clock = $(".fa-clock-o");
    public SelenideElement dateField = $(xpath("//input[@placeholder = 'Date']"));
    public SelenideElement timeField = $(xpath("//input[@placeholder = 'Time']"));
    public SelenideElement clearDate = $(xpath("//input[@placeholder = 'Date']//ancestor::*[@class = 'date-box']/button[@title = 'Clear']"));
    public SelenideElement clearTime = $(xpath("//input[@placeholder = 'Time']//ancestor::*[@class = 'date-box']/button[@title = 'Clear']"));
    public SelenideElement monthDropDown = $(xpath("//input[@placeholder = 'Date']//ancestor::*[@class = 'date-box']//select[1]"));
    public SelenideElement yearDropDown = $(xpath("//input[@placeholder = 'Date']//ancestor::*[@class = 'date-box']//select[2]"));
    public SelenideElement hoursInput = $(xpath("//input[@placeholder = 'Time']//ancestor::*[@class = 'date-box']//input[@placeholder='HH']"));
    public SelenideElement minutesInput = $(xpath("//input[@placeholder = 'Time']//ancestor::*[@class = 'date-box']//input[@placeholder='MM']"));
    public SelenideElement reasonInputClean = $(xpath("//textarea[@id = 'text' and contains(@class, 'ng-invalid')]"));
    public SelenideElement reasonInput = $(xpath("//textarea[@id = 'text' and contains(@class, 'ng-valid')]"));

    public SelenideElement dayOfMonth(String day) {
        return $(xpath("//input[@placeholder = 'Date']//ancestor::*[@class = 'date-box']//*[@class = 'd-block']//*[contains(@class, 'btn-light') and not(contains(@class, 'text-muted'))  ][text() = '" + day + "']"));
    }

    private String titleString(String modalTitle) {
        return "//*[contains(@class , 'modal-title') and text() = '" + modalTitle + "']";
    }

    private String modalString(String modalTitle) {
        return titleString(modalTitle) + "/ancestor::*[@class = 'modal-content text-darkcyan']";
    }

    public SelenideElement modal(String modalTitle) {
        return $(xpath(modalString(modalTitle)));
    }

    public SelenideElement title(String modalTitle) {
        return $(xpath(titleString(modalTitle)));
    }

    public SelenideElement sectionTitle(String modalTitle, String title) {
        return $(xpath(modalString(modalTitle) + "//*[@for and text() = '" + title + "']"));
    }

    public SelenideElement closeModalBtn(String modalTitle) {
        return $(xpath(modalString(modalTitle) + "//button[@class = 'close']"));
    }

    public SelenideElement footerModalButton(String modalTitle, String button) {
        return $(xpath(modalString(modalTitle) + "//*[@class = 'modal-footer']/button[contains(text(), '" + StringUtils.capitalize(button.toLowerCase()) + "')]"));
    }

    public SelenideElement bodyModalGroup(String modalTitle, String labelText) {
        return $(xpath(modalString(modalTitle) + "//*[@class = 'filter-form-group'and contains(text(),'" + labelText + "')]"));
    }


    //    page text
    public static final String CHANGE_DELIVERY_DATE_TITLE = "Change delivery date";
    public static final String DELIVERY_TITLE = "Delivery:";
    public static final String CANCEL_ORDER = "Cancel Order";
    public static final String REASON = "Reason:";
}
