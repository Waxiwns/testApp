// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils;

import core.TestInitValues;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.log;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static utils.constants.ApiConstants.LAT;
import static utils.constants.ApiConstants.LON;

public class UseAPI {

    // Response Types
    private String getResponseType = "GET";
    private String postResponseType = "POST";
    private String putResponseType = "PUT";
    private String deleteResponseType = "DELETE";

    // Status Codes
    private int statusCode200 = 200;
    private int statusCode201 = 201;
    private int statusCode202 = 202;
    private int statusCode429 = 429;

    // Paths -------------------------------------------------------------------------
    private String endPoint = "https://regres.toyou.delivery";

    // User
    private String systemUserPath = "/user/v1/systemuser/";
    private String systemUserAuthTokenPath = systemUserPath + "authtoken";
    private String userAuthTokenPath = "/user/v1/user/authtoken";
    private String mgmtFullCustomerPath = "/user/mgmt/v1/users/";
    private String mgmtUserPath = "/user/mgmt/v1/user/";

    // Driver
    private String driverUserAuthTokenPath = "/driver/v1/driver/authtoken";
    private String driverLocationPath = "/driver/v1/driverlocation";
    private String driverAcceptOrderPath = "/driver/v1/acceptorder";
    private String mgmtFullDriverPath = "/driver/mgmt/v1/profiles/";
    private String mgmtDriverInfoPath = "/driver/mgmt/v1/driverinfo";
    private String mgmtDriverAnalysePath = "/driver/mgmt/v1/driver/analysedriver";

    // Facade
    private String facadeOrdersPath = "/facade/v7/orders";

    // Catalog
    private String merchantUserAuthTokenPath = "/catalog/v1/merchantuser/authtoken";
    private String rootcategoriesPath = "/catalog/mgmt/v2/rootcategories/";
    private String mgmMerchantsPath = "/catalog/mgmt/v1/merchants/";
    private String tagsPath = "/catalog/mgmt/v1/tags/";
    private String merchantTagsPath = "/catalog/mgmt/v1/merchanttags/";
    private String mgmPosPath = "/catalog/mgmt/v1/pos/";
    private String mgmProductsPath = "/catalog/mgmt/v1/products/";
    private String mgmServiceTypesPath = "/catalog/mgmt/v1/servicetypes/";
    private String uiAdminMerchantUserPath = "/catalog/uiadmin/v1/merchantusers";
    private String searchProductPath = "/catalog/search/v3/products/";

    // Delivery
    private String mgmtOrdersPath = "/delivery/mgmt/v1/orders/";
    private String merchantUserOrdersPath = "/delivery/v1/merchantuser/orders/";
    private String merchantOrdersPath = "/delivery/v1/merchant/orders";
    private String deliveryCreateOrderForUserPath = "/delivery/mgmt/v4/users/";
    private String mgmOrderByNumberPath = "/delivery/mgmt/v2/orderbynumber/";
    private String driverDeliveryStatusPath = "/delivery/v1/driver/order/delivery-status";
    private String driverOrderPath = "/delivery/v2/driver/order";
    private String deliveryV2OrdersPath = "/delivery/v2/orders/";
    private String completeOrderADriverUrl = "/delivery/v1/driver/order/complete";
    private String deliveryOrdersPath = "/delivery/v7/orders";
    private String deliveryOrderCostsPath = "/delivery/v2/ordercosts";
    private String deliveryV1OrdersPath = "/delivery/v1/orders/";
    private String deliveryOrderCostsPathTaxi = "/delivery/v3/ordercosts/TAXI";
    private String deliveryTariffCalculatorPath = "/delivery/mgmt/v2/tariffcalculator/order/";

    // Payment
    private String balanceOperationPath = "/payment/mgmt/v1/balanceoperation";
    private String transactionsFindPath = "/payment/mgmt/v2/transactions/find";
    private String balancesByIdsPath = "/payment/uiadmin/v1/transactions/balancesbyids";

    //---------------------------------------------------------------------------------

    // Expect Results
    private String authTokenResult = "authToken";
    private String allResult = "";
    private String trueResult = "true";
    private String falseResult = "false";
    private String idResult = "id";
    private String resultResult = "result";
    private String contentResult = "content";
    private String statusResult = "status";
    private String availableResult = "available";
    private String availableSinceResult = "availableSince";
    private String requiresOrderApprovalResult = "requiresOrderApproval";
    private String merchantOrderStatus = "merchantOrderStatus";
    private String orderNumber = "orderNumber";

    // System User Auth Token
    private String systemUserAuthToken;

    private static final TestInitValues TEST_INIT_VALUES = ConfigFactory.create(TestInitValues.class);

    private Response setResponse(String responseType, RequestSpecification request, String path) {
        Response response;
        switch (responseType) {
            case "POST":
                response = request.post(endPoint + path);
                break;
            case "PUT":
                response = request.put(endPoint + path);
                break;
            case "DELETE":
                response = request.delete(endPoint + path);
                break;
            default:
                response = request.get(endPoint + path);
                break;
        }
        return response;
    }

    // Send request with all params
    private String sendRequest(String responseType, String token, Object body, String path, String expectResult, int statusCode) {
        RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());

        Map<String, Object> header = new HashMap<>();
        header.put("Accept-Language", "en,ar");
        if (body != null) {
            if (body instanceof Map) {
                Map<String, Object> bodyMap = (Map) body;
                if (bodyMap.containsKey("Accept-Language")) {
                    header.put("Accept-Language", bodyMap.get("Accept-Language"));
                }
            }
        }
        if (token != null) header.put("Authorization", token);

        RequestSpecification request = given()
                .contentType("application/json")
                .headers(header);

        if (body != null) request.body(body);

        request.when();

//        bypass statusCode - 429   ------------------------------------------------------
        Response resp = setResponse(responseType, request, path);

        if (resp.statusCode() == statusCode429) {
            sleep(2000);
            log("statusCode - " + resp.statusCode());
            resp = setResponse(responseType, request, path);
        }

        String response = resp
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract().response()
                .asString();
//        ---------------------------------------------------------------------------------


//        without bypass statusCode - 429    ----------------------------------------------
//                String response = setResponse(responseType, request, path)
//                .then()
//                .statusCode(statusCode)
//                .log().all()
//                .extract().response()
//                .asString();
//        ---------------------------------------------------------------------------------

        if (expectResult.equals(allResult)) return response;
        return from(response).getString(expectResult);
    }

    // Send request without body
    private String sendRequest(String responseType, String token, String path, String expectResult, int statusCode) {
        return sendRequest(responseType, token, null, path, expectResult, statusCode);
    }

    // Send request without token and body
    private String sendRequest(String responseType, String path, String expectResult, int statusCode) {
        return sendRequest(responseType, null, null, path, expectResult, statusCode);
    }

    //Accept drop-off location and (optionally) provide a delivery date
    public void acceptOrderByFriend(String orderId, String body, String userToken) {
        String path = deliveryV2OrdersPath + orderId + "/dropoff/accept";
        sendRequest(putResponseType, userToken, body, path, allResult, statusCode200);
    }

    // Update order drop-off address
    public String updateOrderLocationAddress(String geoPoint, String systemUserAuthToken, String orderId, String body) {
        String path = mgmtOrdersPath + orderId + "/" + geoPoint.toLowerCase();

        return sendRequest(putResponseType, systemUserAuthToken, body, path, allResult, statusCode200);
    }


    //Get user locations by user id
    public String getUserLocation(String systemUserAuthToken, String userId) {
        String path = mgmtUserPath + userId + "/locations";
        return sendRequest(getResponseType, systemUserAuthToken, path, allResult, statusCode200);
    }

    // Generate tokens for existing system user by email and password
    public String generateTokenSystemUserAllResult(String email, String password) {
        log("Generate Token System User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, systemUserAuthTokenPath, allResult, statusCode200);
    }

    public String generateTokenSystemUser(String email, String password) {
        log("Generate Token System User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, systemUserAuthTokenPath, authTokenResult, statusCode200);
    }

    // Generate tokens for existing system user
    public String systemUserAuthToken() {
        if (systemUserAuthToken == null)
            systemUserAuthToken = generateTokenSystemUser(TEST_INIT_VALUES.adminEmail(), TEST_INIT_VALUES.password());
        return systemUserAuthToken;
    }

    // Generate tokens for existing merchant user by email and password
    public String generateTokenMerchantUserAllResult(String email, String password) {
        log("Generate Token Merchant User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, merchantUserAuthTokenPath, allResult, statusCode200);
    }

    public String generateTokenMerchantUser(String email, String password) {
        log("Generate Token Merchant User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, merchantUserAuthTokenPath, authTokenResult, statusCode200);
    }

    // Generate tokens for existing driver user by email and password
    public String generateTokenDriverUserAllResult(String email, String password) {
        log("Generate Token Driver User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, driverUserAuthTokenPath, allResult, statusCode200);
    }

    public String generateTokenDriverUser(String email, String password) {
        log("Generate Token Driver User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, driverUserAuthTokenPath, authTokenResult, statusCode200);
    }

    //Continue order flow execution
    public String continueOrder(String orderId) {

        String path = mgmtOrdersPath + orderId + "/continue";
        return sendRequest(putResponseType, systemUserAuthToken(), "", path, allResult, statusCode200);
    }

    //Get order pre auth data by order identifier
    public String preAuthData(String orderId) {
        String path = mgmtOrdersPath + orderId + "/pre-auth-data";
        return sendRequest(getResponseType, systemUserAuthToken(), "", path, allResult, statusCode200);
    }

    // Generate tokens for existing user by email and password
    public String generateTokenUserAllResult(String email, String password) {
        log("Generate Token Customer User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, userAuthTokenPath, allResult, statusCode202);
    }

    public String generateTokenUser(String email, String password) {
        log("Generate Token Customer User");

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return sendRequest(postResponseType, null, body, userAuthTokenPath, authTokenResult, statusCode202);
    }

    // Link Root Category with merchant
    public String linkRootCategoryWithMerchant(String merchantId, String categoryId) {
        String path = rootcategoriesPath + categoryId + "/linkmerchant/" + merchantId;

        return sendRequest(postResponseType, systemUserAuthToken(), path, resultResult, statusCode200);
    }

    // Unlink Root Category with merchant
    public String unlinkRootCategoryWithMerchant(String merchantId, String categoryId) {
        String path = rootcategoriesPath + categoryId + "/linkmerchant/" + merchantId;

        return sendRequest(deleteResponseType, systemUserAuthToken(), path, resultResult, statusCode200);
    }

    // Create Product Option
    public String createProductOption(String merchantId, String name, String title) {
        String path = mgmMerchantsPath + merchantId + "/productoptions";

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("title", title);
        body.put("required", false);
        body.put("multiselect", true);

        return sendRequest(postResponseType, systemUserAuthToken(), body, path, idResult, statusCode201);
    }

    // Update Service Type Customer Tariffs
    public String updateServiceTypeCustomerTariffs(String serviceTypeId, String areaId, String body) {
        String path = mgmServiceTypesPath + serviceTypeId + "/operationareas/" + areaId + "/tariffs";
        return sendRequest(putResponseType, systemUserAuthToken(), body, path, allResult, statusCode200);
    }


    // Create Tag
    public String createTag(String name, String description) {
        Map<String, Object> body = new HashMap<>();
        body.put("description", description);
        body.put("name", name);

        return sendRequest(postResponseType, systemUserAuthToken(), body, tagsPath, idResult, statusCode201);
    }

    // Delete Tag
    public String deleteTag(String tagId) {
        String path = tagsPath + tagId;

        return sendRequest(deleteResponseType, systemUserAuthToken(), path, resultResult, statusCode200);
    }

    // List merchant Tags
    public String listMerchantTags(String merchantId) {
        String path = merchantTagsPath + merchantId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, contentResult, statusCode200);
    }

    // Get order costs calculation result stored in order
    public String getTariffCalculator(String orderId) {
        String path = deliveryTariffCalculatorPath + orderId + "/costcalculationresults";

        return sendRequest(getResponseType, systemUserAuthToken(), path, allResult, statusCode200);
    }

    //Find merchant users
    public String getMerchantUsers(String merchantId) {
        String path = uiAdminMerchantUserPath + "/" + merchantId;
        return sendRequest(getResponseType, systemUserAuthToken(), path, allResult, statusCode200);
    }

    // Update list of tags available for merchant (ordered list of tag ids)
    public String updateListOfTagsForMerchant(String merchantId, String tagId) {
        String path = merchantTagsPath + merchantId;

        List<String> body = new ArrayList<>();
        body.add(tagId);

        return sendRequest(putResponseType, systemUserAuthToken(), body, path, resultResult, statusCode200);
    }

    // Create order for user
    public String createOrderForUser(String body, String systemUserAuthToken, String userId) {
        String path = deliveryCreateOrderForUserPath + userId + "/orders";

        return sendRequest(postResponseType, systemUserAuthToken, body, path, allResult, statusCode201);
    }

    public String createOrderForUser(String body, String userId) {
        return createOrderForUser(body, systemUserAuthToken(), userId);
    }

    //Merchant accept order
    public String acceptOrderByMerchant(String merchantAuthToken, String orderId) {
        String path = merchantOrdersPath + "/" + orderId + "/accept";

        return sendRequest(postResponseType, merchantAuthToken, path, resultResult, statusCode200);
    }

    public void driverAcceptOrder(String driverAuthToken) {
        sendRequest(postResponseType, driverAuthToken, driverAcceptOrderPath, allResult, statusCode200);
    }


    // Get Order by id
    public String orderById(String orderId) {
        String path = mgmtOrdersPath + orderId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, allResult, statusCode200);
    }

    // Get Order by id
    public String orderById(String orderId, String expectResult) {
        String path = mgmtOrdersPath + orderId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, expectResult, statusCode200);
    }

    // Get Order Status by id
    public String orderStateById(String orderId) {
        return orderById(orderId, statusResult);
    }

    // Get Order Status by id
    public String orderNumberById(String orderId) {
        return orderById(orderId, orderNumber);
    }

    // Get product availability in the specified POS
    public boolean getProductAvailabilityInTheSpecifiedPOS(String posId, String productId) {
        String path = mgmPosPath + posId + "/availableProducts/" + productId;

        return Boolean.valueOf(sendRequest(postResponseType, systemUserAuthToken(), path, availableResult, statusCode201));
    }

    // Set product availability in the specified POS
    public boolean setProductAvailabilityInTheSpecifiedPOS(String posId, String productId) {
        String path = mgmPosPath + posId + "/products/" + productId + "/availability";

        return Boolean.valueOf(sendRequest(getResponseType, path, availableResult, statusCode200));
    }

    // Get product availability in the specified POS
    public boolean getProductAvailabilityAvailableSinceInTheSpecifiedPOS(String posId, String productId) {
        String path = mgmPosPath + posId + "/products/" + productId + "/availability";

        return Boolean.valueOf(sendRequest(getResponseType, path, availableSinceResult, statusCode200));
    }

    // Mark product option value as available since specified date by the specified POS
    public boolean makeProductTrueAvailableSinceInSpecificPosViaAPI(String posId, String productId, String dateUnix) {
        String path = mgmPosPath + posId + "/products/" + productId + "/availableSince?dateSec=" + dateUnix;

        return Boolean.valueOf(sendRequest(putResponseType, systemUserAuthToken(), path, resultResult, statusCode201));
    }

    // Update Merchant by ID with body
    public String updateMerchantById(String merchantId, String body) {
        String path = mgmMerchantsPath + merchantId;

        return sendRequest(putResponseType, systemUserAuthToken(), body, path, resultResult, statusCode200);
    }

    // Update driver last location
    public void updateDriverLocation(String driverAuthToken, String heading, String lat, String lon, String status) {
        Map<String, Object> body = new HashMap<>();
        body.put("heading", heading);
        body.put(LAT, lat);
        body.put(LON, lon);
        body.put("status", status);

        sendRequest(putResponseType, driverAuthToken, body, driverLocationPath, resultResult, statusCode200);
    }

    // get driver last location and status
    public String getDriverLocation(String driverAuthToken) {
        return sendRequest(getResponseType, driverAuthToken, driverLocationPath, allResult, statusCode200);
    }

    public void updateDriverLocation(String driverAuthToken, String lat, String lon) {
        updateDriverLocation(driverAuthToken, "1", lat, lon, "1");
    }

    // DEPRECATED: Create order
    public String createFacadeOrder(String userAuthToken, String orderBody) {
        return sendRequest(postResponseType, userAuthToken, orderBody, facadeOrdersPath, allResult, statusCode201);
    }

    public String createOrderByCustomer(String userAuthToken, String orderBody) {
        return sendRequest(postResponseType, userAuthToken, orderBody, deliveryOrdersPath, allResult, statusCode201);
    }

    // Approve order by system user
    public void approveOrderBySystemUser(String orderId) {
        String approvePath = mgmtOrdersPath + orderId + "/approve";
        sendRequest(putResponseType, systemUserAuthToken(), approvePath, allResult, statusCode200);
    }

    // Continue order flow execution
    public void continueOrderFlowExecution(String orderId) {
        String continuePath = mgmtOrdersPath + orderId + "/continue";
        sendRequest(putResponseType, systemUserAuthToken(), continuePath, allResult, statusCode200);
    }

    // Accept order by POS operator
    public void acceptOrderByPosOperator(String merchantAuthToken, String orderId, String posId) {
        String ordersPath = merchantUserOrdersPath + orderId + "/pointofsales/" + posId + "/accept";
        sendRequest(putResponseType, merchantAuthToken, ordersPath, allResult, statusCode200);
    }

    // Forward order to support
    public void forwardOrderToSupport(String orderId) {
        Map<String, Object> bodyAdmin = new HashMap<>();
        bodyAdmin.put("reason", "Admin");

        String toSupportPath = mgmtOrdersPath + orderId + "/to-support";
        sendRequest(putResponseType, systemUserAuthToken(), bodyAdmin, toSupportPath, allResult, statusCode200);
    }

    // Cancel order by id. Only completed orders can not be canceled
    public void cancelOrderById(String orderId) {
        Map<String, Object> bodyAdmin = new HashMap<>();
        bodyAdmin.put("reason", "Admin");

        String cancelPath = mgmtOrdersPath + orderId + "/cancel";
        sendRequest(putResponseType, systemUserAuthToken(), bodyAdmin, cancelPath, allResult, statusCode200);
    }

    // Cancel of customer's order by id
    public void cancelCustomersOrderById(String userAuthToken, String orderId) {
        String cancelPath = deliveryV1OrdersPath + orderId + "/cancel";

        sendRequest(putResponseType, userAuthToken, cancelPath, resultResult, statusCode200);
    }

    // Complete order by id
    public void completeOrderById(String orderId) {
        String completePath = mgmtOrdersPath + orderId + "/complete";
        sendRequest(putResponseType, systemUserAuthToken(), completePath, resultResult, statusCode200);
    }

    // Update Active Order's status for driver
    public void updateActiveOrderStatusForDriver(String driverAuthToken, long eventTime, String status) {
        Map<String, Object> body = new HashMap<>();
        body.put("clientEventTime", eventTime);
        body.put("status", status);

        sendRequest(putResponseType, driverAuthToken, body, driverDeliveryStatusPath, resultResult, statusCode200);
    }

    // Create merchant's order
    public String createMerchantOrder(String merchantUserAuthToken, String body) {
        return sendRequest(postResponseType, merchantUserAuthToken, body, merchantOrdersPath, allResult, statusCode201);
    }

    // Find merchant Orders
    public String getMerchantOrder(String merchantUserAuthToken) {
        return sendRequest(getResponseType, merchantUserAuthToken, merchantOrdersPath, idResult, statusCode200);
    }

    // Create Product by Merchant
    public String createNewProduct(String merchantUserAuthToken, String merchantId) {
        String path = mgmMerchantsPath + merchantId + "/products";
        return sendRequest(postResponseType, merchantUserAuthToken, path, idResult, statusCode201);
    }

    // Delete Product
    public void deleteProduct(String prodId) {
        String path = mgmProductsPath + prodId;
        sendRequest(deleteResponseType, systemUserAuthToken(), path, resultResult, statusCode200);
    }

    // Get SearchProductDto
    public String getProductByIdForMerchant(String local, String productId) {
        String path = searchProductPath + productId + "?currency=SAR";
        Map<String, Object> body = new HashMap<>();
        body.put("Accept-Language", local);
        return sendRequest(getResponseType, null, body, path, allResult, statusCode200);
    }

    // Get Active Order details for driver
    public String getActiveOrderDetailsForDriver(String driversAuthToken) {
        try {
            return sendRequest(getResponseType, driversAuthToken, driverOrderPath, idResult, statusCode200);
        } catch (AssertionError e) {
            return "Order not found";
        }
    }

    public String createManualTransaction(String body) {
        return sendRequest(postResponseType, systemUserAuthToken(), body, balanceOperationPath, allResult, statusCode200);
    }

    // Find transaction records
    public String findTransactionRecords(String orderId, String beneficiaryType, String accountType, String txOperationType, String recordStates) {
        String path = transactionsFindPath + "?orderId=" + orderId + "&beneficiaryType=" + beneficiaryType + "&accountType=" + accountType + "&txOperationType=" + txOperationType + "&recordStates=" + recordStates;

        return sendRequest(getResponseType, systemUserAuthToken(), path, allResult, statusCode200);
    }

    // Request cancel of customer's order by id
    public String cancelOrderByMerchant(String merchantAuthToken, String orderId) {
        String path = merchantUserOrdersPath + orderId + "/cancel?cancel-reason=test";

        return sendRequest(putResponseType, merchantAuthToken, null, path, resultResult, statusCode200);
    }

    public String updateOrderCart(String orderId, String body) {
        String path = mgmtOrdersPath + orderId + "/items";
        return sendRequest(putResponseType, systemUserAuthToken(), body, path, trueResult, statusCode200);
    }

    //Driver complete order
    public String completeOrderAsDriver(String driverToken) {
        return sendRequest(putResponseType, driverToken, completeOrderADriverUrl, trueResult, statusCode200);
    }

    //Get Driver Information
    public String driverById(String diverId, String expectResult) {
        String path = mgmtFullDriverPath + diverId;
        return sendRequest(getResponseType, systemUserAuthToken(), path, expectResult, statusCode200);
    }

    public String customerById(String customerId, String expectResult) {
        String path = mgmtFullCustomerPath + customerId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, expectResult, statusCode200);
    }

    public String systemUserById(String userId, String expectResult) {
        String path = systemUserPath + userId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, expectResult, statusCode200);
    }

    public String merchantById(String merchantId, String expectResult) {
        String path = mgmMerchantsPath + merchantId;

        return sendRequest(getResponseType, systemUserAuthToken(), path, expectResult, statusCode200);
    }

    //List All Driver Accounts
    public int getDriversCountByStatus(String status) {
        String queryPath = "";
        switch (status) {
            case "ANY":
                queryPath = "?enabled=true";
                break;
            case "READY":
                queryPath = "?enabled=true&online=true&hasOrder=false";
                break;
            case "ORDER_EXECUTION":
                queryPath = "?enabled=true&online=true&hasOrder=true";
                break;
            case "OFFLINE":
                queryPath = "?enabled=true&online=false&hasOrder=false";
                break;
            case "ORDER_OFFLINE":
                queryPath = "?enabled=true&online=false&hasOrder=true";
                break;
        }
        String path = mgmtDriverInfoPath + queryPath;
        String result = sendRequest(getResponseType, systemUserAuthToken(), path, "total", statusCode200);
        return Integer.parseInt(result);
    }

    //    DEPRECATED: Estimate approximate order costs
    public String approximateOrderCosts(String userAuthToken, String body) {
        return sendRequest(postResponseType, userAuthToken, body, deliveryOrderCostsPath, allResult, statusCode200);
    }

    //    Update costCorrections for order
    public String updateCostCorrectionsForOrder(String systemUserAuthToken, String orderId, String body) {
        String path = mgmtOrdersPath + orderId + "/costcorrection";
        return sendRequest(putResponseType, systemUserAuthToken, body, path, allResult, statusCode200);
    }

    //   Estimate approximate orders cost for TAXI orders
    public String approximateOrderCostsTaxi(String userAuthToken, String body) {
        return sendRequest(postResponseType, userAuthToken, body, deliveryOrderCostsPathTaxi, allResult, statusCode200);
    }

    // Find balance of second customer
    public String findUserBalance(String beneficiaryId, String beneficiaryType, String accountType) {
        String path = balancesByIdsPath + "?beneficiaryIds=" + beneficiaryId + "&beneficiaryType=" + beneficiaryType + "&accountType=" + accountType;

        return sendRequest(getResponseType, systemUserAuthToken(), path, allResult, statusCode200);
    }
}
