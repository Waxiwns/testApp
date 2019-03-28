package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class StoreActivity {

    public SelenideElement storeName(){
        return Selenide.$(By.id("merchantToolbarTitleTextView"));
    }

    public SelenideElement shareBtn(){
        return Selenide.$(By.id("menuShare"));
    }

    public SelenideElement productTitle(String name){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'productTitle')][@text = '" + name + "']"));
    }

    public SelenideElement productPrice(String name){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'productTitle')][@text = '" + name + "']/parent::*/parent::*/*[contains(@resource-id, 'productPriceContainer')]/*[contains(@resource-id, 'productPrice')]"));
    }
}
