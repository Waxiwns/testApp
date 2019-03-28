import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class EnterToYouActivity {

    public void enterToYouIsDisplayed(){
        toolbar().shouldBe(Condition.visible);
        enterBtn().shouldBe(Condition.disabled);
    }

    public SelenideElement toolbar(){
        return $(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@text,'Enter ToYou')]"));
    }

    public SelenideElement close(){
        return $(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'ImageButton')]"));
    }

    public SelenideElement countryCodeDrop(){
        return $(By.id("editTextCountryCode"));
    }

    public SelenideElement searchCountryBtn(){
        return $(By.id("menuSearch"));
    }

    public SelenideElement searchCountryInput(){
        return $(By.id("countryCodeSearchEditText"));
    }

    public SelenideElement country(String country){
        return $(By.xpath("//*[@resource-id = 'com.arammeem.toyou.android.regression:id/country'][contains(@text, '" + country + "')]"));
    }

    public SelenideElement phoneNumberField(){
        return $(By.id("editTextPhoneNumber"));
    }

    public SelenideElement enterBtn(){
        return $(By.id("buttonNext"));
    }

    public SelenideElement code(){
        return $(By.id("viewPin1"));
    }
}