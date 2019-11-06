// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package orders;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.annotations.Bug;

import static com.codeborne.selenide.Selenide.sleep;
import static core.TestStepLogger.logPreconditionStep;
import static core.TestStepLogger.logStep;

@Log4j
public class LoginPageTest extends BaseTest {

    @BeforeEach
    public void goToLoginPage() {
        logPreconditionStep(1, "Open 'Login' page");
        configure();
    }

    @Test
    @Description("LogIn with correct credentials")
    public void loginWithCorrectCredentialsTest() {
        logStep(1, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logStep(2, "User authorization check");
        headerPageSteps.usersPhotoShouldBeVisible();
    }

    @Test
    @Description("Login with incorrect password")
    public void loginWithIncorrectPasswordTest() {
        logStep(2, "Enter invalid Pass");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.incorrectPassword());

        logStep(3, "Check that user still on Login page");
        loginPageSteps.loginErrorShouldHaveText("en");
        loginPageSteps.checkVisibilityOfSubmitButton();

        logStep(4, "Check error message in Log In block");
        loginPageSteps.checkErrorMessageInLogInBlock();
    }

    @Test
    @Description("Check translated error message")
    public void checkTranslatedErrorMessage() {
        logStep(2, "Switch language to Arabic ");
        loginPageSteps.switchLanguageTo("ar");

        logStep(3, "Enter invalid Pass");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.incorrectPassword());

        logStep(4, "Check that user still on Login page");
        loginPageSteps.loginErrorShouldHaveText("ar");
    }

    @Test
    @Description("logIn button is inactive if any data are not entered")
    public void logInButtonIsInactiveIfAnyDataAreNotEnteredTest() {
        sleep(10000);

        logStep(2, "Check that Log in button is inactive if any data aren't entered");
        loginPageSteps.checkThatSubmitButtonIsDisabled();
    }

    @Test
    @Description("Check placeholder input fields")
    public void checkPlaceholderInputFields() {
        logStep(2, "I check placeholder input fields");
        loginPageSteps.placeholderOfEmailInputShouldBe();
        loginPageSteps.placeholderOfPassInputShouldBe();
    }

    @Bug
    @Issue("UIB-3058")
    @Test
    @Description("Check translation warning message")
    public void checkTranslationWarningMessage() {
        logStep(2, "Switch language to Arabic ");
        loginPageSteps.switchLanguageTo("ar");
        loginPageSteps.checkArabicTranslationAndAlignElements();

        logStep(3, "Enter valid credentials");
        loginPageSteps.authorizationStep(testInitValues.adminEmail(), testInitValues.password());

        logStep(4, " Check that user Logged in");
        headerPageSteps.usersPhotoShouldBeVisible();
    }
}
