// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.PaginationPage;

import static com.codeborne.selenide.Condition.*;

public class PaginationPageSteps {

    public String firstValue = "10";
    public String secondValue = "25";
    public String thirdValue = "100";

    public String itemsPerPageTitleEn = "items per page";

    public String paginationCounterEn = "1 - 10 of 62 items";

    private PaginationPage paginationPage = new PaginationPage();

    @Step
    public void checkQtyOfItemsStepShouldHaveText(String text) {
        paginationPage.qtyOfItemsTxt.shouldHave(text(text));
    }

    @Step
    public void checkQtyOfItemsStepShouldBeEnglish() {
        checkQtyOfItemsStepShouldHaveText(paginationCounterEn);
    }

    @Step
    public void checkVisibilityOfQtyOfItemsStep() {
        paginationPage.qtyOfItemsTxt.shouldBe(visible);
    }

    @Step
    public void checkVisibilityOfGoFirstPageButtonStep() {
        paginationPage.goFirstPageBtn.shouldBe(visible);
    }

    @Step
    public void checkVisibilityOfGoToThePreviousPageButtonStep() {
        paginationPage.goPreviousPageBtn.shouldBe(visible);
    }

    @Step
    public void checkVisibilityOfFirstPageButtonStep() {
        paginationPage.pageBtnByNumber(1).shouldBe(visible);
    }

    @Step
    public void checkVisibilityOfGoToTheNextPageButtonStep() {
        paginationPage.goNextPageBtn.shouldBe(Condition.visible);
    }

    @Step
    public void checkVisibilityOfGoToTheLastPageButtonStep() {
        paginationPage.goLastPageBtn.shouldBe(Condition.visible);
    }

    @Step
    public void checkThatItemsPerPageDropDownFirstValueExist() {
        paginationPage.itemsPerPageDropDownByValue(firstValue).shouldBe(exist);
    }

    @Step
    public void checkThatItemsPerPageDropDownSecondValueExist() {
        paginationPage.itemsPerPageDropDownByValue(secondValue).shouldBe(exist);
    }

    @Step
    public void checkThatItemsPerPageDropDownThirdValueExist() {
        paginationPage.itemsPerPageDropDownByValue(thirdValue).shouldBe(exist);
    }

    @Step
    public void checkThatFirstValueOfItemsPerPageDropDownSelected() {
        paginationPage.itemsPerPageDropDownTxt.getSelectedOption().shouldHave(text(firstValue));
    }

    @Step
    public void checkThatSecondValueOfItemsPerPageDropDownSelected() {
        paginationPage.itemsPerPageDropDownTxt.getSelectedOption().shouldHave(text(secondValue));
    }

    @Step
    public void checkThatThirdValueOfItemsPerPageDropDownSelected() {
        paginationPage.itemsPerPageDropDownTxt.getSelectedOption().shouldHave(text(thirdValue));
    }

    @Step
    public void checkItemsPerPageTitleShouldHaveText(String string) {
        paginationPage.itemsPerPageTitleTxt.shouldHave(text(string));
    }

    @Step
    public void checkItemsPerPageTitleShouldBeEnglish() {
        checkItemsPerPageTitleShouldHaveText(itemsPerPageTitleEn);
    }

    @Step
    public void checkThatActiveNumberButtonHaveTextStep(String text) {
        paginationPage.activePageNumber.shouldBe(visible).shouldHave(text(text));
    }

    @Step
    public void clickSecondPageButtonStep() {
        paginationPage.pageBtnByNumber(2).click();
        checkThatActiveNumberButtonHaveTextStep("2");
    }

    @Step
    public void clickPreviousPageButtonStep() {
        paginationPage.goPreviousPageBtn.click();
    }

    @Step
    public void clickNextPageButtonStep() {
        paginationPage.goNextPageBtn.click();
    }

    @Step
    public void clickFirstPageButtonStep() {
        paginationPage.goFirstPageBtn.click();
        checkThatActiveNumberButtonHaveTextStep("1");
    }

    @Step
    public void clickLastPageButtonStep() {
        paginationPage.goLastPageBtn.click();
    }
}
