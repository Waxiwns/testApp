import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class EnterToYouActivity {

    public void enterToYouIsDisplayed(){
        toolbar().shouldBe(Condition.visible);
    }

    public SelenideElement toolbar(){
        return $(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@text,'Enter ToYou')]"));
    }
    public SelenideElement close(){
        return $(By.xpath("//*[contains(@resource-id, 'toolbar')]/*[contains(@class,'ImageButton')]"));
    }
}