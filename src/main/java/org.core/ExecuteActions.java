package org.core;


import io.cucumber.guice.ScenarioScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
package-private means NO modifier. Outside the package cannot use this class methods
 */
@ScenarioScoped
class ExecuteActions {
    @Inject
    BaseDriver driver;

    private Actions actions() {
        return new Actions(driver.getDriver());
    }

    public void uiActions(WebDriverWait wait, UiActionMethods type, WebElement element, String value){

            switch(type){
                case CLICK -> {
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.click();
                }

                case CLEAR -> {
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.clear();
                }
                case SUBMIT -> {
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.submit();
                }
                case SEND_KEYS -> {
                    wait.until(ExpectedConditions.visibilityOf(element));
                    element.sendKeys(value);
                }
                case DOUBLE_CLICK -> {
                    wait.until(ExpectedConditions.visibilityOf(element));
                    actions().doubleClick(element).build().perform();
                }
                case HOVER -> {
                    wait.until(ExpectedConditions.visibilityOf(element));
                    actions().moveToElement(element).build().perform();
                }
                case JS_CLICK -> {
                    JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
                    js.executeScript("arguments[0].click()", element);
                }
                case SCROLL_TO_ELEMENT -> {
                    actions().scrollToElement(element).build().perform();
                }
                default -> throw new IllegalArgumentException("Invalid action: " + type);
            };
        }
    }
