package userApp;

import org.junit.Test;
import steps.userApp.AuthorizationActivitySteps;
import steps.userApp.EnterToYouActivitySteps;
import steps.userApp.GeneralActivitySteps;
import steps.userApp.MoreTabSteps;
import steps.web.DashboardSteps;
import steps.web.LoginSteps;

public class AuthorizedTests extends AppTests {

    String firstTab = "Shopping";

    String country = "Ukraine";

    String phoneNumber = "0630000000";

    String moreTab = "More";

    String firstName = "Tester";

    String secondName = "Test";

    private AuthorizationActivitySteps authorizationSteps = new AuthorizationActivitySteps();

    private GeneralActivitySteps generalSteps = new GeneralActivitySteps();

    private EnterToYouActivitySteps enterSteps = new EnterToYouActivitySteps();

    private MoreTabSteps moreSteps = new MoreTabSteps();

    private LoginSteps loginSteps = new LoginSteps();

    private DashboardSteps dashboardSteps = new DashboardSteps();


    @Test
    public void authorizeAndGoMore() {
        printTestName();

        authorizationSteps.clickStarted();
        enterSteps.enterActivityIsDisplayed();

//        sign in
        enterSteps.signIn(country, phoneNumber);
        generalSteps.activeTab(firstTab);

//        go to More
        generalSteps.chooseTab(moreTab);
        moreSteps.titleActivityShouldBe(moreTab);
        moreSteps.userNameShouldBe(firstName + " " + secondName);
    }

    @Test
//    Set MobileCapabilityType.BROWSER_NAME, "Chrome" in AppiumDriverFactory to run this test
    public void logInWithCorrectValues() {
        printTestName();

        String id = "ejisko@gmail.com";

        String pass = "0000";

        loginSteps.openLoginPage();
        loginSteps.logInWithCorrectValues(id, pass);

        dashboardSteps.closeMsgForm();
        dashboardSteps.logoutBtnIsDisplayed();

        System.out.println("Login successful");
    }
}
