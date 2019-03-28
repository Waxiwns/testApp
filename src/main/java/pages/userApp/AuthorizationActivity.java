package pages.userApp;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class AuthorizationActivity {

    public SelenideElement getStartedBtn(){
        return Selenide.$(By.id("buttonGetStarted"));
    }

    public SelenideElement skipForNowLnk(){
        return Selenide.$(By.id("guestSkipForNowTextView"));
    }
}
