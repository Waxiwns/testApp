// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.orderPageTabsSteps;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.orderPageTabs.OrderCartPage;
import utils.BaseStep;
import utils.users.Merchant;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.orderPageTabs.OrderCartPage.*;
import static utils.constants.ApiConstants.ID;
import static utils.constants.ApiConstants.NAME;
import static utils.constants.Locale.EN;

public class OrderCartPageSteps extends BaseStep {
    private OrderCartPage orderCartPage = new OrderCartPage();

    private String noImageFileName = "jswyuxzcnzoxate8qnvv.png";
    private String productImageName = "itpuhm4yrb4vsvxact53.jpg";

    @Step
    public SelenideElement orderCartTitleShouldBeVisible() {
        return orderCartPage.orderCartHeaderText.shouldBe(visible);
    }

    @Step
    public void productPhotoPlaceShouldBeDisplayed() {
        orderCartPage.productImage.shouldBe(visible);
    }

    @Step
    public void productsNoImageMockShouldBeDisplayed() {
        String imgAttribute = orderCartPage.productImage.getAttribute("src");
        assertTrue(imgAttribute.contains(noImageFileName));
    }

    @Step
    public void productNameShouldBeAs(String productName) {
        orderCartPage.productName.shouldHave(text(productName));
    }



    @Step
    public void proceedToTheProductProfilePage() {
        orderCartPage.productName.click();
    }

    @Step
    public void openProductOptionsPopup() {
        orderCartPage.optionsPopupBtn.shouldBe(visible);
        orderCartPage.optionsPopupBtn.click();
    }

    @Step
    public void selectAndSaveCertainProductOption(String text) {
        orderCartPage.doubleCheeseCheckbox.click();
        orderCartPage.optionsPopupSaveBtn.click();
        orderCartPage.selectedProductOptions.shouldHave(text(text));
    }

    @Step
    public void unSelectAndSaveCertainProductOption() {
        orderCartPage.doubleCheeseCheckbox.click();
        orderCartPage.optionsPopupSaveBtn.click();
    }

    @Step
    public void checkSavedProductOptions(String text) {
        orderCartPage.selectedProductOptionsInTable.shouldHave(text(text));
    }

    @Step
    public void checkThatProductOptionListIsNotDisplayed() {
        assertTrue(orderCartPage.selectedOptionsList.isEmpty());
    }

    @Step
    public void changeProductQuantity(String quantity) {
        orderCartPage.quantityInputField.clear();
        orderCartPage.quantityInputField.shouldBe(visible).sendKeys(quantity);
    }

    @Step
    public void clickOrderSaveButton() {
        orderCartPage.saveOrderChanges.click();
    }

    @Step
    public void checkProductsFullPriceIsChanged(String text) {
        orderCartPage.productFullPrice.shouldHave(text(text));
    }

    @Step
    public void addNoteToProduct(String note) {
        sleep(1000);    // wait for download
        orderCartPage.noteTextArea.sendKeys(note);
        sleep(1000);    // wait for download
    }

    @Step
    public void checkProductPrice(String price) {
        orderCartPage.productPrice.shouldBe(visible);
        orderCartPage.productPrice.shouldHave(text(price));
    }

    @Step
    public void checkProductFullPrice(String price) {
        orderCartPage.productFullPrice.shouldBe(visible);
        orderCartPage.productFullPrice.shouldHave(text(price));

    }

    @Step
    public void checkTotalOrderPrice(String price) {
        orderCartPage.productTotalPrice.shouldBe(visible);
        orderCartPage.productTotalPrice.shouldHave(text(price));
    }

    @Step
    public void clickDeleteItemButton() {
        orderCartPage.deleteProductBtn.click();
    }

    @Step
    public void clickDeleteItemButton(String productId) {
        orderCartPage.deleteProductBtn(productId).click();
    }

    @Step
    public void checkDeleteOrderLineDialogue(String headerText, String bodyText) {
        orderCartPage.deleteOrderLineDialogue.shouldBe(visible);
        orderCartPage.deleteOrderLineHeader.shouldHave(text(headerText));
        orderCartPage.deleteOrderLineBody.shouldHave(text(bodyText));
        orderCartPage.deleteOrderLineYesBtn.shouldBe(visible);
        orderCartPage.deleteOrderLineNoBtn.shouldBe(visible);
    }

    @Step
    public void deleteOrderLineHeaderShouldNotBeVisible() {
        orderCartPage.deleteOrderLineHeader.shouldNotBe(visible);
    }


    @Step
    public void confirmOrderLineDeletion() {
        orderCartPage.deleteOrderLineYesBtn.shouldBe(visible).click();
    }

    @Step
    public void switchToProductProfile() {
        orderCartPage.productName.click();
    }

    @Step
    public void checkDiscardChangesDialogue(String headerText, String bodyText) {
        orderCartPage.discardChangesDialogue.shouldBe(visible);
        orderCartPage.discardChangesDialogueHeader.shouldHave(text(headerText));
        orderCartPage.discardChangesDialogueBody.shouldHave(text(bodyText));
        orderCartPage.discardChangesDialogueYesBtn.shouldBe(visible);
        orderCartPage.discardChangesDialogueNoBtn.shouldBe(visible);
    }

    @Step
    public void checkProductNote(String text) {
        orderCartPage.noteTextArea.shouldHave(value(text));
    }

    @Step
    public void openAddToCartWindow() {
        orderCartPage.addCustomProductItemButton.shouldBe(visible);
        orderCartPage.addRegularProductItemButton.click();
    }

    @Step
    public void clickOnAddItemButton() {
        orderCartPage.addItemButton.shouldBe(visible);
        orderCartPage.addItemButton.click();
    }

    @Step
    public void productNameShouldBeDisplayed() {
        orderCartPage.productNameField.shouldBe(visible);
    }

    @Step
    public void changeNameField(String productName) {
        productNameShouldBeDisplayed();
        orderCartPage.productNameField.clear();
        orderCartPage.emptyAddItemField.setValue(productName);
    }

    @Step
    public void checkThatNameFieldIsChange(String nameField) {
        orderCartPage.productNameField.shouldBe(value(nameField));
    }

    @Step
    public void addNewProduct(String productName) {
        orderCartPage.emptyAddItemField.shouldBe(visible);
        orderCartPage.emptyAddItemField.setValue(productName);
    }

    @Step
    public void clickOnCancelBtn() {
        orderCartPage.cancelBtn.shouldBe(visible);
        orderCartPage.cancelBtn.click();
    }

    @Step
    public void checkAddItemsWindow(String productName, String productDescription, String discountedPrice, String prodPrice, String categoryName, String groupName) {
        orderCartPage.selectProductPopup.shouldBe(visible);
        orderCartPage.productImageOnPopup.shouldBe(visible);
        assertTrue(orderCartPage.productImageOnPopup.getAttribute("src").contains(productImageName));
        assertTrue(orderCartPage.productWithoutPhotoOnPopup.getAttribute("src").contains(noImageFileName));
        orderCartPage.firstInTheListProductName.shouldHave(text(productName));
        orderCartPage.firstInTheListProductDescription.shouldHave(text(productDescription));
        orderCartPage.productDiscountInTheList.shouldHave(text(discountedPrice));
        orderCartPage.productPriceInTheList.shouldHave(text(prodPrice));
        orderCartPage.productCategoryInTheList.shouldHave(text(categoryName));
        orderCartPage.productGroupsInTheList.shouldHave(text(groupName));
        orderCartPage.productPOSAvailabilityInTheList.shouldNotBe(empty);
        orderCartPage.addToCartButton.shouldBe(visible);
    }

    @Step
    public void closeAddToCartWindow() {
        orderCartPage.closeSelectProductsPopup.click();
        orderCartPage.closeSelectProductsPopup.should(disappear);
    }

    @Step
    public void deleteFirstItemFromTheCart() {
        orderCartPage.deleteProductBtn.shouldBe(visible).click();
        orderCartPage.deleteOrderLineDialogue.shouldBe(visible);
        orderCartPage.deleteOrderLineYesBtn.shouldBe(visible).click();
    }

    @Step
    public void checkLocalisationOnTheCatalogItemsTable(String locale) {
        orderCartPage.catalogItemsTitle.shouldHave(text(locale.equals(EN) ? CATALOG_ITEM_TITLE_EN : CATALOG_ITEM_TITLE_AR));

        orderCartPage.getTitleFromCatalogItemTable(1).shouldHave(text(locale.equals(EN) ? IMAGE_EN : IMAGE_AR));
        orderCartPage.getTitleFromCatalogItemTable(2).shouldHave(text(locale.equals(EN) ? NAME_EN : NAME_AR));
        orderCartPage.getTitleFromCatalogItemTable(3).shouldHave(text(locale.equals(EN) ? PRODUCT_OPTIONS_EN : PRODUCT_OPTIONS_AR));
        orderCartPage.getTitleFromCatalogItemTable(4).shouldHave(text(locale.equals(EN) ? QUANTITY_EN : QUANTITY_AR));
        orderCartPage.getTitleFromCatalogItemTable(5).shouldHave(text(locale.equals(EN) ? PRICE_SAR_EN : PRICE_SAR_AR));
        orderCartPage.getTitleFromCatalogItemTable(6).shouldHave(text(locale.equals(EN) ? FULL_PRICE_SAR_EN : FULL_PRICE_SAR_AR));
        orderCartPage.getTitleFromCatalogItemTable(7).shouldHave(text(locale.equals(EN) ? NOTE_EN : NOTE_AR));
        orderCartPage.getTitleFromCatalogItemTable(8).shouldHave(text(locale.equals(EN) ? DELETE_EN : DELETE_AR));
        orderCartPage.totalLabel.shouldHave(text(locale.equals(EN) ? TOTAL_EN : TOTAL_AR));
    }

    @Step
    public void checkLocalisationOnTheCustomItemsTableCourierOrder(String locale) {

        orderCartPage.getTitleFromCustomItemTableCourierOrder(locale.equals(EN) ? IMAGES_EN : IMAGES_AR).shouldHave(text(locale.equals(EN) ? IMAGES_EN : IMAGES_AR));
        orderCartPage.getTitleFromCustomItemTableCourierOrder(locale.equals(EN) ? NAME_EN : NAME_AR).shouldHave(text(locale.equals(EN) ? NAME_EN : NAME_AR));
        orderCartPage.getTitleFromCustomItemTableCourierOrder(locale.equals(EN) ? DELETE_EN : DELETE_AR).shouldHave(text(locale.equals(EN) ? DELETE_EN : DELETE_AR));
    }

    @Step
    public void checkLocalisationOnTheCustomItemsTable(String locale) {
        orderCartPage.customItemsTitle.shouldHave(text(locale.equals(EN) ? CUSTOM_ITEMS_TITLE_EN : CUSTOM_ITEMS_TITLE_AR));

        orderCartPage.getTitleFromCustomItemTable(2).shouldHave(text(locale.equals(EN) ? IMAGES_EN : IMAGES_AR));
        orderCartPage.getTitleFromCustomItemTable(1).shouldHave(text(locale.equals(EN) ? NAME_EN : NAME_AR));
        orderCartPage.getTitleFromCustomItemTable(3).shouldHave(text(locale.equals(EN) ? DELETE_EN : DELETE_AR));
    }

    @Step
    public void checkLocalisationOnThePage(String locale) {
        orderCartPage.orderCartHeaderText.shouldHave(text(locale.equals(EN) ? TAB_TITLE_EN : TAB_TITLE_AR));
        checkLocalisationOnTheCatalogItemsTable(locale);
        checkLocalisationOnTheCustomItemsTable(locale);

        orderCartPage.saveOrderChanges.shouldHave(text(locale.equals(EN) ? SAVE_EN : SAVE_AR));
    }

    @Step
    public void checkLocalisationOnThePageCourierOrder(String locale) {
        orderCartPage.orderCartHeaderText.shouldHave(text(locale.equals(EN) ? TAB_TITLE_EN : TAB_TITLE_AR));

        checkLocalisationOnTheCustomItemsTableCourierOrder(locale);

        orderCartPage.saveOrderChanges.shouldHave(text(locale.equals(EN) ? SAVE_EN : SAVE_AR));
    }

    @Step
    public void checkLocalisationOnTheSelectProductsModal(String locale, Merchant merchant, String firstProductId, String secondProductId) {
        String title;
        String text;
        openAddToCartWindow();
        orderCartPage.selectProductTitle.shouldHave(text(locale.equals(EN) ? SELECT_PRODUCTS_EN : SELECT_PRODUCTS_AR));
        orderCartPage.getSelectProductInputLabel(NAME_FIELDS_NAME).shouldHave(text(locale.equals(EN) ? NAME_EN : NAME_AR));
        orderCartPage.getSelectProductInputLabel(CATEGORY_FIELDS_NAME).shouldHave(text(locale.equals(EN) ? CATEGORY_EN : CATEGORY_AR));
        orderCartPage.getSelectProductInputLabel(GROUP_FIELDS_NAME).shouldHave(text(locale.equals(EN) ? GROUP_EN : GROUP_AR));
        orderCartPage.getSelectProductInputLabel(POS_AVAILABILITY_FIELDS_NAME).shouldHave(text(locale.equals(EN) ? POS_AVAILABILITY_EN : POS_AVAILABILITY_AR));

        orderCartPage.getSelectProductTitleFromTable(1).shouldHave(text(locale.equals(EN) ? IMAGE_EN : IMAGE_AR));
        orderCartPage.getSelectProductTitleFromTable(2).shouldHave(text(locale.equals(EN) ? NAME_AND_DESCRIPTION_EN : NAME_AND_DESCRIPTION_AR));
        orderCartPage.getSelectProductTitleFromTable(3).shouldHave(text(locale.equals(EN) ? DISCOUNT_EN : DISCOUNT_AR));
        orderCartPage.getSelectProductTitleFromTable(4).shouldHave(text(locale.equals(EN) ? PRICE_EN : PRICE_AR));
        orderCartPage.getSelectProductTitleFromTable(5).shouldHave(text(locale.equals(EN) ? CATEGORY_EN : CATEGORY_AR));
        orderCartPage.getSelectProductTitleFromTable(6).shouldHave(text(locale.equals(EN) ? GROUPS_EN : GROUPS_AR));
        orderCartPage.getSelectProductTitleFromTable(7).shouldHave(text(locale.equals(EN) ? POS_AVAILABILITY_EN : POS_AVAILABILITY_AR));
        orderCartPage.getSelectProductTitleFromTable(8).shouldHave(text(locale.equals(EN) ? ACTIONS_EN : ACTIONS_AR));

        title = locale.equals(EN) ? NAME_AND_DESCRIPTION_EN : NAME_AND_DESCRIPTION_AR;
        text = locale.equals(EN) ? merchant.getProductInfoEn(firstProductId, NAME) : merchant.getProductInfoAr(firstProductId, NAME);
        orderCartPage.getTableElementCell(TEXT_XPATH, title, merchant.getProductInfoEn(firstProductId, ID)).shouldHave(text(text));
        text = locale.equals(EN) ? merchant.getProductInfoEn(secondProductId, NAME) : merchant.getProductInfoAr(secondProductId, NAME);
        orderCartPage.getTableElementCell(TEXT_XPATH, title, merchant.getProductInfoEn(secondProductId, ID)).shouldHave(text(text));
        closeAddToCartWindow();
    }

    @Step
    public void refreshPage() {
        refresh();
    }

    @Step
    public void changePictureField(String imageName) {
        orderCartPage.inputImage.shouldBe(exist);
        orderCartPage.inputImage.uploadFromClasspath("images/" + imageName);
        orderCartPage.imageNotSaved.should(exist);
    }

    @Step
    public void checkThatImageIsUpload() {
        orderCartPage.image.shouldBe(visible);
    }

    @Step
    public void clickOnDeleteBtn() {
        orderCartPage.deleteButton.shouldBe(visible);
        orderCartPage.deleteButton.click();
    }

    @Step
    public void noItemsShouldBeDisplayed() {
        orderCartPage.noItemsField.shouldBe(visible);
    }

    @Step
    public void secondProductNameShouldBeDisplayed() {
        orderCartPage.productNameFieldSecondProd.shouldBe(appear);
    }

    @Step
    public void secondProductNameShouldNotBeDisplayed() {
        orderCartPage.productNameFieldSecondProduct.shouldNotBe(appear);
    }

    @Step
    public void deleteProductItemImage() {
        orderCartPage.image.hover();
        orderCartPage.deletePictureBtn.shouldBe(visible).click();
        orderCartPage.inputImage.shouldBe(exist);
    }
}
