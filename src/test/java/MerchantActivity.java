import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MerchantActivity extends GeneralActivity {

    public SelenideElement shareBtn(){
        return $(By.id("menuShare"));
    }

    public SelenideElement allProductsBtn(){
        return $(By.id("button"));
    }
}
