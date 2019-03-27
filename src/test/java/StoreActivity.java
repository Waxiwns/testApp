import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class StoreActivity extends GeneralActivity {

    public SelenideElement storeName(){
        return $(By.id("merchantToolbarTitleTextView"));
    }

    public SelenideElement shareBtn(){
        return $(By.id("menuShare"));
    }

    public SelenideElement productTitle(String name){
        return $(By.xpath("//*[contains(@resource-id, 'productTitle')][@text = '" + name + "']"));
    }

    public SelenideElement productPrice(String name){
        return $(By.xpath("//*[contains(@resource-id, 'productTitle')][@text = '" + name + "']/parent::*/parent::*/*[contains(@resource-id, 'productPriceContainer')]/*[contains(@resource-id, 'productPrice')]"));
    }
}
