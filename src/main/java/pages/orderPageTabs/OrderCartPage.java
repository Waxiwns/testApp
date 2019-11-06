// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class OrderCartPage {

    private int getTittlePosition(String titleName) {
        String path = "//am-product-list//table//thead//th";
        ElementsCollection thead = $$(xpath(path));
        for (int i = 0; i != thead.size(); i++) {
            if (thead.get(i).getText().equals(titleName))
                return i + 1;
        }
        return -1;
    }

    public SelenideElement getTableElementCell(String typeElement, String titleName, String productId) {
        String startXpath = "//am-product-list//table//button[@id='" + productId + "']//ancestor::tr//td[" + getTittlePosition(titleName) + "]";
        return $(xpath(startXpath + "//" + typeElement));
    }

    public SelenideElement getTitleFromCatalogItemTable(int index) {
        return $(xpath("//table[1]/thead//th[" + index + "]"));
    }

    public SelenideElement getTitleFromCustomItemTable(int index) {
        return $(xpath("//table[2]/thead//th[" + index + "]"));
    }

    public SelenideElement getTitleFromCustomItemTableCourierOrder(String columnName) {
        return $(xpath("//table//th[contains(text(), '" + columnName + "')]"));
    }

    public SelenideElement orderCartHeaderText = $(xpath("//div[contains(@class,card-header)]/h2"));
    public SelenideElement productImage = $("img.img-thumbnail.p-0");
    public SelenideElement productName = $("a.link");
    public SelenideElement optionsPopupBtn = $(xpath("//am-setting-product-options/button"));
    public SelenideElement quantityInputField = $(xpath("//am-order-cart//input"));
    public SelenideElement productPrice = $(xpath("//td[@class='text-right text-success'][1]/span"));
    public SelenideElement productFullPrice = $(xpath("//td[@class='text-right text-success'][2]/span"));
    public SelenideElement productTotalPrice = $(xpath("//label[@class='text-success']/strong"));
    public SelenideElement noteTextArea = $(xpath("//textarea"));
    public SelenideElement deleteProductBtn = $(xpath("//button[@title='Delete item']"));
    public SelenideElement addRegularProductItemButton = $(xpath("//td[@colspan = 2]/button[@title='Add item']"));
    public SelenideElement addCustomProductItemButton = $(xpath("//td[@colspan = 8]/button[@title='Add item']"));
    public SelenideElement selectedProductOptions = $(xpath("//am-setting-product-options/ul/li/span"));
    public SelenideElement selectedProductOptionsInTable = $(xpath("//am-setting-product-options/ul/li"));
    public SelenideElement doubleCheeseCheckbox = $(xpath("//input[@id=\"checkbox-10439-1554\"]"));
    public SelenideElement totalLabel = $(xpath("//table//strong/span"));
    public SelenideElement catalogItemsTitle = $(xpath("//am-order-cart//h5[1]"));
    public SelenideElement customItemsTitle = $(xpath("//am-order-cart//h5[2]"));
    public SelenideElement deleteProductBtn(String productId){
        return $(xpath("//tr//a[contains(@href,'"+productId+"')]//ancestor::tr//button[@title='Delete item']"));
    }

    public SelenideElement saveOrderChanges = $(xpath("//am-save-cancel/div/button[2]"));
    public ElementsCollection selectedOptionsList = $$("ul.list-unstyled.mb-0.ng-star-inserted");
    public SelenideElement addItemButton = $(xpath("//button[contains(@class, 'btn btn-sm btn-secondary skip-valid-border')]"));
    public SelenideElement emptyAddItemField = $(xpath("//input[contains(@class, 'ng-invalid')]"));
    public SelenideElement productNameField = $(xpath("//input[contains(@class, 'form-control content-rtl ng-untouched ng-pristine ng-valid')]"));
    public SelenideElement productNameFieldSecondProd = $(xpath("//input[contains(@class, 'form-control content-rtl ng-untouched ng-pristine ng-valid')]/parent::td/parent::tr//following-sibling::tr//input"));
    public SelenideElement productNameFieldSecondProduct = $(xpath("//input[contains(@class, 'form-control content-rtl ng-untouched ng-pristine ng-valid')][2]"));
    public SelenideElement picture = $(xpath("//div[contains(@class, 'picture-wrapper')]/img"));
    public SelenideElement inputImage = $(xpath("//div/input[@type = 'file']"));
    public SelenideElement deleteButton = $(xpath("//button[contains(@class, 'btn btn-delete')]"));
    public SelenideElement noItemsField = $(xpath("//tr[contains(., 'No items')]"));
    public SelenideElement cancelBtn = $(xpath("//button[contains(@class, 'btn btn-sm btn-secondary btn-xs-stretching')]"));
    public SelenideElement image = $(xpath("//img[contains(@class, 'rounded')]"));
    public SelenideElement imageNotSaved = $(xpath("//i[contains(@class, 'fa fa-times-circle-o ng-star-inserted')]"));
    public SelenideElement deletePictureBtn = $(xpath("//i[contains(@class, 'fa fa-times-circle-o')]"));

    //"Delete order line" dialogue
    public SelenideElement deleteOrderLineDialogue = $(xpath("//div[@class='modal-content']"));
    public SelenideElement deleteOrderLineHeader = $(xpath("//h4[@class='modal-title']"));
    public SelenideElement deleteOrderLineBody = $(xpath("//div[@class='modal-body']/p"));
    public SelenideElement deleteOrderLineNoBtn = $(xpath("//div[@role='document']//button[@class='btn btn-secondary']"));
    public SelenideElement deleteOrderLineYesBtn = $(xpath("//div[@role='document']//button[@class='btn btn-primary']"));

    // "Discard Changes" dialogue popup
    public SelenideElement discardChangesDialogue = $(xpath("//div[@role='document']"));
    public SelenideElement discardChangesDialogueHeader = $(xpath("//h4[@class='modal-title']"));
    public SelenideElement discardChangesDialogueBody = $(xpath("//div[@class='modal-body']/p"));
    public SelenideElement discardChangesDialogueYesBtn = $(xpath("//button[contains(text(), 'Yes')]"));
    public SelenideElement discardChangesDialogueNoBtn = $(xpath("//button[contains(text(), 'No')]"));

    // "Product Options" popup elements
    public SelenideElement optionsPopupSaveBtn = $(xpath("//button[contains(text(),'Save')]"));

    // "Select products" popup elements
    public SelenideElement selectProductPopup = $(xpath("//div[@class='modal-content']"));
    public SelenideElement selectProductTitle = $(xpath("//h4[@class='modal-title']"));
    public SelenideElement productImageOnPopup = $(xpath("//tr[@aria-rowindex = 2]//img"));
    public SelenideElement productWithoutPhotoOnPopup = $(xpath("//tr[@aria-rowindex = 3]//img"));
    public SelenideElement firstInTheListProductName = $(xpath("//tr[1]/td[2]/h6/am-link-or-text"));
    public SelenideElement firstInTheListProductDescription = $(xpath("//tr[1]/td[2]/p"));
    public SelenideElement productDiscountInTheList = $(xpath("//tr[1]/td[@aria-colindex = 3]/span"));
    public SelenideElement productPriceInTheList = $(xpath("//tr[1]/td[@aria-colindex = 4]/span"));
    public SelenideElement productCategoryInTheList = $(xpath("//tr[1]/td[@aria-colindex = 5]/span"));
    public SelenideElement productGroupsInTheList = $(xpath("//tr[1]/td[@aria-colindex = 6]//li"));
    public SelenideElement productPOSAvailabilityInTheList = $(xpath("//tr[1]/td[@aria-colindex = 7]/div/span"));
    public SelenideElement addToCartButton = $(xpath("//*[@id='cb513f8c-2045-4649-8b84-b537191a7669']"));
    public SelenideElement closeSelectProductsPopup = $(xpath("//button[@class='close']"));

    //product
    public static final String PIZZA = "Pizza";
    public static final String PIZZA_IMAGE = "pizza.jpg";

    public SelenideElement getSelectProductInputLabel(String name) {
        return $(xpath("//div[@class='filter-form-group']//*[@name='" + name + "']/parent::div/label"));
    }

    public SelenideElement getSelectProductTitleFromTable(int index) {
        return $(xpath("//div[@class='modal-content']//table//thead/tr/th[" + index + "]"));
    }

    //ELEMENT
    public static final String BUTTON_XPATH = "button";
    public static final String TEXT_XPATH = "self::td";

    //input name
    public static final String NAME_FIELDS_NAME = "name";
    public static final String NAME_FIELDS_NAME_AR = "nameAr";
    public static final String CATEGORY_FIELDS_NAME = "categoryId";
    public static final String GROUP_FIELDS_NAME = "groupId";
    public static final String POS_AVAILABILITY_FIELDS_NAME = "posId";
    public static final String PRODUCT_ID = "productId";

    //cart tab
    public static final String TAB_TITLE_EN = "Order Cart";
    public static final String TAB_TITLE_AR = "سلة المشتريات";
    public static final String CATALOG_ITEM_TITLE_EN = "Catalog items";
    public static final String CATALOG_ITEM_TITLE_AR = "عناصر الكتالوج";
    public static final String CUSTOM_ITEMS_TITLE_EN = "Custom items";
    public static final String CUSTOM_ITEMS_TITLE_AR = "لعناصر الخاصة";
    public static final String IMAGE_EN = "Image";
    public static final String IMAGE_AR = "صورة";
    public static final String NAME_EN = "Name";
    public static final String NAME_AR = "الإسم";
    public static final String PRODUCT_OPTIONS_EN = "Product options";
    public static final String PRODUCT_OPTIONS_AR = "خيارات المنتج";
    public static final String QUANTITY_EN = "Quantity";
    public static final String QUANTITY_AR = "الكمية";
    public static final String PRICE_SAR_EN = "Price, SAR";
    public static final String PRICE_SAR_AR = "السعر, ريال";
    public static final String FULL_PRICE_SAR_EN = "Full price, SAR";
    public static final String FULL_PRICE_SAR_AR = "السعر الكامل , ريال";
    public static final String NOTE_EN = "Note";
    public static final String NOTE_AR = "ملاحظه";
    public static final String IMAGES_EN = "Images";
    public static final String IMAGES_AR = "صور";
    public static final String ADD_ITEM_EN = "AddT item";
    public static final String ADD_ITEM_AR = "إضافة عنصر";
    public static final String SAVE_EN = "Save";
    public static final String SAVE_AR = "حفظ";
    public static final String DELETE_EN = "Delete";
    public static final String DELETE_AR = "حذف";
    public static final String TOTAL_EN = "Total";
    public static final String TOTAL_AR = "المبلغ الاجمالي";

    //add items modal windows
    public static final String CATEGORY_EN = "Category";
    public static final String CATEGORY_AR = "الفئة";
    public static final String GROUP_EN = "Group";
    public static final String GROUP_AR = "مجموعة";
    public static final String GROUPS_EN = "Groups";
    public static final String GROUPS_AR = "مجموعات";
    public static final String POS_AVAILABILITY_EN = "POS availability";
    public static final String POS_AVAILABILITY_AR = "توفر نقاط البيع";
    public static final String NAME_AND_DESCRIPTION_EN = "Name and Description";
    public static final String NAME_AND_DESCRIPTION_AR = "الاسم والوصف";
    public static final String DISCOUNT_EN = "Discount";
    public static final String DISCOUNT_AR = "خصم";
    public static final String PRICE_EN = "Price";
    public static final String PRICE_AR = "السعر";
    public static final String ACTIONS_EN = "Actions";
    public static final String ACTIONS_AR = "أفعال , نشاط";
    public static final String ADD_TO_CART_EN = "Add to cart";
    public static final String ADD_TO_CART_AR = "إضافه إلى السلة";
    public static final String FILTER_EN = "Filter";
    public static final String FILTER_AR = "تطبيق الفلتر";
    public static final String SELECT_PRODUCTS_EN = "Select products";
    public static final String SELECT_PRODUCTS_AR = "حدد المنتجات";


}