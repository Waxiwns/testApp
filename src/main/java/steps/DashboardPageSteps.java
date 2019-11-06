// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import io.qameta.allure.Step;
import pages.DashboardPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.visible;

public class DashboardPageSteps extends BaseStep {

    private DashboardPage dashboardPage = new DashboardPage();

    @Step
    public void dashboardTitleShouldBeVisible() {
        dashboardPage.dashboardTitle.shouldBe(visible);
    }
}
