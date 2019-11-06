// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.body;

import utils.users.Customer;
import utils.users.Merchant;

import java.util.HashMap;
import java.util.Map;

import static utils.BaseTest.testInitValues;
import static utils.constants.ApiConstants.*;
import static utils.constants.Locale.EN;

public class BodyConstructor {
    BodyBuilder bBuilder;
    GeoPointConstructor gConstruct;

    private String operationArea;

    public BodyConstructor(String operationArea) {
        this.operationArea = operationArea;
        bBuilder = new BodyBuilder();
        gConstruct = new GeoPointConstructor();
    }

    public String getRegularOrder(String deliveryTime, Map<String, String> merchantValues, String quantity, Map<String, String> dropOff, String promoCode, String payment) {
        bBuilder.reset();
        bBuilder.setMainInformation(testInitValues.orderKindM2MEReg(), operationArea, deliveryTime);
        bBuilder.getPaymentBlock(payment, false);
        bBuilder.setCatalogOrderItems(merchantValues.get(PRODUCT_ID), quantity);
        bBuilder.add(gConstruct.createPickUp(merchantValues, POS_ID_KIND, merchantValues.get(POS_ID)));
        bBuilder.add(gConstruct.createDropOff(dropOff, ME_KIND));
        bBuilder.setPromoCode(promoCode);
        return bBuilder.getResult();
    }

    public String getCourierOrder(Map<String, String> pickUp, Map<String, String> dropOff, String deliveryTime, Map<String, String> payment, Map<String, String> product, String promo) {
        bBuilder.reset();
        bBuilder.setMainInformation(testInitValues.orderKindCourierUser(), operationArea, deliveryTime);
        bBuilder.getPaymentBlock(payment);
        bBuilder.setCustomOrderItems(product.get(NAME), product.get(QUANTITY));
        bBuilder.setVehicleType(CAR);
        bBuilder.add(gConstruct.createPickUp(pickUp, ME_KIND));
        bBuilder.add(gConstruct.createDropOff(dropOff, ME_KIND));
        bBuilder.setPromoCode(promo);
        return bBuilder.getResult();
    }

    public String getApproximateCourierOrderCostsBody(Map<String, String> product, Map<String, String> pickUpLocation, Map<String, String> dropOffLocation, String promoCode, Map<String, String> payment, String deliveryTime) {
        bBuilder.reset();
        bBuilder.setMainInformation(testInitValues.orderKindCourierUser(), operationArea, deliveryTime);
        bBuilder.setCurrency();
        bBuilder.setPromoCode(promoCode);
        bBuilder.setCatalogOrderItems();
        bBuilder.setServiceItems(EMPTY_VALUE);
        bBuilder.setLanguage(EN);
        bBuilder.add(gConstruct.createPickUp(pickUpLocation, ME_KIND));
        bBuilder.add(gConstruct.createDropOff(dropOffLocation, ME_KIND));
        bBuilder.setCustomOrderItems(product.get(NAME), product.get(QUANTITY));
        bBuilder.getPaymentBlock(payment);
        bBuilder.setCatalogServiceItems();
        bBuilder.setUseClientBalance(true);
        bBuilder.setVehicleType(CAR);
        return bBuilder.getResult();
    }

    public String getTaxiBodyHomeless(Map<String, String> pickUp, String deliveryTime, String serviceId, String promo, Map<String, String> payment) {
        return getTaxiBody(pickUp, null, deliveryTime, serviceId, promo, false, payment);
    }

    public String getTaxiBodyHome(Map<String, String> pickUp, Map<String, String> dropOff, String deliveryTime, String serviceId, String promo, Map<String, String> payment) {
        return getTaxiBody(pickUp, dropOff, deliveryTime, serviceId, promo, true, payment);
    }

    private String getTaxiBody(Map<String, String> pickUp, Map<String, String> dropOff, String deliveryTime, String serviceId, String promo, boolean isNotHomeless, Map<String, String> payment) {
        bBuilder.reset();
        bBuilder.setMainInformation(testInitValues.orderKindServiceTaxi(), operationArea, deliveryTime);
        bBuilder.getPaymentBlock(payment);
        bBuilder.add(gConstruct.createPickUp(pickUp, ME_KIND));
        if (isNotHomeless) {
            bBuilder.add(gConstruct.createDropOff(dropOff, ME_KIND));
        }
        bBuilder.setServiceItems(serviceId);
        bBuilder.setPromoCode(promo);
        return bBuilder.getResult();
    }

    public String getSurpriseBody(Map<String, String> product, String orderKind, String merchantId, String userId) {
        bBuilder.reset();
        bBuilder.setMainInformation(orderKind, operationArea, EMPTY_VALUE);
        bBuilder.setSurprise();
        bBuilder.getPaymentBlock(CARD, false);
        bBuilder.setCatalogOrderItems(product.get(PRODUCT_ID), product.get(QUANTITY));
        bBuilder.add(gConstruct.createSurprisePickUpDropOff(PICKUP, MERCHANT_ID_KIND, merchantId));
        bBuilder.add(gConstruct.createSurprisePickUpDropOff(DROP_OFF, USER_ID_KIND, userId));
        return bBuilder.getResult();
    }

    public String getMerchantBody(Map<String, String> dropOff, Customer customer, String orderTotal, String posId) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put(FIRST_NAME, customer.getFirstName());
        userInfo.put(LAST_NAME, customer.getLastName());
        userInfo.put(PHONE, customer.getPhone());

        bBuilder.reset();
        bBuilder.setPaymentType(CASH_caps);
        bBuilder.setVehicleType(CAR);
        bBuilder.add(gConstruct.createBlockOnlyWithPos(PICKUP, posId));
        bBuilder.add(gConstruct.createDropOff(dropOff, "", "null", userInfo.get(FIRST_NAME), userInfo.get(LAST_NAME), userInfo.get(PHONE)));
        bBuilder.setOrderTotal(orderTotal);
        return bBuilder.getResult();
    }

    public String updateLocationBody(String AddressLine, String locationLat, String locationLon) {
        return "{\n" +
                "  \"" + ADDRESS_LINE_1 + "\": \"" + AddressLine + "\",\n" +
                "  \"" + ADDRESS_LINE_2 + "\": \"\",\n" +
                "  \"" + AREA + "\": \"" + testInitValues.areaKharkiv() + "\",\n" +
                "  \"" + CITY + "\": \"\",\n" +
                "  \"" + COUNTRY + "\": \"\",\n" +
                "  \"" + FORMATTED_ADDRESS + "\": \"" + AddressLine + "\",\n" +
                "  \"" + GEO_POINT + "\": {\n" +
                "    \"" + LAT + "\": " + locationLat + ",\n" +
                "    \"" + LON + "\": " + locationLon + "\n" +
                "  },\n" +
                "  \"" + NOTE + "\": \"\",\n" +
                "  \"" + PLACE_NAME + "\": \"\",\n" +
                "  \"" + STATE + "\": \"\",\n" +
                "  \"" + USER_ADDRESS_DETAILS + "\": \"\",\n" +
                "  \"" + ZIP + "\": \"\"\n" +
                "}";
    }


    // correctionType - ABSOLUTE, DELTA_PERCENT; costCorrectionsName - deliveryFee, fixedFee, waitingFee, deliveryDurationFee, deliveryDistanceFee
    public String updateCostCorrectionsForOrder(String costCorrectionsName, int value, String correctionType) {
        return "{\n" + updateCostCorrectionsBlock(costCorrectionsName, value, correctionType) + "}";
    }

    public String updateCostCorrectionsBlock(String costCorrectionsName, int value, String correctionType) {
        return "  \"" + costCorrectionsName + "\": {\n" +
                "    \"correctionType\": \"" + correctionType + "\",\n" +
                "    \"value\": " + value + "\n" +
                "  }\n";
    }


    public String createManualTransactionBody(String balanceOpType, String amountValue, String beneficiaryId, String orderId, String txDescription) {

        String changeBalanceBody = "{\"" + CURRENCY + "\": \"SAR\"," +
                "\"beneficiaryType\": \"CUSTOMER\"," +
                "\"beneficiaryId\": \"" + beneficiaryId + "\"," +
                "\"" + AMOUNT + "\":\"" + amountValue + "\"," +
                "\"balanceOperationType\":\"" + balanceOpType + "\"," +
                "\"" + DESCRIPTION + "\": \"" + (txDescription.equals("") ? "Transaction description" : txDescription) + "\"," +
                "\"orderId\": \"" + orderId + "\"}";
        return changeBalanceBody;
    }

    public String addProductToCartBody(Merchant merchant, String firstProductId, String secondProductId, String quantity) {
        String body = "{" +
                "\"orderLines\": [" +
                "{" +
                "\"" + NAME + "\": \"" + merchant.getProductInfoEn(firstProductId, NAME) + "\"," +
                "\"" + QUANTITY + "\": " + quantity + "," +
                "\"" + PRICE + "\":" + merchant.getProductInfoEn(firstProductId, PRICE) + "," +
                "\"" + NOTE + "\": \"\"," +
                "\"" + PRODUCT_ID + "\": \"" + merchant.getProductInfoEn(firstProductId, ID) + "\"" +
                "}," +
                "{" +
                "\"" + NAME + "\": \"" + merchant.getProductInfoEn(secondProductId, NAME) + "\"," +
                "\"" + QUANTITY + "\": " + quantity + "," +
                "\"" + PRICE + "\":" + merchant.getProductInfoEn(secondProductId, PRICE) + "," +
                "\"" + NOTE + "\": \"\"," +
                "\"" + PRODUCT_ID + "\": \"" + merchant.getProductInfoEn(secondProductId, ID) + "\"" +
                "}" +
                "]}";

        return body;
    }

    public String getAddressBlockToAcceptOrderByFriend(Map<String, Map> jsonResult) {
        return "{\"" + ADDRESS + "\": {\n" +
                "\"" + FORMATTED_ADDRESS + "\": \"" + jsonResult.get(FORMATTED_ADDRESS) + "\",\n" +
                "\"" + ADDRESS_LINE_1 + "\": \"" + jsonResult.get(ADDRESS_LINE_1) + "\",\n" +
                "\"" + ADDRESS_LINE_2 + "\": \"" + jsonResult.get(ADDRESS_LINE_2) + "\",\n" +
                "\"" + AREA + "\": \"" + jsonResult.get(AREA) + "\",\n" +
                "\"" + CITY + "\": \"" + jsonResult.get(CITY) + "\",\n" +
                "\"" + STATE + "\": \"" + jsonResult.get(STATE) + "\",\n" +
                "\"" + COUNTRY + "\": \"" + jsonResult.get(COUNTRY) + "\",\n" +
                "\"" + ZIP + "\": \"" + jsonResult.get(ZIP) + "\",\n" +
                "\"" + GEO_POINT + "\": {\n" +
                "  \"" + LAT + "\": " + jsonResult.get(GEO_POINT).get(LAT) + ",\n" +
                "  \"" + LON + "\": " + jsonResult.get(GEO_POINT).get(LON) + "\n" +
                "},\n" +
                "\"" + USER_ADDRESS_DETAILS + "\": \"" + jsonResult.get(USER_ADDRESS_DETAILS) + "\"\n" +
                "}";
    }
}

