// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum OrderPrices {

    ZERO("0"),
    REGULAR_FULL_PRODUCT_PRICE("50.00"),
    REGULAR_DISCOUNTED_PRODUCT_PRICE("40.00"),
    DISCOUNTED_PIZZA_PRICE("60.00"),
    ORDER_TOTAL_UNCHANGED_PRICE("40.00 SAR"),
    ORDER_TOTAL_CHANGED_PRICE("240.00"),
    PRODUCT_QUANTITY("6");

    private final String value;

    OrderPrices(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
