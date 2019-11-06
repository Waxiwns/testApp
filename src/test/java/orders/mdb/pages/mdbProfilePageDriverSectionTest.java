// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.mdb.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.orderPageTabs.OrderProfilePage;
import utils.BaseTest;
import utils.constants.Buttons;
import utils.objects.Order;

import static core.TestStepLogger.*;
import static pages.modalPages.ChangeDriverModal.DRIVER_STATUS;
import static pages.orderPageTabs.OrderProfilePage.*;
import static utils.constants.Buttons.CHANGE;
import static utils.constants.PageIdentifiers.PROFILE;
import static utils.constants.Statuses.*;

public class mdbProfilePageDriverSectionTest extends BaseTest {
    @BeforeEach
    public void goToProfilePage() {
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
    @Description("Change Driver pop-up: Check 'Show map'/'Show table' switcher functionality at 'Change Order Driver' form")
    public void checkShowMapShowTableSwitcherFunctionalityAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
        changeDriverModalSteps.serviceTypeFilterFieldValueShouldBeDelivery();
        changeDriverModalSteps.serviceTypeFilterShouldBeInactive();
        changeDriverModalSteps.applyFilterButtonShouldBeVisible();
        changeDriverModalSteps.driversTableHeaderCellsShouldBeVisible();

        logStep(7, "Check 'Show Map' button functionality");
        changeDriverModalSteps.showMapButtonClick();
        changeDriverModalSteps.modalTitleShouldBeDisplayed();
        changeDriverModalSteps.cityMapShouldBeVisible();
    }

    @Test
    @Description("Change Driver pop-up: Check 'cross' and 'Close' buttons on 'Change Order Driver' pop-up")
    public void checkCrossAndCloseButton() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Check that 'Map' tab is opened by default")
    public void checkIsOpenedByDefault() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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

        logStep(4, "Check that map is displaying");
        changeDriverModalSteps.cityMapShouldBeVisible();

        logStep(5, "Check 'Table' is not Opened");
        changeDriverModalSteps.allFilterFieldsShouldNotBeVisible();
    }

    @Test
    @Description("Check possibility to assign driver via map at 'Change Order Driver' form")
    public void checkPossibilityToAssignDriverViaMapAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithFirstDriverAccept());
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
    @Description("Check possibility to select driver via map at 'Change Order Driver' form")
    public void checkPossibilityToSelectDriverViaMapAtChangeOrderDriverForm() {
        firstDriver.updateLocation(locationKharkivPushkinskaProvulok4);
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());


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

        logStep(6, "Select driver via map");
        changeDriverModalSteps.firstCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsSelectButtonInInfoWindowClick(firstDriver.getFirstName());
        changeDriverModalSteps.modalTitleShouldNotBeDisplayed();
        notificationModalPageSteps.assignDriverMessageShouldBeDisplayed();

        logStep(7, "Check 'Driver' section and chosen driver");
        orderProfilePageSteps.sectionTitleShouldBeVisible(DRIVER);
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, NAME, firstDriver.getFullName());
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, PHONE, firstDriver.getPhone());
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, firstDriver.getCarBrand());
    }

    @Test
    @Description(" Check drivers displaying on the map of 'Change Order Driver' form")
    public void checkDriversDisplayingOnTheMapOfChangeOrderDriverForm() {
        firstDriver.updateLocation(locationKharkivPushkinskaProvulok4);
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());
        secondDriver.makeOnline(locationKharkivShevchenka146);

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

        logStep(6, "Check drivers displaying on the map");
        changeDriverModalSteps.firstOnlineCarShouldBeVisibleOnTheMap();
        changeDriverModalSteps.secondOnlineCarShouldBeVisibleOnTheMap();
        changeDriverModalSteps.firstCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsLinkInInfoWindowShouldBeVisible(firstDriver.getFirstName());
        changeDriverModalSteps.secondCarOnTheMapClick();
        changeDriverModalSteps.driverDetailsLinkInInfoWindowShouldBeVisible(secondDriver.getFirstName());
    }


    @Test
    @Description("Drivers list:Check that drivers with 'Ready' state are displayed in the table by default")
    public void checkDefaultDriverStatus() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(4, "Check that driver state is  'Ready' by default");
        changeDriverModalSteps.driverStatusFieldShouldHaveText(READY.getValue());

        logStep(5, "Check that drivers with 'Ready' state are displayed in the table by default");
        changeDriverModalSteps.selectDriverButtonShouldBeVisible();
    }

    @Test
    @Description("Drivers list:Check 'Driver status' filter functionality")
    public void checkDriverStatusFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check 'First name' filter functionality")
    public void checkFirstNameFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check 'Last name' filter functionality")
    public void checkLastNameFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());
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
    @Description("Drivers list:Check 'City' filter functionality")
    public void checkCityFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check 'Clear filter' button at 'Change Order Driver' form")
    public void checkClearFilterButton() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check 'Phone or email' filter functionality")
    public void checkPhoneOrEmailFilter() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check possibility to select driver via grid at 'Change Order Driver' form")
    public void checkPossibilityToSelectDriverViaGrid() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);
        changeDriverModalSteps.showTableButtonClick();

        logStep(3, "Write phone in the field and select Any status");
        changeDriverModalSteps.inputInTheUserPhoneOrEmailField(firstDriver.getPhone());
        changeDriverModalSteps.inputInTheDriverStatusField(ANY.getValue());

        logStep(4, "Apply filters");
        changeDriverModalSteps.applyFilterButtonClick();

        logStep(5, "Select active driver");
        changeDriverModalSteps.selectDriverButtonClick();

        logStep(6, "Check driver information displaying at 'Driver' section (Name, Phone, Car)");
        log("Check the driver name field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.NAME, firstDriver.getFullName());
        log("Check the driver phone field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, OrderProfilePage.PHONE, firstDriver.getPhone());
        log("Check the driver car field in English");
        orderProfilePageSteps.sectionParameterShouldHaveText(DRIVER, CAR, firstDriver.getCar());
    }

    @Test
    @Description("Drivers list:Check that Service Type is pre-selected according to Order Service Type")
    public void CheckThatServiceTypeIsPreSelectedAccordingToOrderServiceType() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Drivers list:Check that 'Service type' filter is preselected and blocked")
    public void checkServiceTypeFilterIsPreselectedAndBlocked() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
    @Description("Check that drivers with expired documents are not displayed on the map and Check that drivers with actual documents are displayed on the map")
    public void checkDriverWithDifferentDocument() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

        logStep(1, "Update driver location");
        firstDriver.updateLocation(locationKharkivPushkinska1);
        incorrectDriver.updateLocation(locationKharkivShevchenka146);

        logStep(2, "Set 'To support order state'");
        orderProfilePageSteps.sectionButtonClick(ACTIONS, Buttons.TO_SUPPORT);
        notificationModalPageSteps.updateOrderStatusMessageShouldBeDisplayed();

        logStep(3, "Check that change driver button is visible and click one");
        orderProfilePageSteps.sectionButtonClick(DRIVER, CHANGE);

        changeDriverModalSteps.firstOnlineCarShouldBeVisibleOnTheMap();
        changeDriverModalSteps.firstCarOnTheMapClick();

        changeDriverModalSteps.driverDetailsLinkInInfoWindowShouldNotBeVisible(incorrectDriver.getFirstName());
    }

    @Test
    @Description("Drivers list:Check filter section at 'Change Order Driver' form")
    public void checkFilterSectionAtChangeOrderDriverForm() {
        createOrderAndGoToProfilePage(useAPISteps.createMdbOrderWithKharkivPos());

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
}
