package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.EnterToYouActivity;

public class EnterToYouActivitySteps {

    private EnterToYouActivity enter = new EnterToYouActivity();


    public void enterActivityIsDisplayed(){
        printStepName();

        enter.enterToYouIsDisplayed();
    }

    public void closeEnter(){
        printStepName();

        enterActivityIsDisplayed();
        enter.close().click();
    }

    public void clickCountryCode(){
        printStepName();

        SelenideElement el = enter.countryCodeDrop();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void clickSearchCountry(){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        enter.searchCountryBtn().click();
    }

    public void fillSearchCountry(String country){
        printStepName();

        enter.searchCountryInput().setValue(country);
    }

    public void chooseCountry(String country){
        printStepName();

        SelenideElement el = enter.country(country);

        clickSearchCountry();
        fillSearchCountry(country);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void fillPhoneNumber(String phoneNumber){
        printStepName();

        SelenideElement el = enter.phoneNumberField();

        el.shouldBe(Condition.visible);
        el.setValue(phoneNumber);
    }

    public void clickEnter(){
        printStepName();

        SelenideElement el = enter.enterBtn();

        el.shouldBe(Condition.enabled);
        el.click();
    }

    public void fillCode(String code){
        printStepName();

        enter.code().setValue(code);
    }

    public void signIn(String country, String phoneNumber){
        printStepName();

        String code = phoneNumber.substring(phoneNumber.length() - 4);

//        choose country
        clickCountryCode();
        chooseCountry(country);

//        fill phone number
        fillPhoneNumber(phoneNumber);
        clickEnter();
        fillCode(code);
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}