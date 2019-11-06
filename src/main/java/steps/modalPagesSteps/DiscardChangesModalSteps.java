// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.modalPagesSteps;

import io.qameta.allure.Step;
import pages.modalPages.DiscardChangesModal;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static pages.modalPages.DiscardChangesModal.TITLE;

public class DiscardChangesModalSteps extends BaseStep {

    private DiscardChangesModal discardChangesModal = new DiscardChangesModal();

    @Step
    public void buttonYesShouldBeVisible() {
        discardChangesModal.buttonYes.shouldNotBe(disabled);
    }

    @Step
    public void buttonYesClick() {
        buttonYesShouldBeVisible();
        discardChangesModal.buttonYes.shouldBe(visible).click();
    }

    @Step
    public void buttonNoShouldBeVisible() {
        discardChangesModal.buttonNo.shouldBe(visible).shouldNotBe(disabled);
    }

    @Step
    public void buttonNoClick() {
        buttonNoShouldBeVisible();
        discardChangesModal.buttonNo.click();
    }

    @Step
    public void bodyContentShouldBeVisible() {
        discardChangesModal.bodyContent.shouldBe(visible);
    }

    @Step
    public void titleModalWindowShouldBeVisible() {
        discardChangesModal.titleModalWindow.shouldBe(visible);
    }

    @Step
    public void titleModalWindowShouldNotBeVisible() {
        discardChangesModal.titleModalWindow.shouldNotBe(visible);
    }

    @Step
    public void titleModalWindowShouldHaveText() {
        titleModalWindowShouldBeVisible();
        discardChangesModal.titleModalWindow.shouldHave(text(TITLE));
    }

    @Step
    public void crossButtonShouldBeVisible() {
        discardChangesModal.crossButton.shouldBe(visible).shouldNotBe(disabled);
    }

    @Step
    public void crossButtonClick() {
        crossButtonShouldBeVisible();
        discardChangesModal.crossButton.click();
    }

    @Step
    public void checkDiscardChangesDialogueAndClickOnTab() { //It is need for passing tests, because notification appear randomly. (Bug)
        if (discardChangesModal.titleModalWindow.is(visible)) {
            buttonYesClick();
            titleModalWindowShouldNotBeVisible();
        }
    }
}
