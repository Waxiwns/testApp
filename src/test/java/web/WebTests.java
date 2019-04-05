package web;

import com.codeborne.selenide.Configuration;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.close;

public class WebTests {

    Boolean headless = true;


    @Before
    public void setConfiguration(){
//        console messages
        BasicConfigurator.configure();

        Configuration.headless = headless;
    }

    public void printTestName(){
        System.out.println("Test " + new Throwable()
                .getStackTrace()[1]
                .getMethodName() + " is running");
    }

    @After
    public void closeBrowser(){
        close();
    }
}
