// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum ProductOptions {

    PRODUCT_NAME("Cheese Pizza"),
    PRODUCT_DESCRIPTION("Big pizza with cheese"),
    DISCOUNT_PRODUCT_NAME("Discounted Pizza"),
    DISCOUNT_PRODUCT_DESCRIPTION("Discounted Pizza Description"),
    PRODUCT_CATEGORY_NAME("Fast food"),
    PRODUCT_GROUP_NAME("Meat dishes"),
    TEST_NOTE("Test Note"),
    EMPTY_TEST_NOTE(""),
    SAVED_PRODUCT_OPTION("+5 SAR Cheese (Double)"),
    FIVE_SAR("+5 SAR");

    private final String value;

    ProductOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
