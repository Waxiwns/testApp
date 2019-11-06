// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package steps.detailsPagesSteps;

import io.qameta.allure.Step;
import pages.detailsPages.SystemUserDetailsPage;
import utils.BaseStep;

import static com.codeborne.selenide.Condition.*;
import static pages.detailsPages.SystemUserDetailsPage.*;

public class SystemUserDetailsPageSteps extends BaseStep {

    SystemUserDetailsPage systemUserDetailsPage = new SystemUserDetailsPage();

    @Step
    public void systemUserRolesShouldHaveValue(String roles) {
        systemUserDetailsPage.systemUserRoles.shouldHave(text(roles));
    }

    @Step
    public void systemUserFirstNameShouldHaveValue(String firstName) {
        systemUserDetailsPage.getLabelXpath(FIRST_NAME).shouldBe(visible).shouldHave(text(FIRST_NAME_LABEL));
        systemUserDetailsPage.systemUserFirstName.shouldBe(visible).shouldHave(value(firstName));
    }

    @Step
    public void systemUserLastNameShouldHaveValue(String lastName) {
        systemUserDetailsPage.getLabelXpath(LAST_NAME).shouldBe(visible).shouldHave(text(LAST_NAME_LABEL));
        systemUserDetailsPage.systemUserLastName.shouldBe(visible).shouldHave(value(lastName));
    }

    @Step
    public void systemUserEmailShouldHaveValue(String email) {
        systemUserDetailsPage.getLabelXpath(EMAIL).shouldBe(visible).shouldHave(text(EMAIL_LABEL));
        systemUserDetailsPage.systemUserEmail.shouldBe(visible).shouldHave(value(email));
    }

    @Step
    public void systemUserPhoneShouldHaveValue(String phone) {
        systemUserDetailsPage.getLabelXpath(PHONE).shouldBe(visible).shouldHave(text(PHONE_LABEL));
        systemUserDetailsPage.systemUserPhone.shouldBe(visible).shouldHave(value(phone));
    }


}