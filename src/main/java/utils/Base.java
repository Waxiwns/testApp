// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils;

import core.TestInitValues;
import core.TestProperties;
import org.aeonbits.owner.ConfigFactory;
import utils.objects.Order;
import utils.users.Admin;
import utils.users.Customer;
import utils.users.Driver;
import utils.users.Merchant;

import java.util.HashMap;
import java.util.Map;

import static utils.constants.ApiConstants.*;

public class Base {
    public static final TestProperties testProperties = ConfigFactory.create(TestProperties.class);
    public static final TestInitValues testInitValues = ConfigFactory.create(TestInitValues.class);

    //    for use in all tests
    protected Order order;
    protected String orderId;
    protected String orderNumber;
    protected Order orderSecond;
    protected String orderIdSecond;
    protected String orderNumberSecond;

    public Admin admin = new Admin();
    public Merchant merchant = new Merchant();
    public Driver secondDriver = new Driver(testInitValues.secondDriverId(), testInitValues.secondDriverEmail(), testInitValues.password());
    public Driver firstDriver = new Driver(testInitValues.firstDriverId(), testInitValues.firstDriverEmail(), testInitValues.password());
    public Driver incorrectDriver = new Driver(testInitValues.incorrectDriverId(), testInitValues.incorrectDriverEmail(), testInitValues.password());
    public Customer firstCustomer = new Customer("first");
    public Customer secondCustomer = new Customer("second");
    public Customer thirdCustomer = new Customer("third");

    public String productName = "AnyProductName";
    public String productQuantity1 = "1";

    public Map<String, String> locationKharkivShevchenka146 = new HashMap<String, String>() {{
        put(ADDRESS, testInitValues.addressKharkivShevchenka146());
        put(LAT, testInitValues.latKharkivShevchenka146());
        put(LON, testInitValues.lonKharkivShevchenka146());
    }};

    public Map<String, String> locationKharkivShevchenka334 = new HashMap<String, String>() {{
        put(ADDRESS, testInitValues.addressKharkivShevchenka334());
        put(LAT, testInitValues.latKharkivShevchenka334());
        put(LON, testInitValues.lonKharkivShevchenka334());
    }};

    public Map<String, String> locationKharkivPushkinska1 = new HashMap<String, String>() {{
        put(ADDRESS, testInitValues.addressKharkivPushkinska1());
        put(LAT, testInitValues.latKharkivPushkinska1());
        put(LON, testInitValues.lonKharkivPushkinska1());
    }};

    public Map<String, String> locationKharkivPushkinska10 = new HashMap<String, String>() {{
        put(ADDRESS, testInitValues.addressKharkivPushkinska10());
        put(LAT, testInitValues.latKharkivPushkinska10());
        put(LON, testInitValues.lonKharkivPushkinska10());
    }};

    public Map<String, String> locationKharkivPushkinska100 = new HashMap<String, String>() {{
        put(ADDRESS, testInitValues.addressKharkivPushkinska100());
        put(LAT, testInitValues.latKharkivPushkinska100());
        put(LON, testInitValues.lonKharkivPushkinska100());
    }};

    public Map<String, String> locationKharkivPushkinskaProvulok4 = new HashMap<String, String>() {{
        put(LAT, testInitValues.latKharkivPushkinskaProvulok4());
        put(LON, testInitValues.lonKharkivPushkinskaProvulok4());
        put(ADDRESS, testInitValues.addressKharkivPushkinskaProvulok4());
    }};

    public Map<String, String> customerProduct = new HashMap<String, String>() {{
        put(NAME, "Test");
        put(QUANTITY, productQuantity1);
    }};

    public Map<String, String> customerProduct1 = new HashMap<String, String>() {{
        put(NAME, productName);
        put(QUANTITY, productQuantity1);
    }};

    public Map<String, String> productForSurprise = new HashMap<String, String>() {{
        put(PRODUCT_ID, testInitValues.firstMerchantCheesePizzaId());
        put(QUANTITY, productQuantity1);
    }};

    public Map<String, String> deliveryKharkivTariff = new HashMap<String, String>() {{
        put(CANCELLATION_PENALTY, "10");
        put(DELIVERY_FIXED_FEE, "5");
        put(DELIVERY_RATE_PER_MIN, "0");
        put(DRIVER_SERVICE_FEE_PERCENT, "0");
        put(DELIVERY_RATE_PER_KM, "1");
        put(FREE_CANCELLATION_TIME_SEC, "60");
        put(FREE_DELIVERY_TIME_SEC, "120");
        put(MINIMAL_DELIVERY_FEE, "10");
    }};

    public Map<String, String> deliveryBookingKharkivTariff = new HashMap<String, String>() {{
        put(CANCELLATION_PENALTY, "10");
        put(DELIVERY_FIXED_FEE, "5");
        put(DELIVERY_RATE_PER_MIN, "0");
        put(DRIVER_SERVICE_FEE_PERCENT, "0");
        put(DELIVERY_RATE_PER_KM, "1");
        put(FREE_CANCELLATION_TIME_SEC, "60");
        put(FREE_DELIVERY_TIME_SEC, "120");
        put(MINIMAL_DELIVERY_FEE, "10");
    }};

    public Map<String, String> courierKharkivTariff = new HashMap<String, String>() {{
        put(CANCELLATION_PENALTY, "10");
        put(DELIVERY_FIXED_FEE, "0");
        put(DELIVERY_RATE_PER_MIN, "5");
        put(DRIVER_SERVICE_FEE_PERCENT, "10");
        put(DELIVERY_RATE_PER_KM, "1");
        put(FREE_CANCELLATION_TIME_SEC, "60");
        put(FREE_DELIVERY_TIME_SEC, "120");
        put(MINIMAL_DELIVERY_FEE, "12");
    }};

    public Map<String, String> taxiKharkivTariff = new HashMap<String, String>() {{
        put(CANCELLATION_PENALTY, "10");
        put(DELIVERY_FIXED_FEE, "10");
        put(DELIVERY_RATE_PER_MIN, "0");
        put(DRIVER_SERVICE_FEE_PERCENT, "0");
        put(DELIVERY_RATE_PER_KM, "1");
        put(FREE_CANCELLATION_TIME_SEC, "60");
        put(FREE_DELIVERY_TIME_SEC, "120");
        put(MINIMAL_DELIVERY_FEE, "");
    }};

}
