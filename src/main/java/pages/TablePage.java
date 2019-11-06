// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class TablePage {

    private int getTittlePosition(String titleName) {
        String path = "//table//thead//th";
        ElementsCollection thead = $$(xpath(path));
        for (int i = 0; i != thead.size(); i++) {
            if (thead.get(i).getText().equals(titleName))
                return i + 1;
        }
        return -1;
    }

    public SelenideElement getTableElementCell(String typeElement, String titleName, String lineFilter, String buttonTittle) {
        String startXpath = "//table//tbody//tr[contains(.,'" + lineFilter + "')]/td[" + getTittlePosition(titleName) + "]";
        return $(xpath(startXpath + "//" + typeElement + "[contains(.,'" + buttonTittle + "')]"));
    }

    public SelenideElement getXpathForTableHeaderTitleInTable(String titleName) {
        return $(xpath("//table//thead//th[contains(.,'" + titleName + "')]"));
    }
}
