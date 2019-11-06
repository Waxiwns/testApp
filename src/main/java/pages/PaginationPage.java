// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class PaginationPage {

    private String paginationSector = "am-kendo-pager";

    public SelenideElement qtyOfItemsTxt = $(paginationSector + " kendo-pager-info");   //    e.g. 1 - 10 of 114
    public SelenideElement goFirstPageBtn = $(paginationSector + "  [title='Go to the first page']");
    public SelenideElement goPreviousPageBtn = $(paginationSector + "  [title='Go to the previous page']");
    public SelenideElement goLastPageBtn = $(paginationSector + "  [title='Go to the last page']");
    public SelenideElement goNextPageBtn = $(paginationSector + "  [title='Go to the next page']");
    public SelenideElement activePageNumber = $(xpath("//" + paginationSector + "//*[@class = 'pager-5']//a[@class = 'k-link k-state-selected']"));

    public SelenideElement itemsPerPageDropDownTxt = $(paginationSector + " select");
    public SelenideElement itemsPerPageTitleTxt = $(xpath("//" + paginationSector + "//kendo-pager-page-sizes[@class='k-pager-sizes k-label']"));

    public SelenideElement pageBtnByNumber(int pageNumber) {
        return $(xpath("//" + paginationSector + "//*[@class = 'pager-5']//a[contains(text(), '" + pageNumber + "')]"));
    }

    public SelenideElement itemsPerPageDropDownByValue(String value) {
        return $(xpath("//" + paginationSector + "//select/option[@value = " + value + "]"));
    }
}
