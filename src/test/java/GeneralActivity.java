import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GeneralActivity {

    public ElementsCollection menuTabs(){
        return $$(By.id("bottom_navigation_small_container"));
    }

    public void activeTab(String tabName){
        SelenideElement el = $(By.id("bottom_navigation_small_item_title"));
        el.shouldBe(Condition.visible);
        el.shouldHave(Condition.text(tabName));
    }

    public void tapByTab(String tabName){
        switch (tabName){
            case ("Shopping"):
                menuTabs().get(0).click();
                break;
            case ("Orders"):
                menuTabs().get(1).click();
                break;
            case ("Messages"):
                menuTabs().get(2).click();
                break;
            case ("More"):
                menuTabs().get(3).click();
                break;
        }
    }

    public SelenideElement guestImage(){
        return $(By.id("imageView"));
    }

    public SelenideElement guestTitle(){
        return $(By.id("guestModeTitleTextView"));
    }

    public SelenideElement guestDesc(){
        return $(By.id("guestModeDescriptionTextView"));
    }

    public SelenideElement getStartedBtn(){
        return $(By.id("guestModeButton"));
    }

    public SelenideElement cart(){
        return $(By.id("openCartScreen"));
    }

    public SelenideElement backBtn(){
        return $(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'ImageButton')]"));
    }
}
