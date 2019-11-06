// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.body;

import static utils.constants.ApiConstants.*;

class GeoPointBuilder {
    private StringBuilder result;


    public GeoPointBuilder() {
        result = new StringBuilder();
    }

    private void addCommaBeforeBlock() {
        if (result.length() > 1 && result.lastIndexOf("{") != result.length() - 1) {
            result.append(",");
        }
    }

    public void setType(String value) {
        result.setLength(0);
        result.append("\"" + value + "\":{");
    }

    public String getResult() {
        result.append("}");
        return result.toString();
    }

    private void add(String data) {
        addCommaBeforeBlock();
        if (data.equals("}")) result.setLength(result.length() - 1);
        result.append(data);
    }

    public void setMerchantPos(String posId) {
        String block = "\"" + MERCHANT_POS + "\":{" +
                "\"" + POS_ID + "\":\"" + posId + "\"" +
                "}";
        add(block);
    }

    private void setPlaceName(String apiConstants) {
        if (apiConstants.equals(PICKUP)) {
            add("\"" + PLACE_NAME + "\":\"1\"");
        }
    }

    private void setUserAddressDetails(String apiConstants) {
        if (apiConstants.equals(DROP_OFF)) {
            add("\"" + USER_ADDRESS_DETAILS + "\":null");
        }
    }

    public void setAddressDetails(String address) {
        String block = "\"" + FORMATTED_ADDRESS + "\":\"" + address + "\"," +
                "\"" + ADDRESS_LINE_1 + "\":\"" + address + "\"," +
                "\"" + ADDRESS_LINE_2 + "\":null," +
                "\"" + AREA + "\":\"\"," +
                "\"" + CITY + "\":\"Харків\"," +
                "\"" + STATE + "\": \"Харківська область\",\n" +
                "\"" + COUNTRY + "\": \"Украина\",\n" +
                "\"" + ZIP + "\":\"61000\"";
        add(block);
    }

    public void setGeoPoint(String lat, String lon) {
        add("   \"" + GEO_POINT + "\":{ \"" + LAT + "\":" + lat + ", \"" + LON + "\":" + lon + "}\n");
    }

    public void setAddress(String type, String address, String Lat, String lon) {
        add("\"" + ADDRESS + "\":{");
        setPlaceName(type);
        setUserAddressDetails(type);
        setAddressDetails(address);
        if (!Lat.isEmpty() && !lon.isEmpty()) setGeoPoint(Lat, lon);
        add("}");
    }

    public void setAddress(String type, String address) {
        setAddress(type, address, "", "");
    }

    public void setKind(String kindType, String id) {
        if (!kindType.isEmpty()) {
            add("\"" + KIND + "\":\"" + kindType + "\"");
            switch (kindType) {
                case ME_KIND:
                    break;
                case MERCHANT_ID_KIND:
                    setMerchant(id);
                    break;
                case POS_ID_KIND:
                    setMerchantPos(id);
                    break;
                case USER_ID_KIND:
                    setUser(id);
                    break;
            }
        }
    }

    public void setKind(String kindType) {
        setKind(kindType, "");
    }

    public void setPosId(String id) {
        add("\"" + POS_ID + "\":\"" + id + "\"");
    }

    public void setUserInformation(String userId, String firstName, String lastName, String phone) {
        String block = "\"" + USER_ID + "\": " + userId + "\n";
        if (!firstName.isEmpty() || !lastName.isEmpty() || !phone.isEmpty()) {
            block += ",\"" + LAST_NAME + "\":\"" + lastName + "\"," +
                    "\"" + FIRST_NAME + "\":\"" + firstName + "\"," +
                    "\"" + PHONE_NUMBER + "\":\"" + phone + "\"";
        }
        add(block);
    }

    public void setUserInformation(String userId) {
        setUserInformation(userId, "", "", "");
    }

    public void setLocationConfirmation(boolean flag) {
        add("\"locationConfirmationRequired\": " + flag + "\n");
    }

    public void setMerchant(String merchantId) {
        add("\"" + MERCHANT + "\": {\n\"" + MERCHANT_ID + "\": \"" + merchantId + "\"\n }\n");
    }

    public void setUser(String userId) {
        add("\"" + USER + "\": {\n\"" + ID + "\": \"" + userId + "\"\n }\n");
    }
}
