import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class CartActivitySteps extends GeneralActivity {

    private CartActivity cart = new CartActivity();


    public void deliverToMe(){
        SelenideElement el = cart.deliverToMeBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void deliverToFriend(){
        SelenideElement el = cart.deliverToFriendBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }
}
