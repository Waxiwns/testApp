import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SearchActivity {

    public SelenideElement searchField(){
        return $(By.id("searchEdit"));
    }

    public SelenideElement chooseTab(String tabName){
        return $(By.xpath("//*[contains(@class, 'Tab')]/*[contains(@text, '" + tabName + "')]"));
    }

    public SelenideElement merchantTitle(String title){
        return $(By.xpath("//*[contains(@resource-id, 'merchantHeader')][contains(@text,'" + title + "')]"));
    }
}
