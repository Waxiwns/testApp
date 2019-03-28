package pages.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class GeneralActivity {

    public ElementsCollection menuTabs(){
        return Selenide.$$(By.id("bottom_navigation_small_container"));
    }

    public void activeTab(String tabName){
        SelenideElement el = Selenide.$(By.id("bottom_navigation_small_item_title"));

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
        return Selenide.$(By.id("imageView"));
    }

    public SelenideElement guestTitle(){
        return Selenide.$(By.id("guestModeTitleTextView"));
    }

    public SelenideElement guestDesc(){
        return Selenide.$(By.id("guestModeDescriptionTextView"));
    }

    public SelenideElement getStartedBtn(){
        return Selenide.$(By.id("guestModeButton"));
    }

    public SelenideElement cart(){
        return Selenide.$(By.id("openCartScreen"));
    }

    public SelenideElement backBtn(){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'ImageButton')]"));
    }

    public SelenideElement titleActivity(){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'TextView')]"));
    }

    public SelenideElement toolbarSaveBtn(){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'menuSave')]"));
    }
}
