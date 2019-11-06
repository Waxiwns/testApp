// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.courier.pages;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.objects.Order;

import static com.codeborne.selenide.Selenide.refresh;
import static core.TestStepLogger.*;
import static pages.orderPageTabs.OrderCartPage.PIZZA;
import static pages.orderPageTabs.OrderCartPage.PIZZA_IMAGE;
import static utils.constants.Locale.AR;
import static utils.constants.Locale.EN;
import static utils.constants.ModalWindows.*;
import static utils.constants.PageIdentifiers.CART;
import static utils.constants.PageIdentifiers.PROFILE;

public class CourierOrderCartPageTest extends BaseTest {

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

    public void createOrderAndGoToCartPage(Order order) {
        this.order = order;
        orderId = order.getId();
        orderNumber = order.getNumber();

        logPreconditionStep(5, "Go to 'Cart' tab");
        switchToTheOrdersPage(orderId, CART.getValue());

        logPreconditionStep(6, "Check order page title");
        orderHeaderPageSteps.orderTitleShouldHaveNumber(orderNumber);
    }

    @Test
    @Description("Cart page: Check breadcrumbs")
    public void checkBreadcrumbsOnCartPage() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check breadcrumbs on 'Cart' tab");
        orderHeaderPageSteps.breadcrumbsShouldBeDisplayedOnTab(orderId, CART);
    }

    @Test
    @Description("Cart page: Check that order cannot be saved without any custom item")
    public void checkThatOrderCannotBeSavedWithoutAnyCustomItem() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Delete product");
        orderCartPageSteps.clickDeleteItemButton();
        orderCartPageSteps.checkDeleteOrderLineDialogue(DELETE_ORDER_LINE_HEADER.getValue(), DELETE_ORDER_LINE_BODY.getValue());
        orderCartPageSteps.confirmOrderLineDeletion();
        orderCartPageSteps.clickOrderSaveButton();

        logStep(2, "Error message should be displayed");
        notificationModalPageSteps.deliveryOrderHaveNoItemMessageShouldBeDisplayed();
    }

    @Test
    @Description("Cart page: Check possibility to add one or several custom items")
    public void checkPossibilityToAddOneOrSeveralCustomItems() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Click on 'Add item' button");
        orderCartPageSteps.clickOnAddItemButton();

        logStep(2, "Add product to cart");
        orderCartPageSteps.addNewProduct(PIZZA);
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
    }

    @Test
    @Description("Cart page: Check 'Name' field functionality")
    public void checkNameFieldFunctionality() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Change 'Name' field");
        orderCartPageSteps.changeNameField(PIZZA);

        logStep(2, "Check that value is changed");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(3, "Check that name field is changed");
        orderCartPageSteps.checkThatNameFieldIsChange(PIZZA);
    }

    @Test
    @Description("Cart page: Check possibility to add picture to the custom item")
    public void checkPossibilityToAddPictureToTheCustomItem() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Add picture");
        orderCartPageSteps.changePictureField(PIZZA_IMAGE);

        logStep(2, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(3, "Check that image is displayed");
        orderCartPageSteps.checkThatImageIsUpload();
    }

    @Test
    @Description("Cart page: Check possibility to reupload product picture")
    public void checkPossibilityToReuploadProductPicture() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Add picture");
        orderCartPageSteps.changePictureField(PIZZA_IMAGE);

        logStep(2, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(3, "Check that image is displayed");
        orderCartPageSteps.checkThatImageIsUpload();

        logStep(4, "Delete product item picture");
        orderCartPageSteps.deleteProductItemImage();

        logStep(5, "Upload new picture");
        orderCartPageSteps.changePictureField(PIZZA_IMAGE);

        logStep(6, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(7, "Check that image is displayed");
        orderCartPageSteps.checkThatImageIsUpload();
    }

    @Test
    @Description("Cart page: Check possibility to delete product picture")
    public void checkPossibilityToDeleteProductPicture() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Add picture");
        orderCartPageSteps.changePictureField(PIZZA_IMAGE);

        logStep(2, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(3, "Check that image is displayed");
        orderCartPageSteps.checkThatImageIsUpload();

        logStep(4, "Delete product item picture");
        orderCartPageSteps.deleteProductItemImage();
    }

    @Test
    @Description("Cart page: Check possibility to delete custom item")
    public void checkPossibilityToDeleteCustomItem() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Delete custom item");
        orderCartPageSteps.productNameShouldBeDisplayed();
        orderCartPageSteps.clickOnDeleteBtn();
        orderCartPageSteps.checkDeleteOrderLineDialogue(DELETE_ORDER_LINE_HEADER.getValue(), DELETE_ORDER_LINE_BODY.getValue());
        orderCartPageSteps.confirmOrderLineDeletion();

        logStep(2, "Check that custom item is deleted");
        orderCartPageSteps.noItemsShouldBeDisplayed();
    }

    @Test
    @Description("Cart page: Check the 'Discard Changes' dialog window if some fields are filled in")
    public void checkTheDiscardChangesDialogWindow() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Click on 'Add item' button");
        orderCartPageSteps.clickOnAddItemButton();

        logStep(2, "Add product to cart");
        orderCartPageSteps.addNewProduct(PIZZA);

        logStep(3, "Go to 'Profile' tab");
        switchToTheOrdersPage(orderId, PROFILE.getValue());

        logStep(4, "Check the 'Discard Changes' dialog window is appear");
        orderCartPageSteps.checkDiscardChangesDialogue(DISCARD_CHANGES_HEADER.getValue(), DISCARD_CHANGES_BODY.getValue());
    }

    @Test
    @Description("Cart page: Check possibility to Save changes")
    public void checkPossibilityToSaveChanges() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Click on 'Add item' button");
        orderCartPageSteps.clickOnAddItemButton();

        logStep(2, "Add product to cart");
        orderCartPageSteps.addNewProduct(PIZZA);
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed();
        refresh();

        logStep(3, "Check that new product was added to cart");
        orderCartPageSteps.secondProductNameShouldBeDisplayed();
    }

    @Test
    @Description("Cart page: Check possibility to Cancel changes")
    public void checkPossibilityToCancelChanges() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Click on 'Add item' button");
        orderCartPageSteps.clickOnAddItemButton();

        logStep(2, "Input new product name");
        orderCartPageSteps.addNewProduct(PIZZA);
        orderCartPageSteps.clickOnCancelBtn();

        logStep(3, "Check that new product wasn't added to cart");
        orderCartPageSteps.secondProductNameShouldNotBeDisplayed();
    }

    @Test
    @Description("Cart page: Check the translation English/Arabic")
    public void checkTheTranslationEnglishToArabic() {
        createOrderAndGoToCartPage(useAPISteps.createCourierCCOrderByFirstCustomer());

        logStep(1, "Check english translation");
        orderCartPageSteps.checkLocalisationOnThePageCourierOrder(EN);

        logStep(2, "Change language to arabic");
        headerPageSteps.changeLanguage();

        logStep(3, "Check arabic translation");
        orderCartPageSteps.checkLocalisationOnThePageCourierOrder(AR);

        logStep(4, "Change language to english");
        headerPageSteps.changeLanguage();

        logStep(5, "Check english translation is correct after arabic translation");
        orderCartPageSteps.checkLocalisationOnThePageCourierOrder(EN);
    }

    @AfterEach
    public void cancelOrder() {
        logPostconditionStep(1, "Cancel order");
        useAPISteps.cancelOrder(order);
    }
}
