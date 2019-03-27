import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class StoreActivitySteps extends GeneralActivity {

    private StoreActivity store = new StoreActivity();


    public void storeIsOpen(String name){
        store.storeName().shouldBe(Condition.text(name));
    }

    public void shareStore(){
        SelenideElement el = store.shareBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void addProductToCart(String product){
        SelenideElement el = store.productPrice(product);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void openProduct(String product){
        SelenideElement el = store.productTitle(product);
        el.shouldBe(Condition.visible);
        el.click();
    }
}
