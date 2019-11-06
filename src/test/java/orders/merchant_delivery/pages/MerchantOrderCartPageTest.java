// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders.merchant_delivery.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.annotations.Bug;
import utils.constants.ProductOptions;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.*;
import static utils.constants.ApiConstants.EMPTY_VALUE;
import static utils.constants.Locale.AR;
import static utils.constants.Locale.EN;
import static utils.constants.ModalWindows.*;
import static utils.constants.OrderPrices.*;
import static utils.constants.PageIdentifiers.CART;
import static utils.constants.ProductOptions.*;

@Log4j
public class MerchantOrderCartPageTest extends BaseTest {

    @BeforeEach
    public void goToCartPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();

        logPreconditionStep(2, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logPreconditionStep(3, "Created order and go to '" + CART.getValue() + "' page");
        order = useAPISteps.createRegularOrder(EMPTY_VALUE);
        orderId = order.getId();
        switchToTheOrdersPage(orderId, CART.getValue());

        logPreconditionStep(4, "Check that " + CART.getValue() + " title appears");
        orderCartPageSteps.orderCartTitleShouldBeVisible();
        orderCartPageSteps.refreshPage();
    }

    @AfterEach
    public void cancelCreatedOrder() {
        logPostconditionStep(1, "Cancel created order");
        useAPISteps.cancelOrder(order);
    }

    @Test
    @Description("Check product info")
    public void checkProductInfoTestEn() {
        logStep(1, "Check product info");
        orderCartPageSteps.productPhotoPlaceShouldBeDisplayed();
        log("Check product name");
        orderCartPageSteps.productNameShouldBeAs(ProductOptions.PRODUCT_NAME.getValue());
        log("Check product price");
        orderCartPageSteps.checkProductPrice(REGULAR_DISCOUNTED_PRODUCT_PRICE.getValue());
        log("Check product product full price");
        orderCartPageSteps.checkProductFullPrice(REGULAR_DISCOUNTED_PRODUCT_PRICE.getValue());
        log("Check product total price");
        orderCartPageSteps.checkTotalOrderPrice(ORDER_TOTAL_UNCHANGED_PRICE.getValue());
        log("Add note to product");
        orderCartPageSteps.addNoteToProduct(ProductOptions.TEST_NOTE.getValue());

        logStep(2, "Save update");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed(EN);
        orderCartPageSteps.checkProductNote(ProductOptions.TEST_NOTE.getValue());
    }

    @Test
    @Description("Go to the product profile")
    public void checkPossibilityToProceedToProductProfilePageTestEn() {
        logStep(1, "Go to the product profile");
        orderCartPageSteps.proceedToTheProductProfilePage();
        productDetailsPageSteps.productTitleShouldHave(PRODUCT_NAME.getValue());
    }

    @Test
    @Description("Add specific option to product")
    public void addSpecificOptionToProductTestEn() {
        logStep(1, "Open product options popup");
        orderCartPageSteps.openProductOptionsPopup();

        logStep(2, "Add product options");
        orderCartPageSteps.selectAndSaveCertainProductOption(FIVE_SAR.getValue());
        orderCartPageSteps.checkSavedProductOptions(SAVED_PRODUCT_OPTION.getValue());
    }

    @Test
    @Description("Uncheck already added product option")
    public void uncheckAlreadyAddedProductOptionTestEn() {
        logStep(1, "Open product options popup");
        orderCartPageSteps.openProductOptionsPopup();

        logStep(2, "Add product options");
        orderCartPageSteps.selectAndSaveCertainProductOption(FIVE_SAR.getValue());

        logStep(3, "Open product options popup");
        orderCartPageSteps.openProductOptionsPopup();

        logStep(4, "UnSelect product options");
        orderCartPageSteps.unSelectAndSaveCertainProductOption();

        logStep(5, "Check product options");
        orderCartPageSteps.checkThatProductOptionListIsNotDisplayed();
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed(EN);
    }

    @Test
    @Description("Change products Quantity and save changes")
    public void changeProductsQuantityAndSaveChangesTestEn() {
        logStep(1, "Change product quantity");
        orderCartPageSteps.changeProductQuantity(PRODUCT_QUANTITY.getValue());
        sleep(1000);

        logStep(2, "Check changes");
        orderCartPageSteps.checkProductsFullPriceIsChanged(ORDER_TOTAL_CHANGED_PRICE.getValue());
        orderCartPageSteps.clickOrderSaveButton();

        logStep(3, "Check that success message appears");
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed(EN);
        orderCartPageSteps.checkProductsFullPriceIsChanged(ORDER_TOTAL_CHANGED_PRICE.getValue());
    }

    @Test
    @Description("Check possibility to delete order item")
    public void checkPossibilityToDeleteOrderItemTestEn() {
        logStep(1, "Add new product");
        useAPISteps.updateOrderCart(orderId);
        refresh();

        logStep(2, "Delete added product");
        orderCartPageSteps.clickDeleteItemButton(testInitValues.firstMerchantCheesePizzaId());

        logStep(3, "Click yes on modal windows");
        orderCartPageSteps.checkDeleteOrderLineDialogue(DELETE_ORDER_LINE_HEADER.getValue(), DELETE_ORDER_LINE_BODY.getValue());
        orderCartPageSteps.confirmOrderLineDeletion();
        orderCartPageSteps.deleteOrderLineHeaderShouldNotBeVisible();

        logStep(4, "Save changes");
        orderCartPageSteps.clickOrderSaveButton();
        notificationModalPageSteps.updateOrderLinesMessageShouldBeDisplayed(EN);


        logStep(5, "Refresh page and check changes");
        refresh();
        orderCartPageSteps.productNameShouldBeAs(DISCOUNT_PRODUCT_NAME.getValue());
    }

    @Test
    @Description("Check discard changes dialogue")
    public void checkDiscardChangesDialogueTestEn() {
        logStep(1, "Add new node");

        orderCartPageSteps.addNoteToProduct(TEST_NOTE.getValue());
        logStep(2, "Check that new node is displayed");
        orderCartPageSteps.switchToProductProfile();
        orderCartPageSteps.checkDiscardChangesDialogue(DISCARD_CHANGES_HEADER.getValue(), DISCARD_CHANGES_BODY.getValue());
    }

    @Test
    @Description("Discard changes through dialogue")
    public void discardChangesThroughDialogueTestEn() {
        logStep(1, "Add new node");
        orderCartPageSteps.addNoteToProduct(TEST_NOTE.getValue());
        orderCartPageSteps.switchToProductProfile();

        logStep(2, "Check that discard was saved");
        discardChangesModalSteps.buttonYesClick();
        switchToTheOrdersPage(orderId, CART.getValue());
        orderCartPageSteps.checkProductNote(EMPTY_VALUE);
    }

    @Test
    @Description("Open add items to cart via add items button")
    public void openAddItemsToCartViaAddItemsButtonTestEn() {
        logStep(1, "Check add items window");
        orderCartPageSteps.openAddToCartWindow();
        orderCartPageSteps.checkAddItemsWindow(ProductOptions.PRODUCT_NAME.getValue(), PRODUCT_DESCRIPTION.getValue(),
                REGULAR_DISCOUNTED_PRODUCT_PRICE.getValue(), REGULAR_FULL_PRODUCT_PRICE.getValue(),
                PRODUCT_CATEGORY_NAME.getValue(), PRODUCT_GROUP_NAME.getValue());
    }

    @Test
    @Description("Open and close add items popup to cart via add items button")
    public void openAndCloseAddItemsPopupToCartViaAddItemsButtonTestEn() {
        logStep(1, "Check possibility close 'add to cart' window");
        orderCartPageSteps.openAddToCartWindow();
        orderCartPageSteps.closeAddToCartWindow();
    }

    @Bug
    @Issue("CI-16")
    @Test
    @Description("Check the translation English/Arabic")
    public void checkLocalisation() {
        logStep(1, "Check english translation");
        orderCartPageSteps.checkLocalisationOnThePage(EN);
        orderCartPageSteps.checkLocalisationOnTheSelectProductsModal(EN, merchant, testInitValues.firstMerchantCheesePizzaId(), testInitValues.firstMerchantDiscountedPizzaId());

        logStep(2, "Change language to arabic");
        headerPageSteps.changeLanguage();

        logStep(3, "Check arabic translation");
        orderCartPageSteps.checkLocalisationOnThePage(AR);
        orderCartPageSteps.checkLocalisationOnTheSelectProductsModal(EN, merchant, testInitValues.firstMerchantCheesePizzaId(), testInitValues.firstMerchantDiscountedPizzaId());

        logStep(4, "Change language to english");
        headerPageSteps.changeLanguage();

        logStep(5, "Check english translation is correct after arabic translation");
        orderCartPageSteps.checkLocalisationOnThePage(EN);
        orderCartPageSteps.checkLocalisationOnTheSelectProductsModal(EN, merchant, testInitValues.firstMerchantCheesePizzaId(), testInitValues.firstMerchantDiscountedPizzaId());
    }
}
