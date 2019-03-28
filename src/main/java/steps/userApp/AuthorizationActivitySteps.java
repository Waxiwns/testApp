package steps.userApp;

import com.codeborne.selenide.Condition;
import pages.userApp.AuthorizationActivity;

public class AuthorizationActivitySteps {

    private AuthorizationActivity authorization = new AuthorizationActivity();


    public void skipLnkIsDisplayed(){
        authorization.skipForNowLnk().shouldBe(Condition.visible);
    }

    public void getStartedBtnIsDisplayed(){
        authorization.getStartedBtn().shouldBe(Condition.visible);
    }

    public void clickSkip(){
        skipLnkIsDisplayed();
        authorization.skipForNowLnk().click();
        authorization.skipForNowLnk().click();
    }

    public void clickStarted(){
        getStartedBtnIsDisplayed();
        authorization.getStartedBtn().click();
        authorization.getStartedBtn().click();
    }
}
