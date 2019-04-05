package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.GeneralActivity;

public class GeneralActivitySteps {

    private GeneralActivity tab = new GeneralActivity();


    public void clickGetStarted(){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        getStartedBtnIsDisplayed();
        tab.getStartedBtn().click();
    }

    public void activeTab(String tabName){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        tab.activeTab(tabName);
    }

    public void chooseTab(String tabName){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        tab.tapByTab(tabName);
        activeTab(tabName);
    }

    public void guestDescIsDisplayed(String descTitle, String desc){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        tab.guestImage().shouldBe(Condition.visible);
        tab.guestTitle().shouldHave(Condition.text(descTitle));
        tab.guestDesc().shouldHave(Condition.text(desc));
        getStartedBtnIsDisplayed();
    }

    public void getStartedBtnIsDisplayed(){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        tab.getStartedBtn().shouldBe(Condition.visible);
    }

    public void openCart(){
        System.out.println("Step: " + new Object() {}.getClass().getEnclosingMethod().getName());

        SelenideElement el = tab.cart();

        el.shouldBe(Condition.visible);
        el.click();
    }

    public void clickBack(){
        printStepName();

        SelenideElement el = tab.backBtn();

        el.shouldBe(Condition.visible);
        el.click();
    }

    public void titleActivityShouldBe(String title){
        printStepName();

        tab.titleActivity().shouldHave(Condition.text(title));
    }

    public void clickToolbarSave(){
        printStepName();

        SelenideElement el = tab.toolbarSaveBtn();

        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
