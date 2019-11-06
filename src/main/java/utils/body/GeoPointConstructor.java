// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.body;

import java.util.Map;

import static utils.constants.ApiConstants.*;

public class GeoPointConstructor {
    GeoPointBuilder gBuilder;

    public GeoPointConstructor() {
        gBuilder = new GeoPointBuilder();
    }


    public String createBlockOnlyWithPos(String type, String posId) {
        gBuilder.setType(type);
        gBuilder.setPosId(posId);
        return gBuilder.getResult();
    }

    public String createPickUp(Map<String, String> locate, String kindType, String id) {
        gBuilder.setType(PICKUP);
        gBuilder.setUserInformation("null");
        gBuilder.setLocationConfirmation(false);
        gBuilder.setAddress(PICKUP, locate.get(ADDRESS), locate.get(LAT), locate.get(LON));
        gBuilder.setKind(kindType, id);
        return gBuilder.getResult();
    }

    public String createPickUp(Map<String, String> locate, String kindType) {
        return createPickUp(locate, kindType, "");
    }

    public String createDropOff(Map<String, String> locate, String kindType, String userId, String firstName, String lastName, String phone) {
        gBuilder.setType(DROP_OFF);
        gBuilder.setUserInformation(userId, firstName, lastName, phone);
        gBuilder.setLocationConfirmation(false);
        gBuilder.setAddress(DROP_OFF, locate.get(ADDRESS), locate.get(LAT), locate.get(LON));
        gBuilder.setKind(kindType);
        return gBuilder.getResult();
    }

    public String createDropOff(Map<String, String> locate, String kindType) {
        return createDropOff(locate, kindType, "null", "", "", "");
    }

    public String createSurprisePickUpDropOff(String type, String kindType, String userId) {
        gBuilder.setType(type);
        gBuilder.setUserInformation("null");
        gBuilder.setLocationConfirmation(false);
        gBuilder.setKind(kindType, userId);
        return gBuilder.getResult();
    }
}


