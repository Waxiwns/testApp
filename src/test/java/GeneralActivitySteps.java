import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class GeneralActivitySteps {

    private GeneralActivity tab = new GeneralActivity();


    public void clickGetStarted(){
        getStartedBtnIsDisplayed();
        tab.getStartedBtn().click();
    }

    public void activeTab(String tabName){
        tab.activeTab(tabName);
    }

    public void tapByTab(String tabName){
        tab.tapByTab(tabName);
        activeTab(tabName);
    }

    public void guestDescIsDisplayed(String descTitle, String desc){
        tab.guestImage().shouldBe(Condition.visible);
        tab.guestTitle().shouldHave(Condition.text(descTitle));
        tab.guestDesc().shouldHave(Condition.text(desc));
        getStartedBtnIsDisplayed();
    }

    public void getStartedBtnIsDisplayed(){
        tab.getStartedBtn().shouldBe(Condition.visible);
    }

    public void openCart(){
        SelenideElement el = tab.cart();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void clickBack(){
        SelenideElement el = tab.backBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }
}
