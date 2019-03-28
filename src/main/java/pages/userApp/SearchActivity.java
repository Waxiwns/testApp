package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class SearchActivity {

    public SelenideElement searchField(){
        return Selenide.$(By.id("searchEdit"));
    }

    public SelenideElement chooseTab(String tabName){
        return Selenide.$(By.xpath("//*[contains(@class, 'Tab')]/*[contains(@text, '" + tabName + "')]"));
    }

    public SelenideElement merchantTitle(String title){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'merchantHeader')][contains(@text,'" + title + "')]"));
    }
}
