package pages.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class EnterToYouActivity {

    public void enterToYouIsDisplayed(){
        toolbar().shouldBe(Condition.visible);
        enterBtn().shouldBe(Condition.disabled);
    }

    public SelenideElement toolbar(){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@text,'Enter ToYou')]"));
    }

    public SelenideElement close(){
        return Selenide.$(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'ImageButton')]"));
    }

    public SelenideElement countryCodeDrop(){
        return Selenide.$(By.id("editTextCountryCode"));
    }

    public SelenideElement searchCountryBtn(){
        return Selenide.$(By.id("menuSearch"));
    }

    public SelenideElement searchCountryInput(){
        return Selenide.$(By.id("countryCodeSearchEditText"));
    }

    public SelenideElement country(String country){
        return Selenide.$(By.xpath("//*[@resource-id = 'com.arammeem.toyou.android.regression:id/country'][contains(@text, '" + country + "')]"));
    }

    public SelenideElement phoneNumberField(){
        return Selenide.$(By.id("editTextPhoneNumber"));
    }

    public SelenideElement enterBtn(){
        return Selenide.$(By.id("buttonNext"));
    }

    public SelenideElement code(){
        return Selenide.$(By.id("viewPin1"));
    }
}