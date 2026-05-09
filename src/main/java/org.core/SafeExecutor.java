package org.core;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.guice.ScenarioScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

/*
package-private means NO modifier. Outside the package cannot use this class methods
 */

@ScenarioScoped
@Slf4j
class SafeExecutor {

    @Inject
    BaseDriver driver;

    @Inject
    ExecuteActions executeActions;

    public void run(UiActionMethods action, WebElement e, String value){
        try{
            WebDriverWait wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(10));
            executeActions.uiActions(wait, action, e, value);
        }
        catch(Exception exception){
            log.error("error on value inception :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public void run(Runnable action){
        try{
            action.run();
        }
        catch(Exception exception){
            log.error("error on value inception :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public <T> T runWithReturnResult(Supplier<T> action){
        try{
            return action.get();
        }
        catch(Exception exception){
            log.error("error on value inception :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

}
