package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.GeneralActivity;
import pages.userApp.StoreActivity;

public class StoreActivitySteps extends GeneralActivity {

    private StoreActivity store = new StoreActivity();


    public void storeIsOpen(String name){
        store.storeName().shouldBe(Condition.text(name));
    }

    public void shareStore(){
        printStepName();

        SelenideElement el = store.shareBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void addProductToCart(String product){
        printStepName();

        SelenideElement el = store.productPrice(product);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void openProduct(String product){
        printStepName();

        SelenideElement el = store.productTitle(product);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
