package steps.userApp;

import com.codeborne.selenide.Condition;
import pages.userApp.AuthorizationActivity;

public class AuthorizationActivitySteps {

    private AuthorizationActivity authorization = new AuthorizationActivity();


    public void skipLnkIsDisplayed(){
        printStepName();
        authorization.skipForNowLnk().shouldBe(Condition.visible);
    }

    public void getStartedBtnIsDisplayed(){
        printStepName();

        authorization.getStartedBtn().shouldBe(Condition.visible);
    }

    public void clickSkip(){
        printStepName();

        skipLnkIsDisplayed();
        authorization.skipForNowLnk().click();
        authorization.skipForNowLnk().click();
    }

    public void clickStarted(){
        printStepName();

        getStartedBtnIsDisplayed();
        authorization.getStartedBtn().click();
        authorization.getStartedBtn().click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
