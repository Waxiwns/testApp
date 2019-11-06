// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import io.qameta.allure.Step;
import utils.BaseStep;
import utils.UseAPI;
import utils.body.BodyConstructor;
import utils.constants.TransactionConstants;
import utils.objects.Order;
import utils.objects.Transactions;
import utils.users.Customer;
import utils.users.Driver;
import utils.users.Merchant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.Assert.assertEquals;
import static utils.constants.ApiConstants.*;
import static utils.constants.Statuses.*;

public class UseAPISteps extends BaseStep {
    private BodyConstructor bConstructor = new BodyConstructor(testInitValues.areaKharkiv());

    public long oneDaySeconds = 24 * 60 * 60;
    public long twentyOneMinutesSeconds = 21 * 60;
    private int suspendTime = 3600;
    private String number200 = "200";
    private double negative500 = -500;

    public Map<String, String> createPaymentMap(String paymentType, boolean useClientBalance, String cardId) {
        Map<String, String> paymentCard = new HashMap<>();

        paymentCard.put(NAME, paymentType);
        paymentCard.put(USE_CLIENT_BALANCE, String.valueOf(useClientBalance));
        if (paymentType.equals(CC_caps)) {
            paymentCard.put(LAST_4, "1111");
            paymentCard.put(BRAND, "Visa");
            paymentCard.put(CARD_ID, cardId);
        }
        return paymentCard;
    }

    public Map<String, String> createPaymentCashMap(boolean useClientBalance) {
        return createPaymentMap(CASH, useClientBalance, EMPTY_VALUE);
    }

    public Map<String, String> createMerchantMap(Merchant merchant, Map<String, String> location, String posId, String productId) {
        return new HashMap<String, String>() {{
            put(ID, merchant.getId());//merchant id
            put(EMAIL, testInitValues.firstMerchantEmail());//merchant user email
            put(NAME, merchant.getName());//merchant name.
            put(POS_ID, posId);//Point of Sale Id
            put(ADDRESS, location.get(ADDRESS));// Point of Salse ADDRESS
            put(LAT, location.get(LAT));// Point of Salse LAT
            put(LON, location.get(LON));// Point of Salse LON
            put(PRODUCT_ID, productId);// Merchant product Id
        }};
    }

    @Step
    public Map<String, Float> createPreAuthMap(String orderId) {
        String result = admin.getPreAuthData(orderId);
        return from(result).getMap(EMPTY_VALUE);
    }

    @Step
    public void acceptOrderBySecondCustomer(String orderId) {
        String jsonUserLocation = admin.getUserLocation(secondCustomer.getId()).replace("[", "").replace("]", "");
        Map<String, Map> addressesMap = from(jsonUserLocation).getMap(USER_LOCATION + "." + ADDRESS);

        String body = bConstructor.getAddressBlockToAcceptOrderByFriend(addressesMap) + ",\"userId\":\"" + secondCustomer.getId() + "\",\"deliveryDate\":\"" + currentTime() + "\"}";

        secondCustomer.acceptOrder(orderId, body);
    }

    @Step
    public void updateServiceTypeCustomerTariffsDeliveryKharkiv(boolean usePreAuthPayment, String serviceTypeId, boolean usePreEstimatedDeliveryFee) {
        Map<String, String> tariff = new HashMap<>();
        switch (serviceTypeId) {
            case "delivery":
                tariff = deliveryKharkivTariff;
                break;
            case "courier":
                tariff = courierKharkivTariff;
                break;
            case "merchant-delivery-booking":
                tariff = deliveryBookingKharkivTariff;
                break;
            case "taxi-econom":
                tariff = taxiKharkivTariff;
                break;
        }

        String body = "{" +
                "\"usePreAuthPayment\": " + usePreAuthPayment + "," +
                "       \"" + CANCELLATION_PENALTY + "\":" + tariff.get(CANCELLATION_PENALTY) + "," +
                "       \"" + DELIVERY_FIXED_FEE + "\":" + tariff.get(DELIVERY_FIXED_FEE) + "," +
                "       \"" + DELIVERY_RATE_PER_MIN + "\":" + tariff.get(DELIVERY_RATE_PER_MIN) + "," +
                "       \"" + DRIVER_SERVICE_FEE_PERCENT + "\":" + tariff.get(DRIVER_SERVICE_FEE_PERCENT) + "," +
                "       \"preEstimatedFixedFee\":" + usePreEstimatedDeliveryFee + "," +
                "       \"" + DELIVERY_RATE_PER_KM + "\":" + tariff.get(DELIVERY_RATE_PER_KM) + "," +
                "       \"" + FREE_CANCELLATION_TIME_SEC + "\":" + tariff.get(FREE_CANCELLATION_TIME_SEC) + "," +
                "        \"" + FREE_DELIVERY_TIME_SEC + "\":" + tariff.get(FREE_DELIVERY_TIME_SEC) + "," +
                "    \"" + MINIMAL_DELIVERY_FEE + "\":\"" + tariff.get(MINIMAL_DELIVERY_FEE) + "\"" ;
               if(!serviceTypeId.equals( "taxi-econom"))
                   body +=  ",\"serviceFee\":[ { \"fee\": 5,  \"startOrderCost\": 0,  \"type\": \"ABSOLUTE\" }]" ;
                body +=  "}";
        new UseAPI().updateServiceTypeCustomerTariffs(serviceTypeId, testInitValues.areaKharkiv(), body);
    }

    @Step
    public Order updateOrderAddressByAdmin(String geoPoint, String orderId, Map<String, String> pickUp) {
        String body = bConstructor.updateLocationBody(pickUp.get(ADDRESS), pickUp.get(LAT), pickUp.get(LON));

        return admin.updateOrderAddress(geoPoint, orderId, body);
    }

    @Step
    public void updateCostCorrectionsForOrder(String orderId, String costCorrectionsName, int value, String correctionType) {
        String body = bConstructor.updateCostCorrectionsForOrder(costCorrectionsName, value, correctionType);

        admin.updateCostCorrectionsForOrder(orderId, body);
    }

    @Step
    public void updateSeveralCostCorrectionsForOrder(String orderId, String correctionType, int value, String... fees) {
        StringBuilder body = new StringBuilder("{");

        for (int i = 0; i < fees.length; i++) {
            body.append(bConstructor.updateCostCorrectionsBlock(fees[i], value, correctionType));
            if (i < fees.length - 1) body.append(",");
            else body.append("}");
        }

        admin.updateCostCorrectionsForOrder(orderId, body.toString());
    }

    @Step
    public Order createRegularOrder(String promo) {
        Map<String, String> firstMerchant = createMerchantMap(merchant, locationKharkivPushkinska1, testInitValues.firstMerchantKharkivPosId(), testInitValues.firstMerchantCheesePizzaId());
        String body = bConstructor.getRegularOrder(stringCurrentTime(), firstMerchant, productQuantity1, locationKharkivShevchenka146, promo, CARD);

        return admin.createOrderForUser(body, firstCustomer.getId());
    }

    @Step
    public Order createRegularOrderWithMerchantAccept() {
        Order order = createRegularOrder(EMPTY_VALUE);
        merchant.acceptOrderById(order.getId());

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createRegularOrderWithDriverAccept(Driver driver) {
        driver.makeOnline(locationKharkivPushkinska1);
        Order order = createRegularOrderWithMerchantAccept();
        driver.acceptOrder();

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createSuspendedRegularOrder() {
        long time = suspendTime + currentTime();
        Map<String, String> firstMerchant = createMerchantMap(merchant, locationKharkivPushkinska1, testInitValues.firstMerchantKharkivPosId(), testInitValues.firstMerchantCheesePizzaId());
        String body = bConstructor.getRegularOrder(String.valueOf(time), firstMerchant, productQuantity1, locationKharkivShevchenka146, EMPTY_VALUE, CARD);
        return admin.createOrderForUser(body, firstCustomer.getId());
    }

    @Step
    public Order createSuspendCourierOrderByFirstCustomer(long time) {
        long futureTime = currentTime() + time;
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.firstCustomerCCId());
        String body1 = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, String.valueOf(futureTime), payment, customerProduct1, EMPTY_VALUE);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createSurprisedRegularOrder() {
        String body = bConstructor.getSurpriseBody(productForSurprise, testInitValues.orderKindM2PReg(), merchant.getId(), secondCustomer.getId());

        return admin.createOrderForUser(body, firstCustomer.getId());
    }

    @Step
    public Order createCourierCCOrderByFirstCustomer() {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.firstCustomerCCId());
        String body1 = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, stringCurrentTime(), payment, customerProduct1, EMPTY_VALUE);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createCourierCCPromoOrderByFirstCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.firstCustomerCCId());
        String body1 = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, stringCurrentTime(), payment, customerProduct1, promo);
        String body2 = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska1, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        firstCustomer.approximateOrderCosts(body2);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createCourierCCUseBalancePromoOrderByFirstCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, true, testInitValues.firstCustomerCCId());
        String body1 = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, stringCurrentTime(), payment, customerProduct1, promo);
        String body2 = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska1, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        firstCustomer.approximateOrderCosts(body2);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createTaxiCCUseBalancePromoOrderByFirstCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, true, testInitValues.firstCustomerCCId());
        String approximateBody = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska100, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        firstCustomer.approximateOrderCosts(approximateBody);
        String body = bConstructor.getTaxiBodyHome(locationKharkivPushkinska100, locationKharkivShevchenka146, stringCurrentTime(), testInitValues.serviceIdTaxiEconom(), promo, payment);

        return firstCustomer.createOrder(body);
    }

    @Step
    public Order createCourierCCPromoOrderBySecondCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.secondCustomerCCId());
        String approximateBody = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska1, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        secondCustomer.approximateOrderCosts(approximateBody);
        String body = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, stringCurrentTime(), payment, customerProduct1, promo);

        return secondCustomer.createOrder(body);
    }

    @Step
    public Order createTaxiCCPromoOrderBySecondCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.secondCustomerCCId());
        String approximateBody = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska100, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        secondCustomer.approximateOrderCosts(approximateBody);
        String body = bConstructor.getTaxiBodyHome(locationKharkivPushkinska100, locationKharkivShevchenka146, stringCurrentTime(), testInitValues.serviceIdTaxiEconom(), promo, payment);

        return secondCustomer.createOrder(body);
    }

    @Step
    public Order createCourierCashOrderByFirstCustomer() {
        Map<String, String> payment = createPaymentCashMap(false);
        String body1 = bConstructor.getCourierOrder(locationKharkivPushkinska1, locationKharkivShevchenka146, stringCurrentTime(), payment, customerProduct1, EMPTY_VALUE);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createCompletedRegularOrderByFirstDriver() {
        firstDriver.makeOnline(locationKharkivPushkinska1);
        Order order = createRegularOrderWithMerchantAccept();

        firstDriver.acceptOrder();
        changeStatusesAcceptedOrderByDriver(order, firstDriver);
        firstDriver.completeOrder();

        createManualTransactionForCustomer(firstCustomer, TransactionConstants.PENALTY, number200, order.getId());

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createCompletedCourierOrderBySecondDriver() {
        secondDriver.makeOnline(locationKharkivPushkinska1);
        Order order = createCourierCCOrderByFirstCustomer();
        secondDriver.acceptOrder();

        changeStatusesAcceptedOrderByDriver(order, secondDriver);
        secondDriver.completeOrder();

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createCompletedCourierCCPromoOrderBySecondDriver(String promo) {
        Order order = createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(promo);
        String orderId = order.getId();

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedTaxiCCPromoOrderBySecondDriver(String promo) {
        Order order = createTaxiCCPromoOrderAcceptedBySecondDriver(promo);

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedCourierCCUseBalancePromoOrderBySecondDriver(String promo) {
        Order order = createCourierCCUseBalancePromoOrderAcceptedBySecondDriver(promo);

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedTaxiCCUseBalancePromoOrderBySecondDriver(String promo) {
        Order order = createTaxiCCUseBalancePromoOrderAcceptedBySecondDriver(promo);

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedCourierCCPromoAndDebtOrderForSecondCustomerBySecondDriver(String promo, double balance) {
        setBalanceToCustomer(balance, negative500, secondCustomer);
        Order order = createCourierCCPromoOrderForSecondCustomerAcceptedBySecondDriver(promo);

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedTaxiCCPromoAndDebtOrderForSecondCustomerBySecondDriver(String promo) {
        Order order = createTaxiCCPromoOrderForSecondCustomerAcceptedBySecondDriver(promo);

        return changeStatusesAndCompleteAcceptedOrderBySecondDriver(order);
    }

    @Step
    public Order createCompletedTaxiOrderBySecondDriver() {
        secondDriver.makeOnline(locationKharkivPushkinska100);
        Order order = createTaxiOrder();
        secondDriver.acceptOrder();

        changeStatusesAcceptedOrderByDriver(order, secondDriver);
        secondDriver.completeOrder();

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createCompletedMdbOrderByFirstDriver() {
        Order order = createMdbOrderWithFirstDriverAccept();
        changeStatusesAcceptedOrderByDriver(order, firstDriver);
        firstDriver.completeOrder();

        createManualTransactionForCustomer(firstCustomer, TransactionConstants.PENALTY, number200, order.getId());

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createCourierCCUseBalancePromoAndDebtOrderForSecondCustomerBySecondDriver(String promo, double balance) {
        setBalanceToCustomer(balance, negative500, secondCustomer);
        Order order = createCourierCCPromoOrderForSecondCustomerAcceptedBySecondDriver(promo);

        return changeStatusesAcceptedOrderByDriver(order, secondDriver);
    }

    @Step
    public Order createCourierCCPromoOrderForFirstCustomerAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska1);
        Order order = createCourierCCPromoOrderByFirstCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createTaxiCCPromoOrderAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska100);
        Order order = createTaxiCCPromoOrderByFirstCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createCourierCCUseBalancePromoOrderAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska1);
        Order order = createCourierCCUseBalancePromoOrderByFirstCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createCourierCCPromoOrderForSecondCustomerAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska1);
        Order order = createCourierCCPromoOrderBySecondCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createTaxiCCPromoOrderForSecondCustomerAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska100);
        Order order = createTaxiCCPromoOrderBySecondCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createTaxiCCUseBalancePromoOrderAcceptedBySecondDriver(String promo) {
        secondDriver.makeOnline(locationKharkivPushkinska100);
        Order order = createTaxiCCUseBalancePromoOrderByFirstCustomer(promo);
        secondDriver.acceptOrder();

        return order;
    }

    @Step
    public Order createCourierOrderAndSecondDriverReadyAtLocation() {
        secondDriver.checkActiveOrderAndCancelIt();
        Order order = createCourierCCOrderByFirstCustomer();
        secondDriver.updateLocation(locationKharkivPushkinska1);

        return order;
    }

    @Step
    public Order createTaxiOrderAndSecondDriverReadyAtLocation() {
        secondDriver.checkActiveOrderAndCancelIt();
        Order order = createTaxiOrder();
        secondDriver.updateLocation(locationKharkivPushkinska100);

        return order;
    }

    @Step
    public Order createCourierOrderAndAcceptByDriver(Driver driver) {
        driver.makeOnline(locationKharkivPushkinska1);
        Order order = createCourierCCOrderByFirstCustomer();
        driver.acceptOrder();

        return order;
    }

    @Step
    public Order createTaxiOrder() {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.firstCustomerCCId());
        String body = bConstructor.getTaxiBodyHome(locationKharkivPushkinska100, locationKharkivShevchenka146, String.valueOf(currentTime()), testInitValues.serviceIdTaxiEconom(), testInitValues.promoSmokeTaxi(), payment);

        return firstCustomer.createOrder(body);
    }

    @Step
    public Order createTaxiOrderForHomeless() {
        Map<String, String> payment = createPaymentCashMap(false);
        String body = bConstructor.getTaxiBodyHomeless(locationKharkivPushkinska100, String.valueOf(currentTime()), testInitValues.serviceIdTaxiEconom(), testInitValues.promoSmokeTaxi(), payment);

        return thirdCustomer.createOrder(body);
    }

    @Step
    public Order createTaxiCCPromoOrderByFirstCustomer(String promo) {
        Map<String, String> payment = createPaymentMap(CC_caps, false, testInitValues.firstCustomerCCId());
        String body1 = bConstructor.getTaxiBodyHome(locationKharkivPushkinska100, locationKharkivShevchenka146, String.valueOf(currentTime()), testInitValues.serviceIdTaxiEconom(), promo, payment);
        String body2 = bConstructor.getApproximateCourierOrderCostsBody(customerProduct, locationKharkivPushkinska100, locationKharkivShevchenka146, promo, payment, stringCurrentTime());
        firstCustomer.approximateOrderCosts(body2);

        return firstCustomer.createOrder(body1);
    }

    @Step
    public Order createMdbOrderWithKharkivPos() {
        String posId = testInitValues.firstMerchantKharkivPosId();
        String body = bConstructor.getMerchantBody(locationKharkivPushkinskaProvulok4, firstCustomer, "156", posId);

        return merchant.createMerchantOrder(body);
    }

    @Step
    public Order createMdbOrderWithFirstDriverAccept() {
        firstDriver.makeOnline(locationKharkivPushkinskaProvulok4);
        Order order = createMdbOrderWithKharkivPos();
        firstDriver.acceptOrder();

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order createTaxiOrderAndAcceptByDriver(Driver driver) {
        driver.makeOnline(locationKharkivPushkinska100);
        Order order = createTaxiOrder();
        driver.acceptOrder();

        return order;
    }

    @Step
    public double getUserBalance(String beneficiaryId, String beneficiaryType, String accountType) {
        return admin.getBalancesSummaryByBeneficiaryIds(beneficiaryId, beneficiaryType, accountType);
    }

    @Step
    public void setBalanceToCustomer(double currentBalance, double newBalanceValue, Customer customer) {
        double amount;

        amount = currentBalance > 0 ? 0 - currentBalance : Math.abs(currentBalance);

        amount = newBalanceValue > 0 ? amount + newBalanceValue : amount - Math.abs(newBalanceValue);

        if (amount > 0)
            createManualTransactionForCustomer(customer, TransactionConstants.BONUS, String.valueOf(Math.abs(amount)), "");
        if (amount < 0)
            createManualTransactionForCustomer(customer, TransactionConstants.PENALTY, String.valueOf(Math.abs(amount)), "");
    }

    @Step
    public Order changeStatusesAndCompleteAcceptedOrderBySecondDriver(Order order) {
        changeStatusesAcceptedOrderByDriver(order, secondDriver);
        secondDriver.completeOrder();

        return updateOrderDetails(order.getId());
    }

    @Step
    public Order changeStatusesAcceptedOrderByDriver(Order order, Driver driver) {
        driver.updateActiveOrderStatus(AT_PICK_UP.getValue());
        driver.updateActiveOrderStatus(ON_THE_WAY_TO_DROP_OFF.getValue());
        driver.updateLocation(order.getAddress(DROP_OFF));
        driver.updateActiveOrderStatus(AT_DROP_OFF.getValue());

        return updateOrderDetails(order.getId());
    }

    @Step
    public void cancelOrder(Order order) {
        admin.cancelOrder(order);
    }

    @Step//don`t delete
    public void cancelCustomerOrder(String orderId) {
        firstCustomer.cancelOrderById(orderId);
    }

    @Step//don`t delete
    public String continueOrder(String orderId) {
        return admin.continueOrder(orderId);
    }

    @Step//don`t delete
    public void forwardOrderToSupport(String orderId) {
        admin.forwardOrderToSupport(orderId);
    }

    @Step
    public Order updateOrderDetails(String orderId) {
        return admin.getOrderById(orderId);
    }

    @Step
    public Transactions createManualTransactionForCustomer(Customer customer, String balanceOpType, String amountValue, String orderId) {
        String body = bConstructor.createManualTransactionBody(balanceOpType, amountValue, customer.getId(), orderId, "");

        return admin.createManualTransaction(body);
    }

    @Step
    public Transactions findTransactionRecords(String orderId, String beneficiaryType, String accountType, String txOperationType, String recordStates) {
        return admin.findTransactionRecords(orderId, beneficiaryType, accountType, txOperationType, recordStates);
    }

    @Step
    public Transactions findSuccessTransactionRecords(String orderId, String beneficiaryType, String accountType, String txOperationType) {
        return findTransactionRecords(orderId, beneficiaryType, accountType, txOperationType, TransactionConstants.SUCCESS);
    }


    @Step
    public void updateOrderCart(String orderId) {
        String body = bConstructor.addProductToCartBody(merchant, testInitValues.firstMerchantCheesePizzaId(), testInitValues.firstMerchantDiscountedPizzaId(), productQuantity1);
        admin.updateOrderCart(orderId, body);
    }

    @Step
    public void waitForReadyToExecuteOrderStatus(Driver driver, Map<String, String> location, String orderId) {
        long waitTime = currentTime() + 2 * 60;

        while (currentTime() < waitTime) {
            driver.updateLocation(location);
            sleep(5000);

            if (updateOrderDetails(orderId).getKey(STATUS).equals(READY_TO_EXECUTE.getValue()))
                break;
        }
    }

    @Step
    public String convertOrderDate(long time) {
        return new SimpleDateFormat("M/d/YY, h:mm a").format(new Date(time * 1000));
    }

    @Step
    public int getDriversCountByStatus(String status) {
        return admin.getDriversCountByStatus(status);
    }

    @Step
    public void checkActiveOrderForSecondDriver(String orderId) {
        assertEquals(orderId, secondDriver.checkActiveOrder());
    }
}