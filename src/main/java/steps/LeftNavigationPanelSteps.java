// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps;

import io.qameta.allure.Step;
import pages.LeftNavigationPanel;
import utils.BaseStep;

public class LeftNavigationPanelSteps extends BaseStep {

    private LeftNavigationPanel leftNavigationPanel = new LeftNavigationPanel();

    @Step
    public void clickOrdersButtonStep() {
        leftNavigationPanel.ordersBtn.click();
    }

    @Step
    public void clickCustomersButtonStep() {
        leftNavigationPanel.customersBtn.click();
    }

    @Step
    public void clickTransactionButtonStep() {
        leftNavigationPanel.transactionBtn.click();
    }

    @Step
    public void clickDashboardStep() {
        leftNavigationPanel.dashboardBtn.click();
    }
}
