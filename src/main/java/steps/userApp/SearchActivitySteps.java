package steps.userApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.userApp.SearchActivity;

public class SearchActivitySteps {

    private SearchActivity search = new SearchActivity();


    public void fillSearchField(String value){
        search.searchField().setValue(value);
    }

    public void chooseTab(String tabName){
        SelenideElement el = search.chooseTab(tabName);
        el.shouldBe(Condition.visible);
        el.click();
    }

    public void chooseMerchant(String title){
        SelenideElement el = search.merchantTitle(title);
        el.shouldBe(Condition.visible);
        el.click();
    }
}
