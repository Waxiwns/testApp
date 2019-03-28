package steps.userApp;

import com.codeborne.selenide.Condition;
import pages.userApp.MoreTab;

public class MoreTabSteps extends GeneralActivitySteps {

    private MoreTab more = new MoreTab();


    public String getUserName(){
        return more.userName().text();
    }

    public void userNameShouldBe(String userName){
        more.userName().shouldHave(Condition.text(userName));
    }
}
