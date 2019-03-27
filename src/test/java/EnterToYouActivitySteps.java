public class EnterToYouActivitySteps {

    private EnterToYouActivity enter = new EnterToYouActivity();


    public void enterActivityIsDisplayed(){
        enter.enterToYouIsDisplayed();
    }

    public void closeEnter(){
        enterActivityIsDisplayed();
        enter.close().click();
    }
}