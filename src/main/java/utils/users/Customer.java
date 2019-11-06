// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.users;

import utils.objects.Order;

import static core.TestStepLogger.log;
import static utils.constants.ApiConstants.AUTH_EXPIRATION_DATE;
import static utils.constants.ApiConstants.AUTH_TOKEN;

public class Customer extends BaseUser {

    public Customer(String id, String email, String password) {
        super(id, email, password);
    }

    public Customer(String name) {
        super();

        if (name.equals("first")) {
            id = testInitValues.firstCustomerId();
            email = testInitValues.firstCustomerEmail();
            password = testInitValues.password();
        } else if (name.equals("second")) {
            id = testInitValues.secondCustomerId();
            email = testInitValues.secondCustomerEmail();
            password = testInitValues.password();
        } else {
            id = testInitValues.thirdCustomerId();
            email = testInitValues.thirdCustomerEmail();
            password = testInitValues.password();
        }
    }

    @Override
    protected String getAllInfo() {
        if (allInfo == null) return allInfo = useAPI.customerById(id, "");
        return allInfo;
    }

    protected String getAuthToken() {
        // if AUTH_TOKEN is null or expiration period was end - get new token
        if (authToken.get(AUTH_TOKEN) == null || Integer.parseInt(authToken.get(AUTH_EXPIRATION_DATE)) < currentTime()) {
            String generateToken = useAPI.generateTokenUserAllResult(email, password);

            authToken.put(AUTH_TOKEN, getDataFromJson(generateToken, AUTH_TOKEN));
            authToken.put(AUTH_EXPIRATION_DATE, getDataFromJson(generateToken, AUTH_EXPIRATION_DATE));
        }
        return authToken.get(AUTH_TOKEN);
    }

    public void acceptOrder(String orderId, String body) {
        useAPI.acceptOrderByFriend(orderId, body, getAuthToken());
    }

    public Order createOrder(String body) {
        Order order = new Order(useAPI.createOrderByCustomer(getAuthToken(), body));
        log("Customer order was created with order number: #" + order.getNumber());

        return order;
    }

    //    DEPRECATED: Create order
    public Order createFacadeOrder(String body) {
        Order order = new Order(useAPI.createFacadeOrder(getAuthToken(), body));
        log("Customer order was created with order number: #" + order.getNumber());

        return order;
    }

    public void approximateOrderCosts(String body) {
        useAPI.approximateOrderCosts(getAuthToken(), body);
    }

    public void cancelOrderById(String orderId) {
        useAPI.cancelCustomersOrderById(getAuthToken(), orderId);
    }
}
