// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.users;

import utils.constants.Statuses;

import java.util.Map;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.log;
import static junit.framework.TestCase.fail;
import static utils.constants.ApiConstants.*;
import static utils.constants.Statuses.*;

public class Driver extends BaseUser {

    public Driver(String id, String email, String password) {
        super(id, email, password);
    }

    // first, second
    public Driver(String name) {
        super();

        if (name.equals("first")) {
            id = testInitValues.firstDriverId();
            email = testInitValues.firstDriverEmail();
            password = testInitValues.password();
        } else {
            id = testInitValues.secondDriverId();
            email = testInitValues.secondDriverEmail();
            password = testInitValues.password();
        }
    }

    @Override
    protected String getAllInfo() {
        if (allInfo == null) return allInfo = useAPI.driverById(id, "");
        return allInfo;
    }

    @Override
    public String getPhone() {
        return getKey(PHONE);
    }

    protected String getAuthToken() {
        // if AUTH_TOKEN is null or expiration period was end - get new token
        if (authToken.get(AUTH_TOKEN) == null || Integer.parseInt(authToken.get(AUTH_EXPIRATION_DATE)) < currentTime()) {

            String generateToken = useAPI.generateTokenDriverUserAllResult(email, password);

            authToken.put(AUTH_TOKEN, getDataFromJson(generateToken, AUTH_TOKEN));
            authToken.put(AUTH_EXPIRATION_DATE, getDataFromJson(generateToken, AUTH_EXPIRATION_DATE));
        }
        return authToken.get(AUTH_TOKEN);
    }

    public String getCarBrand() {
        return getKey(CAR_BRAND);
    }

    public String getCarType() {
        return getKey(CAR_TYPE);
    }

    public String getCar() {
        return getCarBrand() + " " + getCarType();
    }

    public String getCity() {
        return getKey(ADDRESS + "." + CITY);
    }

    public String checkActiveOrder() {
        return useAPI.getActiveOrderDetailsForDriver(getAuthToken());
    }

    public void checkActiveOrderAndCancelIt() {
        String orderId = checkActiveOrder();

        if (orderId.equals("Order not found"))
            log("Driver is not executing any order right now. So, there is no need to cancel any order");
        else {
            useAPI.forwardOrderToSupport(orderId);
            useAPI.cancelOrderById(orderId);
            log("Order was cancelled");
        }
    }

    public void updateActiveOrderStatus(String status) {
        useAPI.updateActiveOrderStatusForDriver(getAuthToken(), currentTime(), status);
    }

    public void updateLocation(Map<String, String> location) {
        useAPI.updateDriverLocation(getAuthToken(), location.get(LAT), location.get(LON));
    }

    public void makeOnline(Map<String, String> location) {
        checkActiveOrderAndCancelIt();
        updateLocation(location);
    }

    public void acceptOrder() {
        int seconds = 60;
        long waitTime = currentTime() + seconds;
        String orderId;

        while (currentTime() < waitTime) {
            useAPI.driverAcceptOrder(getAuthToken());

            orderId = checkActiveOrder();

            if (orderId.equals("Order not found")) {
                log("Accept order: Driver is not executing any order right now. Trying again...");
                sleep(3000);
            } else {
                log("Order #" + orderId + " was accepted by " + getFirstName() + " driver");
                sleep(3000);
                return;
            }
        }

        fail("Accept order: Driver is not executing any order for " + seconds + " seconds");
    }

    public void completeOrder() {
        useAPI.completeOrderAsDriver(getAuthToken());
    }

    public String getStatus() {
        String status = getDataFromJson(useAPI.getDriverLocation(getAuthToken()), STATUS);

        switch (status) {
            case "0":
                return ANY.getEnValue();
            case "1":
                return READY.getEnValue();
            case "2":
                return ORDER_EXECUTION.getEnValue();
            case "3":
                return OFFLINE.getEnValue();
            case "4":
                return ORDER_OFFLINE.getEnValue();
            default:
                return status;
        }
    }

    public void waitForStatus(Statuses status) {
        long waitTime = currentTime() + 60;

        while (currentTime() < waitTime) {
            String driverStatus = getStatus();

            if (driverStatus.equals(ANY.getValue().split(": ")[1]) || driverStatus.equals(status.getValue().split(": ")[1]))
                break;
            sleep(5000);
        }
    }
}