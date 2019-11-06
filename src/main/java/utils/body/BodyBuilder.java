// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.body;

import java.util.Map;

import static utils.constants.ApiConstants.*;

class BodyBuilder {

    private StringBuilder result;

    public BodyBuilder() {
        result = new StringBuilder();
    }

    private void addCommaBeforeBlock() {
        if (result.length() > 1) {
            result.append(",");
        }
    }

    public void reset() {
        result.setLength(0);
        result.append("{");
    }

    public String getResult() {
        result.append("}");
        return result.toString();
    }

    public void add(String data) {
        addCommaBeforeBlock();
        result.append(data);
    }

    public void setSurprise() {
        add("\"" + SURPRISE + "\": true");
    }

    public void setMainInformation(String orderKind, String operationArea, String deliveryTime) {
        String deliveryProperty = deliveryTime.equals(EMPTY_VALUE) ? EMPTY_VALUE : "\"" + DELIVERY_TIME + "\":" + deliveryTime + ",";
        String block = "\"" + STATUS + "\":\"NEW\"," +
                "\"" + ORDER_KIND + "\":\"" + orderKind + "\"," +
                "\"" + OPERATION_AREA + "\":\"" + operationArea + "\"," +
                deliveryProperty +
                "\"comment\":null";
        add(block);
    }

    public void setPaymentCash(boolean useClientBalance) {
        String block = "\"" + PAYMENT + "\": {\n" +
                "   \"" + USE_CLIENT_BALANCE + "\": " + useClientBalance + ",\n" +
                "    \"" + KIND + "\": \"CASH\"\n" +
                "  }";

        add(block);
    }

    public void setPaymentType(String type) {
        add("\"" + PAYMENT_TYPE + "\": \"" + type + "\"");
    }

    public void setPaymentCc(Map<String, String> payment) {
        setPaymentCc(Boolean.valueOf(payment.get(USE_CLIENT_BALANCE)), payment.get(LAST_4), payment.get(BRAND), payment.get(CARD_ID));
    }

    public void setPaymentCc(boolean useClientBalance, String last4, String brand, String cardId) {
        String block = "\"" + PAYMENT + "\": {" +
                "\"" + KIND + "\": \"CREDIT_CARD\"," +
                "\"" + USE_CLIENT_BALANCE + "\": " + useClientBalance + ",\n" +
                "\"" + LAST_4 + "\": \"" + last4 + "\"," +
                "\"" + BRAND + "\": \"" + brand + "\"," +
                "\"" + CARD_ID + "\": \"" + cardId + "\"}";

        add(block);
    }

    public void getPaymentBlock(String paymentType, boolean useClientBalance) {
        if (paymentType.equals(CASH))
            setPaymentCash(useClientBalance);
        else
            setPaymentCc(useClientBalance, "1111", "Visa", "6dc7bf12-91a8-4fd7-894a-970dbd315457");
    }

    public void getPaymentBlock(Map<String, String> payment) {
        if (payment == null)
            return;
        if (payment.get(NAME).equals(CASH))
            setPaymentCash(Boolean.valueOf(payment.get(USE_CLIENT_BALANCE)));
        else
            setPaymentCc(payment);
    }

    public void setPromoCode(String promo) {
        if (!promo.isEmpty()) add("\"" + PROMO_CODE + "\": \"" + promo + "\"");
    }

    public void setVehicleType(String vehicle) {
        add("  \"" + VEHICLE_TYPE + "\": \"" + vehicle + "\"\n");
    }

    public void setOrderTotal(String total) {
        add("\"orderTotal\":" + total + "");
    }

    public void setCustomOrderItems(String productName, String productQuantity) {
        String block = "  \"" + CUSTOM_ORDER_ITEMS + "\": [\n" +
                "    {\n" +
                "      \"" + NAME + "\": \"" + productName + "\",\n" +
                "      \"" + QUANTITY + "\": " + productQuantity + ",\n" +
                "      \"" + UIID + "\": \"0.10810662575650531\"\n" +
                "    }\n" +
                "  ]\n";
        add(block);
    }

    public void setCatalogServiceItems() {
        add("\"" + CATALOG_SERVICE_ITEMS + "\": []\n");
    }

    public void setServiceItems(String serviceId) {
        String block = serviceId.isEmpty() ? "\"" + SERVICE_ITEMS + "\": []\n" : "\"" + SERVICE_ITEMS + "\":[{ \"" + SERVICE_ID + "\":\"" + serviceId + "\"}]";
        add(block);
    }

    public void setLanguage(String lang) {
        add("\"" + LANG + "\": \"" + lang + "\"\n");
    }

    public void setCurrency() {
        add("  \"" + CURRENCY + "\": \"SAR\"\n");
    }

    public void setUseClientBalance(boolean isUseBalance) {
        add("  \"" + USE_CLIENT_BALANCE + "\": " + isUseBalance + "\n");
    }

    public void setCatalogOrderItems(String productId, String quantity) {
        String block = "\"" + CATALOG_ORDER_ITEMS + "\":[{" +
                "\"" + NOTE + "\":\"\"," +
                "\"" + PRODUCT_ID + "\":\"" + productId + "\"," +
                "\"" + QUANTITY + "\":" + quantity + "," +
                "\"" + OPTION_VALUE_IDS + "\":[]" +
                "}]";
        if (productId.isEmpty()) block = "\"" + CATALOG_ORDER_ITEMS + "\": []";

        add(block);
    }

    public void setCatalogOrderItems() {
        setCatalogOrderItems(EMPTY_VALUE, EMPTY_VALUE);
    }
}
