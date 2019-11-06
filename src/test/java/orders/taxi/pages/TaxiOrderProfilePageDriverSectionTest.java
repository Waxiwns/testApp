// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.taxi.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.constants.Buttons;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static pages.modalPages.ChangeDriverModal.CITY;
import static pages.modalPages.ChangeDriverModal.*;
import static pages.orderPageTabs.OrderProfilePage.NAME;
import static pages.orderPageTabs.OrderProfilePage.PHONE;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.ApiConstants.PICKUP_ADDRESS_GEO_POINT_LAT;
import static utils.constants.ApiConstants.PICKUP_ADDRESS_GEO_POINT_LON;
import static utils.constants.Buttons.SELECT;
import static utils.constants.Buttons.*;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.Statuses.*;

public class TaxiOrderProfilePageDriverSectionTest extends BaseTest {

    @BeforeEach
    public void goToProfilePage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Check that Dashboard page title appears");
        dashboardPageSteps.dashboardTitleShouldBeVisible();

        logPreconditionStep(4, "Create taxi new order");
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
    @Description("Taxi order 'Driver' section: Check driver information displaying at 'Driver' section")
    public void checkDriverInformationDisplayingAtDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check driver section parameters");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, secondDriver.getFullName());

        logStep(3, "Check driver section parameters");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, secondDriver.getPhone());

        logStep(4, "Check driver section parameters");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, secondDriver.getCar());

        logStep(5, "Check driver section parameters");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, FLEET, "");
    }

    @Test
    @Description("Taxi order 'Driver' section: Check that 'Details' button at 'Driver' section leads to Driver Profile page")
    public void checkThatDetailsButtonAtDriverSectionLeadsToDriverProfilePage() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check 'Details' button");
        orderProfilePageSteps.sectionButtonClick(DRIVER, DETAILS);
        driverDetailsSteps.driverFirstNameEnShouldHaveText(secondDriver.getFirstName());
        driverDetailsSteps.driverLastNameEnShouldHaveText(secondDriver.getLastName());
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'Analyze' button functionality at 'Driver' section")
    public void checkAnalyzeButtonFunctionalityAtDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check 'Analyze' button");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);
        driverAnalysePageSteps.titleShouldBe();
    }

    @Test
    @Description("Taxi order 'Driver' section: Check the 'Driver analyse' page")
    public void checkTheDriverAnalysePage() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));

        String driverId = testInitValues.secondDriverId();

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check 'Analyze' button");
        orderProfilePageSteps.sectionButtonClick(DRIVER, ANALYZE);
        driverAnalysePageSteps.titleShouldBe();

        logStep(3, "Check 'Driver analyse' page");
        driverAnalysePageSteps.idInputFieldShouldHaveDriverId(driverId);
        driverAnalysePageSteps.getButtonClick();
        driverAnalysePageSteps.resultBlockShouldHaveText(driverId);
    }

    @Test
    @Description("Taxi order 'Driver' section: Check the 'Change' button functionality in the 'Driver' section")
    public void checkTheChangeButtonFunctionalityInTheDriverSection() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'Show map'/'Show table' switcher functionality at 'Change Order Driver' form")
    public void checkShowMapShowTableSwitcherFunctionalityAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.cityMapShouldBeVisible();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();

        logStep(6, "Check filter fields and buttons");
        changeDriverModalSteps.allFilterFieldsShouldBeVisible();
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, READY.getValue());
        changeDriverModalSteps.serviceTypeFilterFieldValueShouldBeTaxi();
        changeDriverModalSteps.serviceTypeFilterShouldBeInactive();
        changeDriverModalSteps.applyFilterButtonShouldBeVisible();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();

        logStep(7, "Check 'Show Map' button functionality");
        changeDriverModalSteps.showMapButtonClick();
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.cityMapShouldBeVisible();
    }

    @Test
    @Description("Taxi order 'Driver' section: Check drivers displaying on the map of 'Change Order Driver' form")
    public void checkDriversDisplayingOnTheMapOfChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndSecondDriverReadyAtLocation());
        firstDriver.makeOnline(locationKharkivShevchenka146);

        logStep(1, "Check driver section");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, NA);
        sleep(2000);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);
        sleep(2000);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        sleep(2000);

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Map'");
        changeDriverModalSteps.cityMapShouldBeVisible();
        changeDriverModalSteps.cityMapGoogleLinkShouldBeVisible(order.getKey(PICKUP_ADDRESS_GEO_POINT_LAT), order.getKey(PICKUP_ADDRESS_GEO_POINT_LON));

        logStep(6, "Check drivers displaying on the map");
        changeDriverModalSteps.firstOnlineCarShouldBeVisibleOnTheMap();
        changeDriverModalSteps.secondOnlineCarShouldBeVisibleOnTheMap();
        changeDriverModalSteps.firstCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsLinkInInfoWindowShouldBeVisible(secondDriver.getFirstName());
        changeDriverModalSteps.secondCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsLinkInInfoWindowShouldBeVisible(firstDriver.getFirstName());
    }

    @Test
    @Description("Taxi order 'Driver' section: Check possibility to select driver via map at 'Change Order Driver' form")
    public void checkPossibilityToSelectDriverViaMapAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndSecondDriverReadyAtLocation());

        logStep(1, "Check driver section");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, NA);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Map'");
        changeDriverModalSteps.cityMapShouldBeVisible();
        changeDriverModalSteps.cityMapGoogleLinkShouldBeVisible(order.getKey(PICKUP_ADDRESS_GEO_POINT_LAT), order.getKey(PICKUP_ADDRESS_GEO_POINT_LON));

        logStep(6, "Select driver via map");
        changeDriverModalSteps.firstCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsSelectButtonInInfoWindowClick(secondDriver.getFirstName());
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();
        notificationModalPageSteps.assignDriverMessageShouldBeDisplayed();

        logStep(7, "Check 'Driver' section and chosen driver");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, secondDriver.getFullName());
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, secondDriver.getPhone());
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, secondDriver.getCarBrand());
    }

    @Test
    @Description("Taxi order 'Driver' section: Check possibility to assign driver via map at 'Change Order Driver' form")
    public void checkPossibilityToAssignDriverViaMapAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(firstDriver));
        secondDriver.checkActiveOrderAndCancelIt();
        secondDriver.updateLocation(locationKharkivPushkinska1);

        String name = firstDriver.getFullName();
        String phone = firstDriver.getPhone();
        String car = firstDriver.getCarBrand();

        logStep(1, "Check driver section");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, name);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, phone);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        name = secondDriver.getFullName();
        phone = secondDriver.getPhone();
        car = secondDriver.getCarBrand();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Map'");
        changeDriverModalSteps.cityMapShouldBeVisible();
        changeDriverModalSteps.cityMapGoogleLinkShouldBeVisible(order.getKey(PICKUP_ADDRESS_GEO_POINT_LAT), order.getKey(PICKUP_ADDRESS_GEO_POINT_LON));

        logStep(6, "Select driver via map");
        changeDriverModalSteps.firstCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsSelectButtonInInfoWindowClick(secondDriver.getFirstName());

        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();
        notificationModalPageSteps.assignDriverMessageShouldBeDisplayed();

        logStep(7, "Check 'Driver' section and chosen driver");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, name);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, phone);

        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);
    }

    @Test
    @Description("Taxi order 'Driver' section: Check possibility to select driver via grid at 'Change Order Driver' form")
    public void checkPossibilityToSelectDriverViaGridAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndSecondDriverReadyAtLocation());
        secondDriver.checkActiveOrderAndCancelIt();
        secondDriver.updateLocation(locationKharkivPushkinska100);

        String name = secondDriver.getFullName();
        String phone = secondDriver.getPhone();
        String car = secondDriver.getCarBrand();

        logStep(1, "Check driver section");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, NA);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, NA);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();

        logStep(6, "Check 'Driver status' filter 'Ready' as default");
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, READY.getValue());

        logStep(7, "Check driver table line values");
        changeDriverModalSteps.driversTableLineByIdCellsShouldHaveText(secondDriver.getId(), name, phone, car, secondDriver.getStatus(), SELECT.getValue());

        logStep(8, "Select driver");
        changeDriverModalSteps.driversTableSelectButtonByIdClick(secondDriver.getId());
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();
        sleep(2000);
        notificationModalPageSteps.assignDriverMessageShouldBeDisplayed();

        logStep(9, "Check driver section");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, name);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, phone);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'cross' and 'Close' buttons at 'Change Order Driver' form")
    public void checkCrossAndCloseButtonsAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndSecondDriverReadyAtLocation());

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
    @Description("Taxi order 'Driver' section: Check 'Order_execution' filter for 'Driver status' at 'Change Order Driver' form")
    public void checkOrderExecutionFilterForDriverStatusAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));

        String driverId = secondDriver.getId();
        String name = secondDriver.getFullName();
        String phone = secondDriver.getPhone();
        String car = secondDriver.getCarBrand();

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, name);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, phone);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();

        logStep(6, "Check filter fields and buttons");
        changeDriverModalSteps.filterFieldSetValue(DRIVER_STATUS, ORDER_EXECUTION.getValue());
        changeDriverModalSteps.applyFilterButtonClick();
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, ORDER_EXECUTION.getValue());
        changeDriverModalSteps.driversTableLineByIdCellsShouldHaveText(driverId, name, phone, car, ORDER_EXECUTION.getEnValue().replace("_", " "));
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'Offline' filter for 'Driver status' at 'Change Order Driver' form")
    public void checkOfflineFilterForDriverStatusAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrder());
        secondDriver.checkActiveOrderAndCancelIt();
        secondDriver.waitForStatus(OFFLINE);

        String driverId = secondDriver.getId();
        String name = secondDriver.getFullName();
        String phone = secondDriver.getPhone();
        String car = secondDriver.getCarBrand();

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();

        logStep(6, "Check filter fields and buttons");
        changeDriverModalSteps.filterFieldSetValue(PHONE_OR_EMAIL, phone);
        changeDriverModalSteps.filterFieldSetValue(DRIVER_STATUS, OFFLINE.getValue());
        changeDriverModalSteps.applyFilterButtonClick();
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, OFFLINE.getValue());
        changeDriverModalSteps.driversTableLineByIdCellsShouldHaveText(driverId, name, phone, car, OFFLINE.getEnValue());
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'Order_Offline' filter for 'Driver status' at 'Change Order Driver' form")
    public void checkOrderOfflineFilterForDriverStatusAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndAcceptByDriver(secondDriver));
        secondDriver.waitForStatus(ORDER_OFFLINE);

        String driverId = secondDriver.getId();
        String name = secondDriver.getFullName();
        String phone = secondDriver.getPhone();
        String car = secondDriver.getCarBrand();

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, name);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, phone);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, car);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();

        logStep(6, "Check filter fields and buttons");
        changeDriverModalSteps.filterFieldSetValue(PHONE_OR_EMAIL, phone);
        changeDriverModalSteps.filterFieldSetValue(DRIVER_STATUS, ORDER_OFFLINE.getValue());
        changeDriverModalSteps.applyFilterButtonClick();
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, ORDER_OFFLINE.getValue());
        changeDriverModalSteps.driversTableLineByIdCellsShouldHaveText(driverId, name, phone, car, ORDER_OFFLINE.getEnValue().replace("_", " "));
    }

    @Test
    @Description("Taxi order 'Driver' section: Check 'Clear filter' button at 'Change Order Driver' form")
    public void checkClearFilterButtonAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createTaxiOrderAndSecondDriverReadyAtLocation());

        String driverId = secondDriver.getId();
        String fistName = secondDriver.getFirstName();
        String lastName = secondDriver.getLastName();
        String name = secondDriver.getFullName();
        String phone = secondDriver.getPhone();
        String car = secondDriver.getCarBrand();

        logStep(1, "Check driver section title");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);

        logStep(2, "Check disabled 'Change' button");
        orderProfilePageSteps.sectionButtonDisabled(DRIVER, CHANGE);

        logStep(3, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(4, "Check 'Change' button functionality");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.modalTitleShouldBeDisplayed();

        logStep(5, "Check 'Show Table' button functionality");
        changeDriverModalSteps.showTableButtonClick();

        logStep(6, "Check filter fields and buttons");
        changeDriverModalSteps.allFilterFieldsShouldBeVisible();
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, READY.getValue());
        changeDriverModalSteps.serviceTypeFilterFieldValueShouldBeTaxi();
        changeDriverModalSteps.serviceTypeFilterShouldBeInactive();
        changeDriverModalSteps.applyFilterButtonShouldBeVisible();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();
        sleep(2000);

        logStep(7, "Fill filters values");
        changeDriverModalSteps.filterFieldSetValue(PHONE_OR_EMAIL, phone);
        changeDriverModalSteps.filterFieldSetValue(FIRST_NAME, fistName);
        changeDriverModalSteps.filterFieldSetValue(LAST_NAME, lastName);
        changeDriverModalSteps.filterFieldSetValue(LAST_NAME, lastName);
        changeDriverModalSteps.filterFieldSetValue(CITY, testInitValues.areaKharkiv());

        logStep(8, "Check apply filter");
        changeDriverModalSteps.driversTableLineByIdCellsShouldHaveText(driverId, name, phone, car, READY.getEnValue());
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, READY.getValue());
        sleep(2000);

        logStep(9, "Check 'Clear filter' button ");
        changeDriverModalSteps.filterCancelButtonClick();
        changeDriverModalSteps.filterFieldValueShouldBe(PHONE_OR_EMAIL, "");
        changeDriverModalSteps.filterFieldValueShouldBe(FIRST_NAME, "");
        changeDriverModalSteps.filterFieldValueShouldBe(LAST_NAME, "");
        changeDriverModalSteps.filterFieldValueShouldBe(LAST_NAME, "");
        changeDriverModalSteps.filterFieldValueShouldBe(CITY, "");
        changeDriverModalSteps.filterFieldValueShouldBe(DRIVER_STATUS, READY.getValue());
        changeDriverModalSteps.serviceTypeFilterFieldValueShouldBeTaxi();
        changeDriverModalSteps.serviceTypeFilterShouldBeInactive();
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}