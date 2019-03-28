package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class ShoppingTab {

    public SelenideElement searchBtn(){
        return Selenide.$(By.id("menuSearch"));
    }

    public SelenideElement chooseItem(String item){
        return Selenide.$(By.xpath("//*[contains(@text, '" + item + "')]/parent::*/android.widget.FrameLayout"));
    }
}