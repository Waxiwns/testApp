package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.CartActivity;
import pages.userApp.GeneralActivity;

public class CartActivitySteps extends GeneralActivity {

    private CartActivity cart = new CartActivity();


    public void deliverToMe(){
        printStepName();

        SelenideElement el = cart.deliverToMeBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void deliverToFriend(){
        printStepName();

        SelenideElement el = cart.deliverToFriendBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
