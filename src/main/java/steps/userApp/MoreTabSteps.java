package steps.userApp;

import com.codeborne.selenide.Condition;
import pages.userApp.MoreTab;

public class MoreTabSteps extends GeneralActivitySteps {

    private MoreTab more = new MoreTab();


    public String getUserName(){
        printStepName();

        return more.userName().text();
    }

    public void userNameShouldBe(String userName){
        printStepName();

        more.userName().shouldHave(Condition.text(userName));
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
