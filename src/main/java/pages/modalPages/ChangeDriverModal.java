// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.modalPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class ChangeDriverModal {
    public SelenideElement modal = $(xpath("//*[@class = 'modal-title mr-auto' and text() = 'Change Order Driver']/ancestor::*[@class = 'modal-dialog modal-lg']"));
    public SelenideElement modalMapTitle = $(xpath("//h6[@class='modal-title mr-auto']"));
    public SelenideElement showMapButton = $(xpath("//button[contains(., 'Show map')]"));
    public SelenideElement showTableButton = $(xpath("//button[contains(., 'Show table')]"));
    public SelenideElement crossModelMap = $(xpath("//button[contains(., 'Show table')]/parent::div/parent::div/parent::div//button[@class='close']"));
    public SelenideElement modalMapСloseButton = $("am-lookup-driver-dialog .modal-footer .btn.btn-secondary");

    public SelenideElement userPhoneOrEmailField = $(xpath("//input[@name ='searchPattern']"));
    public SelenideElement userFirstNameField = $(xpath("//input[@name ='firstName']"));
    public SelenideElement userLastNameField = $(xpath("//input[@name ='lastName']"));
    public SelenideElement driverStatusField = $(xpath("//select[@name ='orderId']"));
    public SelenideElement cityField = $(xpath("//kendo-autocomplete[@name ='cityList']//input"));
    public SelenideElement serviceTypeField = $(xpath("//am-service-type-select[@name ='serviceType']/span/select"));

    public SelenideElement userPhoneOrEmailLabel = $(xpath("//input[@name ='searchPattern']/parent::div/label"));
    public SelenideElement userFirstNameLabel = $(xpath("//input[@name ='firstName']/parent::div/label"));
    public SelenideElement userLastNameLabel = $(xpath("//input[@name ='lastName']/parent::div/label"));
    public SelenideElement driverStatusLabel = $(xpath("//select[@name ='orderId']/parent::div/parent::div/label"));
    public SelenideElement cityLabel = $(xpath("//am-city-autocomplete/parent::div/parent::div/label"));
    public SelenideElement serviceTypeLabel = $(xpath("//am-service-type-select/parent::div/label"));

    public SelenideElement filterCancelButton = $(xpath("//am-filter-buttons-new//button[contains(@class,'warning')]"));
    public SelenideElement applyFilterButton = $(xpath("//button[@title='Apply filter']"));

    public SelenideElement cityMap = $(xpath("//div[contains(@class, 'modal-body')]//*[contains(@class, 'agm-map-container-inner')]//iframe"));
    public SelenideElement searchResults = $(xpath("//div/kendo-grid//tbody/tr/td[2][contains(.,'TestDriverForSmoke')]"));

    public SelenideElement searchResultsBlock = $("div.modal-body table tbody");

    public SelenideElement cityMapGoogleLink(String locationLatOrLon) {
        return $(xpath("//div[contains(@class, 'modal-body')]//*[contains(@class, 'agm-map-container-inner')]//a[contains(@href, 'https://maps.google.com/maps?') and contains(@href, '" + locationLatOrLon.substring(0, 8) + "')]"));
    }

    public SelenideElement onlineCarMap(int num) {
        return $$(xpath("//img[contains(@src, '" + ONLINE_DRIVER_CAR_IDENTIFICATION + "')]")).get(num);
    }

    // driver car window info
    public SelenideElement driverDetailsLinkInInfoWindow(String driverName) {
        return $(xpath("//*[@class = 'agm-info-window-content']//table//a[contains(text(), '" + driverName + "')]"));
    }

    public SelenideElement driverDetailsSelectButtonInInfoWindow(String driverName) {
        return $(xpath("//*[@class = 'agm-info-window-content']//table//a[contains(text(), '" + driverName + "')]/ancestor::tbody//button[contains(., '" + SELECT + "')]"));
    }

    public ElementsCollection getSearchLineFromResultBlock(String textFilter) {
        String containsText = "";
        switch (textFilter) {
            case "ANY":
                containsText = "";
                break;
            case "READY":
            case "OFFLINE":
                containsText = textFilter;
                break;
            case "ORDER_OFFLINE":
                containsText = "ORDER OFFLINE";
                break;
            case "ORDER_EXECUTION":
                containsText = "ORDER EXECUTION";
                break;
            default:
                containsText = textFilter;
        }
        String xpathString = "//tr[contains(.,\"" + containsText + "\")]";
        ElementsCollection result = searchResultsBlock.findAll(By.xpath(xpathString));
        return result;
    }

    public SelenideElement getEmptyResultBlock() {

        return searchResultsBlock.find(By.xpath("//tr/td/ div"));
    }


    public SelenideElement selectDriverButton = $(".btn.btn-sm.btn-secondary");


    public SelenideElement dayOfMonth(String day) {
        return $(xpath("//input[@placeholder = 'Date']//ancestor::*[@class = 'date-box']//*[@class = 'd-block']//*[contains(@class, 'btn-light')][text() = '" + day + "']"));
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

    public SelenideElement deliveryDateTitle(String modalTitle, String title) {
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

    //    drivers table
    private String driversTable = "//*[contains(@class, 'modal-dialog')]//table";

    private String driversTableLineById(String driverId) {
        return driversTable + "/tbody//td[text() = '" + driverId + "']/parent::*";
    }

    private String driversTableLineByIdCellsCollection(String driverId) {
        return driversTableLineById(driverId) + "/td";
    }

    public SelenideElement driversTableHeaderCell(String cellName) {
        return $(xpath(driversTable + "//th[text() = '" + cellName + "']"));
    }

    public ElementsCollection driversTableLineByIdCells(String driverId) {
        return $$(xpath(driversTableLineByIdCellsCollection(driverId)));
    }

    public SelenideElement driversTableSelectButtonById(String driverId) {
        return $(xpath(driversTableLineByIdCellsCollection(driverId) + "[6]/button"));
    }

    public SelenideElement filterField(String filterName) {
        String field = "input";
        if (filterName.equals(DRIVER_STATUS) || filterName.equals(SERVICE_TYPE)) field = "select";

        return $(xpath("//*[@class = 'filter-form-group']/label[. = '" + filterName + "']/parent::div//" + field));
    }


    //    page text
    public static final String TITLE = "Change Order Driver";
    public static final String NO_RECORDS_AVAILABLE = "No records available";
    public static final String PHONE_OR_EMAIL_LABEL = "Phone or email";
    public static final String FIRST_NAME_LABEL = "First name";
    public static final String LAST_NAME_LABEL = "Last name";
    public static final String DRIVER_STATUS_LABEL = "Driver status";
    public static final String CITY_LABEL = "City";
    public static final String SERVICE_TYPE_LABEL = "Service type";

    //    drivers Table Header Cells
    public static final String ID = "Id";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String TRANSPORT = "Transport";
    public static final String STATUS = "Status";
    public static final String SELECT = "Select";

    //    drivers filter names
    public static final String PHONE_OR_EMAIL = "Phone or email";
    public static final String FIRST_NAME = "First name";
    public static final String LAST_NAME = "Last name";
    public static final String SERVICE_TYPE = "Service type";
    public static final String CITY = "City";
    public static final String DRIVER_STATUS = "Driver status";
    public static final String ONLINE_DRIVER_CAR_IDENTIFICATION = "iVBORw0KGgoAAAANSUhEUgAAACoAAAAqCAYAAADFw8lbAAA";
}
