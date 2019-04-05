package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.GeneralActivity;
import pages.userApp.MerchantActivity;

public class MerchantActivitySteps extends GeneralActivity {

    private MerchantActivity merchant = new MerchantActivity();


    public void merchantAllProducts(){
        printStepName();

        SelenideElement el = merchant.allProductsBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void shareMerchant(){
        printStepName();

        SelenideElement el = merchant.shareBtn();
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
