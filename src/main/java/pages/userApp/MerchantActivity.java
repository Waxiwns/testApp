package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class MerchantActivity {

    public SelenideElement shareBtn(){
        return Selenide.$(By.id("menuShare"));
    }

    public SelenideElement allProductsBtn(){
        return Selenide.$(By.id("button"));
    }
}
