import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CartActivity extends GeneralActivity {

    public SelenideElement deliverToMeBtn(){
        return $(By.id("deliverToMeTextView"));
    }

    public SelenideElement deliverToFriendBtn(){
        return $(By.id("deliverToFriendTextView"));
    }

}
