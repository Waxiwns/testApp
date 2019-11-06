// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class OrderTransactionsPage {

    public SelenideElement externalTransactionsButton = $(xpath("//button[@class='btn btn-sm btn-outline-success btn-xs-stretching']"));
    public SelenideElement headerOfExternalTransactionsPanel = $(xpath("//div[@class='modal-header']"));
    public SelenideElement exportToExcelButton = $(".k-grid-excel");
    public SelenideElement applyFilter = $(".btn-primary-outline");
    public SelenideElement clearFilter = $(".btn .btn-warning");
    public SelenideElement addTransactionButton = $(".card-header>.btn-primary");
    public SelenideElement cardTitle = $(".card-title");

    public SelenideElement getFilterFieldLabel(String elementName) {
        return $(xpath("//*[@name='" + elementName + "']/ancestor::div[@class='filter-order-form-group']/label"));
    }

    private int getTittlePosition(String titleName) {
        String path = "//table//thead//th";
        ElementsCollection thead = $$(xpath(path));
        for (int i = 0; i != thead.size(); i++) {
            if (thead.get(i).getText().equals(titleName))
                return i + 1;
        }
        return -1;
    }

    public SelenideElement getTableElementCell(String typeElement, String titleName, String lineFilter, String buttonTittle) {
        String startXpath = "//table//tbody//tr[contains(.,'" + lineFilter + "')]/td[" + getTittlePosition(titleName) + "]";
        return $(xpath(startXpath + "//" + typeElement + "[contains(.,'" + buttonTittle + "')]"));
    }

    public ElementsCollection getAllColumnFieldsByColumnName(String titleName) {
        int position = getTittlePosition(titleName);
        return position == 1 ? $$(xpath("//tbody//td[" + getTittlePosition(titleName) + "]")) : $$(xpath("//tbody//td[" + getTittlePosition(titleName) + "]/child::*"));
    }

    public SelenideElement getXpathForTableHeaderTitleInTable(String titleName) {
        return $(xpath("//table//thead//th[contains(.,'" + titleName + "')]"));
    }

    public SelenideElement getNoRecordsVariable(String text) {
        return $(xpath("//tbody//div[contains(text(),'" + text + "')]"));
    }

    public SelenideElement getTransactionLineCell(String beneficiaryValue, String accountValue, String operationValue, String titleName) {
        String xpath = "//tbody//tr//td[" + getTittlePosition(BENEFICIARY) + "][contains(.,'" + beneficiaryValue + "')]//ancestor::tr";
        xpath = xpath + "//td[" + getTittlePosition(ACCOUNT) + "][contains(.,'" + accountValue + "')]//ancestor::tr";
        xpath = xpath + "//td[" + getTittlePosition(OPERATION) + "][contains(.,'" + operationValue + "')]//ancestor::tr";
        return $(xpath(xpath + "//td[" + getTittlePosition(titleName) + "]"));
    }

    //ELEMENT
    public final String LINK_XPATH = "a";
    public final String BUTTON_XPATH = "button";

    public static String OPERATION_FIELD_NAME = "txOperationType";
    public static String DATE_FROM_FIELD_NAME = "dpfrom";

    //TITLE_en
    public static String BENEFICIARY = "Beneficiary";
    public static String OPERATION = "Operation";
    public static String ACCOUNT = "Account";
    public static String STATE = "State";
    public static String DESCRIPTION = "Description";
    public static String AMOUNT = "Amount";
    public static String CREATOR = "Creator";
    public static String ID = "ID";
    public static String DATE = "Date";

    //TITLE_ar
    public static String BENEFICIARY_AR = "المستفيد";
    public static String OPERATION_AR = "العملية";
    public static String ACCOUNT_AR = "الحساب";
    public static String STATE_AR = "الحالة";
    public static String DESCRIPTION_AR = "وصف";
    public static String AMOUNT_AR = "المبلغ";
    public static String CREATOR_AR = "Creator";
    public static String ID_AR = "الهوية الشخصية";
    public static String DATE_AR = "التاريخ";

    //FILER
    public static String DRIVER = "Driver";
    public static String DRIVER_CAPS = "DRIVER";
    public static String CUSTOMER_CAPS = "CUSTOMER";
    public static String MERCHANT_CAPS = "MERCHANT";
    public static String TO_YOU = "TOYOU";

    //TRANSACTION_NAME
    public static String INPUT_VAT = "INPUT_VAT";
    public static String OUTPUT_VAT = "OUTPUT_VAT";
    public static String PRODUCT_RECEIVED = "PRODUCT_RECEIVED";
    public static String PAY_FOR_PRODUCTS = "PAY_FOR_PRODUCTS";
    public static String PAY_FOR_ORDER = "PAY_FOR_ORDER";
    public static String PRODUCT_TRANSFERED = "PRODUCT_TRANSFERED";
    public static String EXPENSES_COMPENSATION = "EXPENSES_COMPENSATION";
    public static String ORDER_REWARD = "ORDER_REWARD";
    public static String COMMISSION = "COMMISSION";

    //other
    public static String EMPTY_VALUE = "";
    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
    public static String RESERVED = "RESERVED";
    public static String NO_RECORD_AVAILABLE = "No records available";
    public static String BALANCE = "BALANCE";
    public static String PRODUCT = "PRODUCT";
    public static String CASH = "CASH";
    public static String ORDER_COMPLETED = "Order completed";
    public static String CARD_TITLE = "Transaction list";
    public static String CARD_TITLE_AR = "قائمة المعاملات";
    public static String EXPORT_TO_EXCEL = "Export to Excel";
    public static String EXPORT_TO_EXCEL_AR = "تصدير إلى Excel";
    public static String ADD_TRANSACTION = "Add transaction";
    public static String ADD_TRANSACTION_AR = "إضافة حركة";
    public static String CREATED_FROM_TO_label = "Created (from ... to)";
    public static String CREATED_FROM_TO_label_AR = "تم الإنشاء (من ... إلى)";
    public static String OPERATION_label = "Operation";
    public static String OPERATION_label_AR = "العملية";
    public static String FILTER = "Filter";
    public static String FILTER_AR = "فلتر";


}
