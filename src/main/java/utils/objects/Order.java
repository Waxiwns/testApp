// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static core.TestStepLogger.log;
import static io.restassured.path.json.JsonPath.from;
import static utils.constants.ApiConstants.*;

public class Order {

    private String allInfo;

    private String dateFormat = "M/d/YY, h:mm a";

    public Order(String allInfo) {
        this.allInfo = allInfo;
    }

    private String getAllInfo() {
        return allInfo;
    }

    public String getKey(String key) {
        String result = getDataFromJson(getAllInfo(), key);

        if (result == null) {
            log("value: '" + key + "' is absent");
            return key;
        }
        if (key.contains(DATE))
            return convertDate(Long.parseLong(result));
        return result;
    }

    public String getId() {
        return getKey(ID);
    }

    public String getNumber() {
        return getKey(ORDER_NUMBER);
    }

    public String getStatus() {
        return getKey(STATUS);
    }

    public String getCreationDate() {
        return getKey(CREATION_DATE);
    }

    public String getDeliveryDate() {
        return getKey(DELIVERY_DATE);
    }

    public String getFinishedDate() {
        return getKey(FINISHED_DATE);
    }

    public Long getDeliveryDateLong() {
        return Long.parseLong(getDataFromJson(getAllInfo(), DELIVERY_DATE));
    }

    public String getServiceGroup() {
        return getKey(SERVICE_TYPE + "." + SERVICE_GROUP);
    }

    public String getOriginalCost() {
        return getKey(COSTS + "." + DELIVERY_FEE + "." + DISCOUNT + "." + ORIGINAL_COST);
    }

    public String getDeliveryFee() {
        return getKey(COSTS + "." + DELIVERY_FEE + "." + VALUE);
    }

    public String getDeliveryFeePromo() {
        return getKey(COSTS + "." + DELIVERY_FEE + "." + DISCOUNT + "." + AMOUNT);
    }

    public String getServiceFeePromoValue() {
        return getKey(COSTS + "." + SERVICE_FEE + "." + DISCOUNT + "." + VALUE);
    }

    public String getServiceFee() {
        return getKey(TOTAL_COST + "." + SERVICE_FEE_CAPS);
    }

    public String getDeliveryCost() {
        return getKey(TOTAL_COST + "." + DELIVERY_COST);
    }

    public String getTotalCost() {
        return getKey(CHARGES + "." + TOTAL_INC_INCLUDING_VAT);
    }

    public String getVAT() {
        return getKey(CHARGES + "." + VAT);
    }

    public Map<String, String> getAddress(String geoPoint) {
        return new HashMap<String, String>() {{
            put(ADDRESS, getKey(geoPoint + "." + ADDRESS + "." + FORMATTED_ADDRESS));
            put(LAT, getKey(geoPoint + "." + ADDRESS + "." + GEO_POINT + "." + LAT));
            put(LON, getKey(geoPoint + "." + ADDRESS + "." + GEO_POINT + "." + LON));
        }};
    }

    public String getDate(String key, String format) {
        String value = getDataFromJson(getAllInfo(), key);

        return convertDate(Long.parseLong(value), format);
    }

    private String convertDate(Long time, String format) {
        return new SimpleDateFormat(format, java.util.Locale.ENGLISH).format(new Date(time * 1000));
    }

    private String convertDate(Long time) {
        return convertDate(time, dateFormat);
    }

    private String getDataFromJson(String jsonBody, String key) {
        return from(jsonBody).getString(key);
    }
}
