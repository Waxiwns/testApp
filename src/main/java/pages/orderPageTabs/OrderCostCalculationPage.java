// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class OrderCostCalculationPage {

    public SelenideElement costCalcHeaderText = $(xpath("//h2[contains(text(), \"Cost Calculator\")]"));

    //"Order pre-authorization" table
    public SelenideElement preAuthTableBody = $(xpath("//th[contains(text(), \"Order pre-authorization\")]/parent::tr/parent::tbody"));
    public SelenideElement preAuthTableHeader = $(xpath("//th[contains(text(), \"Order pre-authorization\")]"));
    public SelenideElement debtRow = $(xpath("//td[contains(text(), \"Debt\")]"));
    public SelenideElement debtValueRow = $(xpath("//td[contains(text(), \"Debt\")]/following-sibling::td"));
    public SelenideElement initialAmountRow = $(xpath("//td[contains(text(), \"Initial amount\")]"));
    public SelenideElement initialAmountValueRow = $(xpath("//td[contains(text(), \"Initial amount\")]/following-sibling::td"));
    public SelenideElement reservedFromBalanceRow = $(xpath("//td[contains(text(), \"Reserved from balance\")]"));
    public SelenideElement reservedFromBalanceValueRow = $(xpath("//td[contains(text(), \"Reserved from balance\")]/following-sibling::td"));
    public SelenideElement reservedFromCreditCardRow = $(xpath("//td[contains(text(), \"Reserved from credit card\")]"));
    public SelenideElement reservedFromCreditCardValueRow = $(xpath("//td[contains(text(), \"Reserved from credit card\")]/following-sibling::td"));
    public SelenideElement totalPreauthAmountRow = $(xpath("//b[contains(text(), \"Total pre-authorization amount\")]"));
    public SelenideElement totalPreauthAmountValueRow = $(xpath("//b[contains(text(), \"Total pre-authorization amount\")]/parent::td/following-sibling::td/b"));

    //"Tariff" table
    public SelenideElement tariffHeader = $(xpath("//h4/span[contains(text(), 'Tariff')]"));
    public SelenideElement tariffHeaderLink = $(xpath("//div[contains(@class, 'col-4')]//a"));
    public SelenideElement tariffProfilePageHeader = $(xpath("//am-service-type-tabs//h2[@class = \"card-title\"]"));
    public SelenideElement tariffLinkElement = $(xpath("//am-link-or-text/a"));
    public SelenideElement minimalDeliveryFeeRow = $(xpath("//td[contains(text(), \"Minimal Delivery Fee\")]"));
    public SelenideElement minimalDeliveryFeeValueRow = $(xpath("//td[contains(text(), \"Minimal Delivery Fee\")]/following-sibling::td"));
    public SelenideElement deliveryFixedFeeRow = $(xpath("//td[contains(text(), \"Delivery Fixed Fee\")]"));
    public SelenideElement deliveryFixedFeeValueRow = $(xpath("//td[contains(text(), \"Delivery Fixed Fee\")]/following-sibling::td"));
    public SelenideElement usePreEstimatedDeliveryFeeRow = $(xpath("//td[contains(text(), \"Use pre estimated Delivery Fee\")]"));
    public SelenideElement usePreEstimatedDeliveryFeeValueRow = $(xpath("//td[contains(text(), \"Use pre estimated Delivery Fee\")]/following-sibling::td"));
    public SelenideElement deliveryFeeRatePerKmRow = $(xpath("//td[contains(text(), \"Delivery Rate per km\")]"));
    public SelenideElement deliveryFeeRatePerKmValueRow = $(xpath("//td[contains(text(), \"Delivery Rate per km\")]/following-sibling::td"));
    public SelenideElement deliveryFeeRatePerMinRow = $(xpath("//td[contains(text(), \"Delivery Rate per min\")]"));
    public SelenideElement deliveryFeeRatePerMinValueRow = $(xpath("//td[contains(text(), \"Delivery Rate per min\")]/following-sibling::td"));
    public SelenideElement waitingRatePerMinRow = $(xpath("//td[contains(text(), \"Waiting Rate per min\")]"));
    public SelenideElement waitingRatePerMinValueRow = $(xpath("//td[contains(text(), \"Waiting Rate per min\")]/following-sibling::td"));
    public SelenideElement freeCancellationTimeRow = $(xpath("//td[contains(text(), \"Free cancellation time\")]"));
    public SelenideElement freeCancellationTimeValueRow = $(xpath("//td[contains(text(), \"Free cancellation time\")]/following-sibling::td"));
    public SelenideElement cancellationPenaltyRow = $(xpath("//td[contains(text(), \"Cancellation penalty\")]"));
    public SelenideElement cancellationPenaltyValueRow = $(xpath("//td[contains(text(), \"Cancellation penalty\")]/following-sibling::td"));
    public SelenideElement driverServiceFeeRateRow = $(xpath("//td[contains(text(), \"Driver Service Fee Rate (Percent)\")]"));
    public SelenideElement driverServiceFeeRateValueRow = $(xpath("//td[contains(text(), \"Driver Service Fee Rate (Percent)\")]/following-sibling::td"));
    public SelenideElement usePreAuthPaymentRow = $(xpath("//td[contains(text(), \"Use pre-auth payment\")]"));
    public SelenideElement usePreAuthPaymentValueRow = $(xpath("//td[contains(text(), \"Use pre-auth payment\")]/following-sibling::td"));
    public SelenideElement fixedPreAuthDeliveryFeeRow = $(xpath("//td[contains(text(), \"Fixed delivery fee (pre-auth)\")]"));
    public SelenideElement fixedPreAuthDeliveryFeeValueRow = $(xpath("//td[contains(text(), \"Fixed delivery fee (pre-auth)\")]/following-sibling::td"));
    public SelenideElement fixedPreAuthBasketCostRow = $(xpath("//td[contains(text(), \"Fixed basket cost (pre-auth)\")]"));
    public SelenideElement fixedPreAuthBasketCostValueRow = $(xpath("//td[contains(text(), \"Fixed basket cost (pre-auth)\")]/following-sibling::td"));

    //Order receipt table
    public SelenideElement basketServiceCostLine = $(xpath("//span[contains(text(), \"Basket/Service cost\")]"));
    public SelenideElement basketServiceCostValue = $(xpath("//am-order-receipt-line[1]/td[2]/span"));
    public SelenideElement deliveryFeeLine = $(xpath("//span[contains(text(), \"Delivery Fee\")]"));
    public SelenideElement deliveryFeeValue = $(xpath("//am-order-receipt-line[2]/td[2]/span"));
    public SelenideElement serviceFeeLine = $(xpath("//span[contains(text(), \"Service Fee\")]"));
    public SelenideElement serviceFeeValue = $(xpath("//am-order-receipt-line[3]/td[2]/span"));
    public SelenideElement cancellationFee = $(xpath("//span[contains(text(), \"Cancellation Fee\")]"));
    public SelenideElement cancellationFeeValue = $(xpath("//am-order-receipt-line[4]/td[2]/span"));
    public SelenideElement totalCostLine = $(xpath("//span[contains(text(), \"Total cost\")]"));
    public SelenideElement totalCostValue = $(xpath("//am-order-receipt-line[5]/td[2]/span"));
    public SelenideElement vatLine = $(xpath("//span[contains(text(), \"VAT\")]"));
    public SelenideElement vatValue = $(xpath("//am-order-receipt-line[6]/td[2]/span"));

    //Calculation table

    public SelenideElement deliveryFeeCostInputField = $(xpath("//b[text()='Delivery Fee']/ancestor:: tr//input"));
    public SelenideElement deliveryFeeCostDropdownMenu = $(xpath("//b[text()='Delivery Fee']/ancestor:: tr//button"));
    public SelenideElement saveButton = $(xpath("//button[contains(@class, 'btn btn-sm btn-primary btn-xs-stretching')]"));
    public SelenideElement createTicket = $(".order-id-go button");

    public SelenideElement dropdownMenuFields(String parameter) {
        return $(xpath("//b[text()='Delivery Fee']/ancestor:: tr//button[contains(text(), '" + parameter + "')]"));
    }

    public SelenideElement calculationTableField(String str, String column) {
        return $(xpath("//h4[contains(., 'Calculation')]/following-sibling::table//tr[contains(., '" + str + "')]/td[contains(@class, '" + column + "')]"));
    }

    public static final String DELIVERY_FEE = "Delivery Fee";
    public static final String SERVICE_FEE = "Service Fee";
    public static final String TOTAL_COSTS = "Total costs";
    public static final String SHOWN_AMOUNT = "shown-amount";
    public static final String PROMO_DISCOUNT = "promo-discount";
    public static final String AFTER_CORRECTION = "after-correction";

    //dropdown menu fields
    public static final String VALUE = "Value";
    public static final String DELTA = "Delta";
    public static final String PERSENT = "%";

    //reaction in table
    public static final String SUCCESS = "✔";
    public static final String FAILURE = "❌";

    public static final String NUMBER_0 = "0";
    public static final String NUMBER_200 = "200";
    public static final String NUMBER_5 = "5";
    public static final String NUMBER_69_AND_4 = "69.4";
    public static final String NUMBER_109_AND_4 = "109.4";
    public static final String NUMBER_129_AND_4 = "129.4";
    public static final String NUMBER_169_AND_4 = "169.4";
    public static final String NUMBER_100 = "100";
    public static final String NUMBER_40 = "40";
    public static final String NUMBER_60 = "60";
    public static final String NUMBER_11_AND_77 = "11.77";
    public static final String NUMBER_15_AND_4 = "15.4";
    public static final String penalty = "PENALTY";

    //Beneficiary Type
    public static final String CUSTOMER = "CUSTOMER";

    //Account Type
    public static final String BALANCE = "BALANCE";
}
