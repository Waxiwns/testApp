// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.users;

import utils.objects.Order;

import static utils.constants.ApiConstants.*;
import static utils.constants.Locale.AR;
import static utils.constants.Locale.EN;

public class Merchant extends BaseUser {

    public Merchant(String id, String email, String password) {
        super(id, email, password);
    }

    public Merchant() {
        super(testInitValues.firstMerchantId(), testInitValues.firstMerchantEmail(), testInitValues.password());
    }

    @Override
    protected String getAllInfo() {
        if (allInfo == null) return allInfo = useAPI.merchantById(id, "");
        return allInfo;
    }

    protected String getAuthToken() {
        // if AUTH_TOKEN is null or expiration period was end - get new token
        if (authToken.get(AUTH_TOKEN) == null || Integer.parseInt(authToken.get(AUTH_EXPIRATION_DATE)) < currentTime()) {
            String generateToken = useAPI.generateTokenMerchantUserAllResult(email, password);

            authToken.put(AUTH_TOKEN, getDataFromJson(generateToken, AUTH_TOKEN));
            authToken.put(AUTH_EXPIRATION_DATE, getDataFromJson(generateToken, AUTH_EXPIRATION_DATE));
        }
        return authToken.get(AUTH_TOKEN);
    }

    public String getMerchantUserInfo(String merchantUserId, String key) {
        key = "merchantUser." + key;
        return getDataFromJson(useAPI.getMerchantUsers(merchantUserId), key);
    }

    public String getName() {
        return getKey(NAME);
    }

    private String getProductInfo(String local, String productId, String key) {
        String prodAllInfo = useAPI.getProductByIdForMerchant(local, productId);
        key = "product." + key;
        return getDataFromJson(prodAllInfo, key);
    }

    public String getProductInfoEn(String productId, String key) {
        return getProductInfo(EN, productId, key);
    }

    public String getProductInfoAr(String productId, String key) {
        return getProductInfo(AR, productId, key);
    }

    public void acceptOrderById(String orderId) {
        useAPI.acceptOrderByMerchant(getAuthToken(), orderId);
    }

    public Order createMerchantOrder(String body) {
        return new Order(useAPI.createMerchantOrder(getAuthToken(), body));
    }

    public void cancelOrderById(String orderId) {
        useAPI.cancelOrderByMerchant(getAuthToken(), orderId);
    }
}
