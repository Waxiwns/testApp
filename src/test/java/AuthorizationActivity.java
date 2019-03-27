import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AuthorizationActivity {

    public SelenideElement getStartedBtn(){
        return $(By.id("buttonGetStarted"));
    }

    public SelenideElement skipForNowLnk(){
        return $(By.id("guestSkipForNowTextView"));
    }
}
