package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class CartActivity {

    public SelenideElement deliverToMeBtn(){
        return Selenide.$(By.id("deliverToMeTextView"));
    }

    public SelenideElement deliverToFriendBtn(){
        return Selenide.$(By.id("deliverToFriendTextView"));
    }

}
