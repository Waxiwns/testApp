package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class MoreTab extends GeneralActivity {

    public SelenideElement userName(){
        return Selenide.$(By.id("moreAccount"));
    }
}
