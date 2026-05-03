package org.core;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.testng.Assert;

@Slf4j
public class HelperFunctions {

    @Inject
    BaseDriver driver;

    public WebDriverWait explicitWait(int timeOut) {
        return new WebDriverWait(driver.getDriver(), Duration.ofSeconds(timeOut));
    }

    public void implicitWait(int timeOut){
        driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
    }

    public void dropDownSelectByVisibleText(WebElement e, String text){
        try{
            new Select(e).selectByVisibleText(text);
        }
        catch(Exception exception){
            log.error("error on visible text : ", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void dropDownSelectByIndex(WebElement e, int index){
        try{
            new Select(e).selectByIndex(index);
        }
        catch(Exception exception){
            log.error("error on select by index", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void dropDownSelectByValue(WebElement e, String value){
        try{
            new Select(e).selectByValue(value);
        }
        catch(Exception exception){
            log.error("error on select by value", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void scrollToElement(WebElement e){
        try{
            Actions act = new Actions(driver.getDriver());
            act.scrollToElement(e).build().perform();
        }
        catch (Exception exception){
            log.error("error on Scrolling to the target element :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void hoverElement(WebElement e){
        try{
            Actions act = new Actions(driver.getDriver());
            act.moveToElement(e).build().perform();
        }
        catch (Exception exception){
            log.error("error on hover to the element :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void doubleClickAction(WebElement e){
        try{
            Actions act = new Actions(driver.getDriver());
            act.doubleClick(e).build().perform();
        }
        catch (Exception exception){
            log.error("error on double click :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void sendKeys(WebElement e, String value) {
        try{
            e.sendKeys(value);
        }
        catch (Exception exception){
            log.error("error on value inception :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void click(WebElement e) {
        try{
            e.click();
        }
        catch (Exception exception){
            log.error("error  on clicking webelement :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void handleSimpleAlert(){
        try{
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.getDriver().switchTo().alert();
            alert.accept();
        }
        catch (Exception exception){
            log.error("error on handling alert :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void handleConfirmationAlert(String value){
        try{
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.getDriver().switchTo().alert();
            if (value.equalsIgnoreCase("accept")) alert.accept();
            else alert.dismiss();
        }
        catch (Exception exception){
            log.error("error on handling alerts :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public void handlePromtAlert(String message, String acceptance){
        try{
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.getDriver().switchTo().alert();
            alert.sendKeys(message);
            if (acceptance.equalsIgnoreCase("accept")) alert.accept();
            else alert.dismiss();
        }
        catch (Exception exception){
            log.error("error on handling alerts :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }

    public String readAlertText(){
        String alertText = "";
        try{
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.getDriver().switchTo().alert();
            alertText = alert.getText();
        }
        catch (Exception exception){
            log.error("error on handling alerts text :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
        return alertText;
    }

    String parentWindow;
    public void switchToNewWindow(int totalWindows){
        /*
        if it is single new tab gets open
         */
        parentWindow = driver.getDriver().getWindowHandle();
        WebDriverWait wait = explicitWait(10);
        wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
        for(String newWindow : driver.getDriver().getWindowHandles()){
            if(!newWindow.equals(parentWindow)){
                driver.getDriver().switchTo().window(newWindow);
                break;
            }
        }
    }

    public void switchToWindowByIndex(int index, int totalWindows){
        parentWindow = driver.getDriver().getWindowHandle();
        WebDriverWait wait = explicitWait(10);
        wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
        List<String> windows = new ArrayList<>(driver.getDriver().getWindowHandles());
        driver.getDriver().switchTo().window(windows.get(index));
    }
    public void switchToWindowByTitle(int title){
        parentWindow = driver.getDriver().getWindowHandle();
        List<String> windows = new ArrayList<>(driver.getDriver().getWindowHandles());
        for(String newWindow : windows){
            driver.getDriver().switchTo().window(newWindow);
            if(driver.getDriver().getTitle().equals(title)){
                break;
            }
        }
    }

    public void existFromNewWindow(){
        driver.getDriver().close();
        driver.getDriver().switchTo().window(parentWindow);
    }

    public void switchToFramebyId(String id){
        driver.getDriver().switchTo().frame(id);
    }

    public void switchToDefault(){
        driver.getDriver().switchTo().defaultContent();
    }

    public void uploadFile(WebElement e, String path){
        /*
        Only if the tags are like <input type ='file'>
        if input taggis hidden then use javaScript executor
         */
        e.sendKeys(path);
    }

    public void uploadFileUsingjavaScript(WebElement e, String path){
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("argument[0].style.display='block');",e);
        e.sendKeys(path);
    }

    public void compareValue(String expected, String actual){
        try{
            Assert.assertEquals(expected, actual);
        }
        catch (Exception exception){
            log.error("error on handling alerts text :", exception);
            ExtentCucumberAdapter.addTestStepLog("failed : "+exception.getMessage());
        }
    }



}
