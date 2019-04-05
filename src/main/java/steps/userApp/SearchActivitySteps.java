package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.SearchActivity;

public class SearchActivitySteps {

    private SearchActivity search = new SearchActivity();


    public void fillSearchField(String value){
        printStepName();

        search.searchField().setValue(value);
    }

    public void chooseTab(String tabName){
        printStepName();

        SelenideElement el = search.chooseTab(tabName);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void chooseMerchant(String title){
        printStepName();

        SelenideElement el = search.merchantTitle(title);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void printStepName(){
        System.out.println("Step: " + new Throwable()
                .getStackTrace()[1]
                .getMethodName());
    }
}
