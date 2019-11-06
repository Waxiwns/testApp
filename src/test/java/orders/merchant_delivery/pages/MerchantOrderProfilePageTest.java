// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static pages.modalPages.DialogModal.CANCEL_ORDER;
import static pages.orderPageTabs.OrderHeaderPage.CUSTOMERS;
import static pages.orderPageTabs.OrderHeaderPage.DRIVERS;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.Buttons.TO_SUPPORT;
import static utils.constants.Buttons.*;
import static utils.constants.PageIdentifiers.*;
import static utils.constants.Statuses.*;

@Log4j
public class MerchantOrderProfilePageTest extends BaseTest {

    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();
        logPreconditionStep(4, "Create courier new order");
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }

    public void createOrderAndGoToProfilePage(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to order profile page");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);

        logPreconditionStep(7, "Check order profile page is active");
        orderHeaderPageSteps.isActiveTab(PROFILE);
    }

    @Test
    @Description("Driver section: Check driver information displaying at 'Driver' section (Name, Phone, Car)")
    public void checkDriverInformationInDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));

        logStep(1, "Get driver information");
        String fullName = firstDriver.getFullName();
        String car = firstDriver.getCar();

        logStep(2, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(3, "Check Driver section");
        log("Check the driver name field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.NAME, fullName);
        log("Check the driver phone field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.PHONE, firstDriver.getPhone());
        log("Check the driver car field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);
    }

    @Test
    @Description("Driver section: Check that 'Details' button at 'Driver' section leads to Driver Profile page in English")
    public void checkDetailsButtonInDriverSectionEn() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));
        logStep(1, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Click button details");
        orderProfilePageSteps.sectionButtonClick(DRIVER, DETAILS);

        logStep(3, "Check that information on driver page is correct");
        driverDetailsSteps.driverFirstNameEnShouldHaveText(firstDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(firstDriver.getLastName());
    }

    @Test
    @Description("Driver section: Check 'Analyze' button functionality at 'Driver' section")
    public void checkAnalyzeButton() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));
        logStep(1, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check 'Analyze' button functionality at 'Driver' section");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);

        logStep(3, "Check the 'Driver analyse' page");
        driverAnalysePageSteps.idInputFieldShouldBeVisible();
    }

    @Test
    @Description("Driver section: Check the 'Driver analyse' page")
    public void checkAnalyzeButtonPage() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));
        logStep(1, "Check 'Analyze' button functionality at 'Driver' section");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);

        logStep(2, "Driver field should be visible");
        driverAnalysePageSteps.idInputFieldShouldBeVisible();

        logStep(3, "Click to analise button");
        driverAnalysePageSteps.getButtonClick();
        sleep(2000);

        logStep(4, "Check result block and them content");
        driverAnalysePageSteps.resultBlockShouldHaveText(testInitValues.firstDriverId());
    }

    @Test
    @Description("Driver section: Check 'Change Order Driver' button functionality")
    public void checkChangeOrderButtonInDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Send driver to support to can assign driver for order");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Check Driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(3, "Check change button is display and not disabled");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);

        logStep(4, "Check that after clicking the button we can see the model map");
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.modalMapCloseButtonClick();
    }

    @Test
    @Description("Customer section: Check Customer name displayed at 'Customer' section")
    public void checkCustomerName() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check customer section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check Customer name displayed at 'Customer' section");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.NAME, firstCustomer.getFullName());
    }

    @Test
    @Description("Customer section: Check Customer phone displayed at 'Customer' section")
    public void checkCustomerPhone() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check customer section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check Customer phone displayed at 'Customer' section");
        orderProfilePageSteps.sectionParameterShouldHaveText(CUSTOMER, OrderProfilePage.PHONE, firstCustomer.getPhone());

    }

    @Test
    @Description("Customer section: Check Section name: Customer for regular PT/delivery/service orders and Customer(Cashier) for MDB orders")
    public void checkCustomerSectionName() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Check customer section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER_CASHIER);
    }

    @Test
    @Description("Customer section: Check that 'Details' button at 'Customer' section leads to Customer Profile page")
    public void checkCustomerDetailsPage() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check customer section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(CUSTOMER);

        logStep(2, "Check details button is display and not disabled");
        orderProfilePageSteps.sectionButtonClick(CUSTOMER, DETAILS);

        logStep(3, "Clicking the button and check customer details page");
        customerDetailsPageSteps.customerFirstNameEnShouldHaveText(firstCustomer.getFirstName());
        customerDetailsPageSteps.customerLastNameEnShouldHaveText(firstCustomer.getLastName());
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'Show map' / 'Show table' switcher functionality at 'Change Order Driver' form")
    public void checkShowMapSwitcher() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Transit order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Open change driver pop-up");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.showTableButtonClick();

        logStep(3, "Check that we on the page with filters field");
        changeDriverModalSteps.orderServiceTypeFieldShouldByVisible();

        logStep(4, "Come back to modal map");
        changeDriverModalSteps.showMapButtonClick();

        logStep(5, "Check that we can look at the map");
        changeDriverModalSteps.cityMapShouldBeVisible();
    }

    @Test
    @Description("Change Driver pop-up and Drivers list: Check pagination at 'Change Order Driver' form")
    public void checkPopUpPagination() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Transit order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Open change driver pop-up and select table tab");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.showTableButtonClick();

        logStep(3, "Select Any status and apply filters");
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(4, "Check pagination");
        log("Check title");
        paginationPageSteps.checkItemsPerPageTitleShouldBeEnglish();
        log("Check Drop down value");
        paginationPageSteps.checkThatItemsPerPageDropDownFirstValueExist();
        log("Check buttons");
        paginationPageSteps.checkVisibilityOfGoFirstPageButtonStep();
        paginationPageSteps.checkVisibilityOfGoToThePreviousPageButtonStep();
        paginationPageSteps.checkVisibilityOfGoToTheLastPageButtonStep();
        paginationPageSteps.checkVisibilityOfGoToTheNextPageButtonStep();
        log("Check buttons functionality");
        paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("1");
        paginationPageSteps.clickNextPageButtonStep();
        sleep(1000);
        paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("2");
        paginationPageSteps.clickNextPageButtonStep();
        sleep(1000);
        paginationPageSteps.checkThatActiveNumberButtonHaveTextStep("3");

    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'cross' and 'Close' buttons on 'Change Order Driver' pop-up")
    public void checkCrossAndCloseButton() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(2, "Check cross button on map");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.crossModelMapClick();
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();

        logStep(3, "Check close button on map");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.modalMapCloseButtonClick();
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();

        logStep(4, "Check cross button on table");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.showTableButtonClick();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();
        changeDriverModalSteps.crossModelMapClick();
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();

        logStep(5, "Check close button on table");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.showTableButtonClick();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();
        changeDriverModalSteps.modalMapCloseButtonClick();
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check that drivers with 'Ready' state are displayed in the table by default")
    public void checkDefaultDriverStatus() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(2, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check that driver state is  'Ready' by default");
        changeDriverModalSteps.driverStatusFieldShouldHaveText(READY.getValue());

        logStep(5, "Check that drivers with 'Ready' state are displayed in the table by default");
        changeDriverModalSteps.selectDriverButtonShouldBeVisible();
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'Driver status' filter functionality")
    public void checkDriverStatusFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check ANY status filter ");
        changeDriverModalSteps.checkStatusFieldEnglish(ANY, useAPISteps.getDriversCountByStatus(ANY.getValue()));

        logStep(5, "Check READY status filter ");
        changeDriverModalSteps.checkStatusFieldEnglish(READY, useAPISteps.getDriversCountByStatus(READY.getValue()));

        logStep(6, "Check OFFLINE status filter ");
        changeDriverModalSteps.checkStatusFieldEnglish(OFFLINE, useAPISteps.getDriversCountByStatus(OFFLINE.getValue()));

        logStep(7, "Check ORDER_OFFLINE status filter ");
        changeDriverModalSteps.checkStatusFieldEnglish(ORDER_OFFLINE, useAPISteps.getDriversCountByStatus(ORDER_OFFLINE.getValue()));
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'First name' filter functionality")
    public void checkFirstNameFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Write firstName in the field and select Any status");
        changeDriverModalSteps.inputInTheUserFirstNameField(firstDriver.getFirstName());
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());

        logStep(5, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(6, "Check that result is correct");
        changeDriverModalSteps.checkResultByDriverProperties(firstDriver.getFirstName());
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'Last name' filter functionality")
    public void checkLastNameFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Write lastName in the field and select Any status");
        changeDriverModalSteps.inputInTheUserLastNameField(firstDriver.getLastName());
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());

        logStep(5, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(6, "Check that result is correct");
        changeDriverModalSteps.checkResultByDriverProperties(firstDriver.getLastName());
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'City' filter functionality")
    public void checkCityFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Write city in the field and select READY status");
        changeDriverModalSteps.inputInTheCityField(firstDriver.getCity());
        changeDriverModalSteps.inputInTheDriverStatusField(READY.getValue());

        logStep(5, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(6, "Check that result is correct");
        changeDriverModalSteps.checkResultBlockForSelectedStatus(READY.getEnValue(), 1);

    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'Clear filter' button at 'Change Order Driver' form")
    public void checkClearFilterButton() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Entering a value in the field");
        changeDriverModalSteps.inputInTheUserFirstNameField(orderId);
        changeDriverModalSteps.inputInTheUserPhoneOrEmailField(orderId);
        changeDriverModalSteps.inputInTheUserLastNameField(orderId);
        changeDriverModalSteps.inputInTheCityField(orderId);

        logStep(5, "Click to the button");
        changeDriverModalSteps.filterCancelButtonClick();

        logStep(6, "Check that field is empty");
        changeDriverModalSteps.userFirstNameFieldShouldHaveText("");
        changeDriverModalSteps.userLastNameFieldShouldHaveText("");
        changeDriverModalSteps.userPhoneOrEmailFieldShouldHaveText("");
        changeDriverModalSteps.cityFieldShouldHaveText("");
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check 'Phone or email' filter functionality")
    public void checkPhoneOrEmailFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Write phone in the field and select Any status");
        changeDriverModalSteps.inputInTheUserPhoneOrEmailField(firstDriver.getPhone());
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());

        logStep(5, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(6, "Check that result is correct");
        changeDriverModalSteps.checkResultByDriverProperties(firstDriver.getPhone());
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check possibility to select driver via grid at 'Change Order Driver' form")
    public void checkPossibilityToSelectDriverViaGrid() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());


        logStep(1, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(2, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(3, "Show driver change modal window");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Write phone in the field and select Any status");
        changeDriverModalSteps.inputInTheUserPhoneOrEmailField(firstDriver.getPhone());
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());

        logStep(5, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(6, "Select active driver");
        changeDriverModalSteps.selectDriverButtonClick();

        logStep(7, "Check driver information displaying at 'Driver' section (Name, Phone, Car)");
        log("Check the driver name field");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.NAME, firstDriver.getFullName());
        log("Check the driver phone field");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.PHONE, firstDriver.getPhone());
        log("Check the driver car field");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, firstDriver.getCar());
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check that Service Type is pre-selected according to Order Service Type")
    public void CheckThatServiceTypeIsPreSelectedAccordingToOrderServiceType() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check service type for correctness");
        changeDriverModalSteps.orderServiceTypeFieldShouldHaveValue(order.getKey("serviceType.name"));
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check that 'Service type' filter is preselected and blocked")
    public void checkServiceTypeFilterIsPreselectedAndBlocked() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check service type for correctness");
        changeDriverModalSteps.orderServiceTypeFieldShouldHaveValueAndDisabled(order.getKey("serviceType.name"));
    }

    @Test
    @Description("Change Driver pop-up and Drivers list:Check filter section at 'Change Order Driver' form")
    public void checkFilterSectionAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check labels at form");
        log("Check phone or email label is visible and has correct text");
        changeDriverModalSteps.userPhoneOrEmailLabelShouldHaveText();
        log("Check fistName label is visible and has correct text");
        changeDriverModalSteps.userFirstNameLabelShouldHaveText();
        log("Check LastName label is visible and has correct text");
        changeDriverModalSteps.userLastNameLabelShouldHaveText();
        log("Check driver status label is visible and has correct text");
        changeDriverModalSteps.driverStatusLabelShouldHaveText();
        log("Check city label is visible and has correct text");
        changeDriverModalSteps.cityLabelShouldHaveText();
        log("Check service type label is visible and has correct text");
        changeDriverModalSteps.serviceTypeLabelShouldHaveText();

        logStep(5, "Check fields at form");
        log("Check phone or email field is visible");
        changeDriverModalSteps.userPhoneOrEmailFieldShouldBeVisible();
        log("Check first name field is visible");
        changeDriverModalSteps.userFirstNameFieldShouldBeVisible();
        log("Check last name field is visible");
        changeDriverModalSteps.userLastNameFieldShouldBeVisible();
        log("Check city field is visible");
        changeDriverModalSteps.cityFieldShouldBeVisible();
        log("Check service type field is visible");
        changeDriverModalSteps.orderServiceTypeFieldShouldByVisible();
        log("Check driver status field is visible");
        changeDriverModalSteps.driverStatusFieldShouldByVisible();
    }

    @Test
    @Description("Finances section: Check that 'Details' button at 'Finances' section leads to Cost Calculation tab")
    public void checkDetailsButtonInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check the visibility of the button and press it");
        orderProfilePageSteps.sectionButtonClick(FINANCES, DETAILS);

        logStep(3, "Check current url");
        orderProfilePageSteps.checkPageUrl(orderId, COSTCALCULATION.getValue());
    }

    @Test
    @Description("Finances section: Check correct payment method displaying at 'Finances' section")
    public void checkPaymentMethodInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check payment method is correct");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PAYMENT, CREDIT_CARD);
    }

    @Test
    @Description("Finances section: Check that VAT line is displayed correct VAT value")
    public void checkCorrectVatValueInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check VAT values");
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, VAT, order.getKey("vat.outputVat"));
    }

    @Test
    @Description("Finances section: Check that prices which are not defined for now is displayed as “Not available yet” instead of zero")
    public void checkNotDefinedValuesInFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createSurprisedRegularOrder());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(3, "Check that total cost values is " + NOT_AVAILABLE_YET);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, NOT_AVAILABLE_YET);

        logStep(4, "Cancel order for friend and create regular order");
        useAPISteps.cancelOrder(order);

        createOrderAndGoToProfilePage(useAPISteps.createCompletedRegularOrderByFirstDriver());

        logStep(5, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(7, "Check that total cost values is " + PRICE_109_AND_40_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, order.getTotalCost());
    }

    @Test
    @Description("Finances section: Check correct order prices displaying at 'Finances' section (Basket, Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkFinancesSection() {
        createOrderAndGoToProfilePage(useAPISteps.createCompletedRegularOrderByFirstDriver());

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that basket values is " + PRICE_40_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, BASKET, PRICE_40_AND_00_SAR);

        logStep(3, "Check that service fee values is " + PRICE_0_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, SERVICE_FEE, order.getServiceFee());

        logStep(4, "Check that delivery fee values is " + PRICE_69_AND_40_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, DELIVERY_FEE, order.getDeliveryCost());

        logStep(5, "Check that total cost values is " + PRICE_109_AND_40_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, order.getTotalCost());

        logStep(6, "Check that total values is " + PRICE_109_AND_40_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL, order.getTotalCost());

        logStep(7, "Check that vat values is " + PRICE_9_AND_95_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, VAT, order.getVAT());

        logStep(8, "Check that payment values is " + CREDIT_CARD);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PAYMENT, CREDIT_CARD);
    }

    @Test
    @Description("Finances section: Check correct order prices displaying at 'Finances' section (Basket, Service Fee, Delivery Fee, Cancellation Fee, Discount, Total)")
    public void checkFinancesSectionForCanceledOrder() {
        order = useAPISteps.createRegularOrder(EMPTY_VALUE);
        useAPISteps.cancelOrder(order);
        createOrderAndGoToProfilePage(order);

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check that cancellation fee values is " + PRICE_0_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, CANCELLATION_FEE, PRICE_0_AND_00_SAR);

        logStep(3, "Check that total cost values is " + PRICE_0_AND_00_SAR);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, TOTAL_COST, PRICE_0_AND_00_SAR);

        logStep(4, "Check that payment values is " + CREDIT_CARD);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PAYMENT, CREDIT_CARD);
    }

    @Test
    @Description("Finances section: Check the correct 'Promo code' is displayed, if applied")
    public void checkPromoCodeInFinanceSection() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(testInitValues.promoSmokeDeliveryPromoCome()));

        logStep(1, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(2, "Check order for a promo code " + testInitValues.promoSmokeDeliveryPromoCome());
        orderProfilePageSteps.sectionParameterShouldBeVisible(FINANCES, PROMO_CODE);
        orderProfilePageSteps.sectionParameterShouldHaveText(FINANCES, PROMO_CODE, testInitValues.promoSmokeDeliveryPromoCome());

        logStep(3, "Cancel order with promo code and create regular order");
        useAPISteps.cancelOrder(order);
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(4, "Check order section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(FINANCES);

        logStep(5, "Check order does not has a promo code");
        orderProfilePageSteps.sectionParameterShouldNotBeVisible(FINANCES, PROMO_CODE);
    }

    @Test
    @Description("Contact section: Check 'Chat' button functionality at 'Customer' field of 'Contact' section ")
    public void checkCustomerChatBtnFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendedRegularOrder());

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, CUSTOMER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, CUSTOMERS);
    }

    @Test
    @Description("Contact section: Check 'Call' button functionality at 'Customer' field of 'Contact' section")
    public void checkCustomerCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createSuspendedRegularOrder());

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, CUSTOMER_PARAMETER, CALL);
    }

    @Test
    @Description("Contact section: Check 'Chat' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverChatButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));

        logStep(1, "Check that 'Chat' button exist and click");
        orderProfilePageSteps.sectionParameterButtonClick(CONTACT, DRIVER_PARAMETER, CHAT);

        logStep(2, "Should be displayed messenger page");
        orderHeaderPageSteps.breadcrumbMessengerPageShouldBeCorrectlyDisplayed(MESSENGER, DRIVERS);
    }

    @Test
    @Description("Contact section: Check 'Call' button functionality at 'Driver' field of 'Contact' section")
    public void checkDriverCallButtonFunctionality() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithDriverAccept(firstDriver));

        logStep(1, "Check that 'Call' button exist");
        orderProfilePageSteps.sectionParameterButtonShouldBeVisible(CONTACT, DRIVER_PARAMETER, CALL);
    }

    @Test
    @Description("Orders with required merchant approval: Check order canceled by Merchant")
    public void merchantCancellation() {
        order = useAPISteps.createRegularOrderWithMerchantAccept();
        merchant.cancelOrderById(order.getId());
        createOrderAndGoToProfilePage(order);

        logStep(1, "Check order status is CANCELLED");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, CANCELLED.getEnValue());

        logStep(2, "Check merchant status is CANCELLED");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, MERCHANT_STATUS, CANCELLED.getEnValue());
    }

    @Test
    @Description("Orders with required merchant approval: Check that order to friend from merchant receives 'WAIT_MERCHANT_APPROVAL' status after it was approved by friend")
    public void checkThatOrderToFriendFromMerchantReceivesStatusWaitMerchantApproval() {
        createOrderAndGoToProfilePage(useAPISteps.createSurprisedRegularOrder());

        logStep(1, "Check order status is NOT_CONFIRMED");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, NOT_CONFIRMED.getEnValue());

        logStep(2, "Accept order by Friend and refresh page");
        useAPISteps.acceptOrderBySecondCustomer(orderId);
        refresh();

        logStep(3, "Check status is WAIT_MERCHANT_APPROVAL");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, WAIT_MERCHANT_APPROVAL.getEnValue());
    }

    @Test
    @Description("Orders with required merchant approval: Check possibility to move order to support")
    public void checkPossibilityToMoveOrderToSupport() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Forward order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Check to_support line is visible");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, OrderProfilePage.TO_SUPPORT, YES);
    }

    @Test
    @Description("Orders with required merchant approval: Check possibility to cancel order")
    public void checkPossibilityToCancelOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createSurprisedRegularOrder());

        logStep(1, "Forward order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Click cancel button");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, CANCEL_lower);

        logStep(3, "Fill canceled reason and save changes");
        dialogModalSteps.cancelOrderTitleShouldBeVisible();
        dialogModalSteps.fillCancelReason("TEST");
        dialogModalSteps.saveBtnClick(CANCEL_ORDER);

        logStep(4, "Check order status is CANCELLED");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, CANCELLED.getEnValue());
    }

    @Test
    @Description("Orders with required merchant approval: Check that just created orders receive 'WAIT_MERCHANT_APPROVAL' status")
    public void checkJustCreatedOrderStatus() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Check order status is WAIT_MERCHANT_APPROVAL");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, WAIT_MERCHANT_APPROVAL.getEnValue());
    }

    @Test
    @Description("Orders with required merchant approval: Check that orders receive 'READY_TO_EXECUTE' status after order was approved by merchant user")
    public void checkOrderStatusAfterMerchantApproval() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrderWithMerchantAccept());

        logStep(1, "Check order status is READY_TO_EXECUTE");
        orderProfilePageSteps.sectionParameterShouldHaveText(ORDER, STATUS, READY_TO_EXECUTE.getEnValue());
    }

    @Test
    @Description("Orders with required merchant approval: Check possibility to restart order")
    public void checkPossibilityToRestartOrder() {
        createOrderAndGoToProfilePage(useAPISteps.createRegularOrder(EMPTY_VALUE));

        logStep(1, "Forward order to support");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, TO_SUPPORT);

        logStep(2, "Click restart button");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, RESTART_ORDER);
        sleep(1000);

        refresh();
        logStep(3, "Check that to_support parameter is not visible");
        orderProfilePageSteps.sectionParameterShouldNotBe(ORDER, OrderProfilePage.TO_SUPPORT);
    }
}