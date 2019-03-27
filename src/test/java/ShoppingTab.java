import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ShoppingTab{

    public SelenideElement searchBtn(){
        return $(By.id("menuSearch"));
    }

    public SelenideElement chooseItem(String item){
        return $(By.xpath("//*[contains(@text, '" + item + "')]/parent::*/android.widget.FrameLayout"));
    }
}