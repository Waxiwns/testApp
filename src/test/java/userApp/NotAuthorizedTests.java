package userApp;

import org.junit.Test;
import steps.userApp.*;

public class NotAuthorizedTests extends AppTests {

    String firstTab = "Shopping";

    String moreTab = "More";

    String messagesTab = "Messages";

    String ordersTab = "Orders";

    String descTitle = "You're viewing as a guest";

    String moreDesc = "In order for you to manage your account and profile, please sign in, or sign up if you don’t have an account.";

    String messagesDesc = "Creating an account and signing in allows you to use our in-app chat, share favorite products and gifts.";

    String ordersDesc = "Creating a ToYou account and signing in allows you to place orders, set your pickup or drop-off locations and enjoy a personalized experience.";

    String merchantDesc = "Proceeding to this feature requires having an active account and signing in.";

    String merchant = "Ashan store";

    String merchantsTab = "Merchants";

    String taxiItem = "Taxi";

    String product = "qwerty";

    private AuthorizationActivitySteps authorizationSteps = new AuthorizationActivitySteps();

    private GeneralActivitySteps generalSteps = new GeneralActivitySteps();

    private EnterToYouActivitySteps enterSteps = new EnterToYouActivitySteps();

    private ShoppingTabSteps shoppingSteps = new ShoppingTabSteps();

    private SearchActivitySteps searchSteps = new SearchActivitySteps();

    private MerchantActivitySteps merchantSteps = new MerchantActivitySteps();

    private CartActivitySteps cartSteps = new CartActivitySteps();

    private StoreActivitySteps storeSteps = new StoreActivitySteps();


    @Test
    public void moreTab() {
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

        generalSteps.chooseTab(moreTab);

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, moreDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void messagesTab() {
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

        generalSteps.chooseTab(messagesTab);

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, messagesDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void ordersTab() {
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

        generalSteps.chooseTab(ordersTab);

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, ordersDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void driver() {
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

        shoppingSteps.chooseShoppingItem(taxiItem);

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, merchantDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void canNotShareMerchant() {
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

//        search merchant
        shoppingSteps.clickSearch();
        searchSteps.fillSearchField(merchant);
        searchSteps.chooseTab(merchantsTab);
        searchSteps.chooseMerchant(merchant);

//        share merchant
        merchantSteps.shareMerchant();
        enterSteps.enterActivityIsDisplayed();

//        get started !(Get started dialog doesn't show)
        enterSteps.closeEnter();

//        go to store
        merchantSteps.merchantAllProducts();
        storeSteps.storeIsOpen(merchant);

//        share store
        storeSteps.shareStore();

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, merchantDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void canNotDeliver(){
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

//        search merchant
        shoppingSteps.clickSearch();
        searchSteps.fillSearchField(merchant);
        searchSteps.chooseTab(merchantsTab);
        searchSteps.chooseMerchant(merchant);

//        go to store
        merchantSteps.merchantAllProducts();
        storeSteps.storeIsOpen(merchant);

        storeSteps.addProductToCart(product);

        generalSteps.openCart();

//        deliver to me
        cartSteps.deliverToMe();

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, merchantDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();

        enterSteps.closeEnter();

        generalSteps.clickBack();

//        deliver to friend
        cartSteps.deliverToFriend();

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, merchantDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }

    @Test
    public void canNotShareProduct(){
        printTestName();

        authorizationSteps.clickSkip();
        generalSteps.activeTab(firstTab);

//        search merchant
        shoppingSteps.clickSearch();
        searchSteps.fillSearchField(merchant);
        searchSteps.chooseTab(merchantsTab);
        searchSteps.chooseMerchant(merchant);

//        go to store
        merchantSteps.merchantAllProducts();
        storeSteps.storeIsOpen(merchant);

        storeSteps.openProduct(product);

//        share product
        storeSteps.shareStore();

//        get started
        generalSteps.guestDescIsDisplayed(descTitle, merchantDesc);
        generalSteps.clickGetStarted();
        enterSteps.enterActivityIsDisplayed();
    }
}
