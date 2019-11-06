// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.users;

import utils.objects.Order;
import utils.objects.Transactions;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.log;
import static junit.framework.TestCase.fail;
import static utils.constants.ApiConstants.*;
import static utils.constants.Statuses.COMPLETED;

public class Admin extends BaseUser {

    public Admin(String id, String email, String password) {
        super(id, email, password);
    }

    public Admin() {
        super(testInitValues.adminId(), testInitValues.adminEmail(), testInitValues.password());
    }

    @Override
    protected String getAllInfo() {
        if (allInfo == null) return allInfo = useAPI.systemUserById(id, "");
        return allInfo;
    }

    protected String getAuthToken() {
        // if AUTH_TOKEN is null or expiration period was end - get new token
        if (authToken.get(AUTH_TOKEN) == null || Integer.parseInt(authToken.get(AUTH_EXPIRATION_DATE)) < currentTime()) {
            String generateToken = useAPI.generateTokenSystemUserAllResult(email, password);

            authToken.put(AUTH_TOKEN, getDataFromJson(generateToken, AUTH_TOKEN));
            authToken.put(AUTH_EXPIRATION_DATE, getDataFromJson(generateToken, AUTH_EXPIRATION_DATE));
        }
        return authToken.get(AUTH_TOKEN);
    }

    public Order updateOrderAddress(String geoPoint, String orderId, String body) {
        return new Order(useAPI.updateOrderLocationAddress(geoPoint, getAuthToken(), orderId, body));
    }

    public String getUserLocation(String userId) {
        return useAPI.getUserLocation(getAuthToken(), userId);
    }

    public void updateOrderCart(String orderId, String body) {
        useAPI.updateOrderCart(orderId, body);
    }

    public Order createOrderForUser(String body, String userId) {
        return new Order(useAPI.createOrderForUser(body, getAuthToken(), userId));
    }

    public void forwardOrderToSupport(String orderId) {
        useAPI.forwardOrderToSupport(orderId);
    }

    public Order getOrderById(String orderId) {
        return new Order(useAPI.orderById(orderId));
    }

    public String getOrderProperties(String orderId, String properties) {
        return useAPI.orderById(orderId, properties);
    }

    public void cancelOrderById(String orderId) {
        if (!getOrderProperties(orderId, STATUS).equals(COMPLETED.getValue())) {
            useAPI.forwardOrderToSupport(orderId);
            useAPI.cancelOrderById(orderId);
        }
    }

    public void cancelOrder(Order order) {
        String orderStatus = order.getStatus();

        if (!orderStatus.equals(COMPLETED.getValue()))
            cancelOrderById(order.getId());
        else
            log("order status is " + orderStatus);
    }

    public String continueOrder(String orderId) {
        return useAPI.continueOrder(orderId);
    }

    public Transactions createManualTransaction(String body) {
        return new Transactions(useAPI.createManualTransaction(body));
    }

    public Transactions findTransactionRecords(String orderId, String beneficiaryType, String accountType, String txOperationType, String recordStates) {
        long waitTime = currentTime() + 120;

        Transactions transactions = new Transactions(useAPI.findTransactionRecords(orderId, beneficiaryType, accountType, txOperationType, recordStates));

        while (!transactions.getRecordState().equals(recordStates) && currentTime() < waitTime) {
            log("Wait wor " + recordStates + " " + txOperationType + " " + accountType + " transaction for " + beneficiaryType);

            sleep(3000);

            transactions = new Transactions(useAPI.findTransactionRecords(orderId, beneficiaryType, accountType, txOperationType, recordStates));
        }

        if (!transactions.getRecordState().equals(recordStates))
            fail(recordStates + " " + txOperationType + " " + accountType + " transaction for " + beneficiaryType + " has not been added!");

        return transactions;
    }

    public int getDriversCountByStatus(String status) {
        return useAPI.getDriversCountByStatus(status);
    }

    public void updateCostCorrectionsForOrder(String orderId, String body) {
        useAPI.updateCostCorrectionsForOrder(getAuthToken(), orderId, body);
    }

    public String getTariffCalculator(String orderId) {
        return useAPI.getTariffCalculator(orderId);
    }

    //Get balances summary by beneficiary ids
    public double getBalancesSummaryByBeneficiaryIds(String beneficiaryId, String beneficiaryType, String accountType) {
        String balance = getDataFromJson(useAPI.findUserBalance(beneficiaryId, beneficiaryType, accountType), CONTENT + "." + AMOUNT);
        return Double.parseDouble(removeFirstEndScraping(balance));
    }

    private String removeFirstEndScraping(String string) {
        string = string.startsWith("[") ? string.substring(1) : string;
        string = string.endsWith("]") ? string.substring(0, string.length() - 1) : string;

        if (string.startsWith("[") || string.endsWith("]")) string = removeFirstEndScraping(string);

        return string;
    }

    public String getPreAuthData(String orderId) {
        return useAPI.preAuthData(orderId);
    }
}
