import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class EnterToYouActivitySteps {

    private EnterToYouActivity enter = new EnterToYouActivity();


    public void enterActivityIsDisplayed(){
        enter.enterToYouIsDisplayed();
    }

    public void closeEnter(){
        enterActivityIsDisplayed();
        enter.close().click();
    }

    public void clickCountryCode(){
        SelenideElement el = enter.countryCodeDrop();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void clickSearchCountry(){
        enter.searchCountryBtn().click();
    }

    public void fillSearchCountry(String country){
        enter.searchCountryInput().setValue(country);
    }

    public void chooseCountry(String country){
        SelenideElement el = enter.country(country);

        clickSearchCountry();
        fillSearchCountry(country);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void fillPhoneNumber(String phoneNumber){
        SelenideElement el = enter.phoneNumberField();

        el.shouldBe(Condition.visible);
        el.setValue(phoneNumber);
    }

    public void clickEnter(){
        SelenideElement el = enter.enterBtn();

        el.shouldBe(Condition.enabled);
        el.click();
    }

    public void fillCode(String code){
            enter.code().setValue(code);
    }

    public void signIn(String country, String phoneNumber){
        String code = phoneNumber.substring(phoneNumber.length() - 4);

//        choose country
        clickCountryCode();
        chooseCountry(country);

//        fill phone number
        fillPhoneNumber(phoneNumber);
        clickEnter();
        fillCode(code);
    }
}