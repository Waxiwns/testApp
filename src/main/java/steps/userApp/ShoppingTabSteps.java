package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.GeneralActivity;
import pages.userApp.ShoppingTab;

public class ShoppingTabSteps extends GeneralActivity {

    private ShoppingTab shopping = new ShoppingTab();


    public void clickSearch(){
        printStepName();

        shopping.searchBtn().click();
    }

    public void chooseShoppingItem(String item){
        printStepName();

        SelenideElement el = shopping.chooseItem(item);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}