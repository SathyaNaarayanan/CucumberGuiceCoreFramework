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
import org.testng.Assert;

@Slf4j
public class HelperFunctions {

    @Inject
    BaseDriver driver;

    @Inject
    SafeExecutor safeExecutor;

    public WebDriverWait explicitWait(int timeOut) {
            return new WebDriverWait(driver.getDriver(), Duration.ofSeconds(timeOut));
    }

    public void implicitWait(int timeOut){
        driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
    }

    public Alert alert(){
        return driver.getDriver().switchTo().alert();
    }

    public void click(WebElement e){ safeExecutor.run(UiActionMethods.CLICK,e,"");}

    public void clear(WebElement e){ safeExecutor.run(UiActionMethods.CLEAR,e,"");}

    public void sendKeys(WebElement e, String value) { safeExecutor.run(UiActionMethods.SEND_KEYS, e, value); }

    public void hoverElement(WebElement e){ safeExecutor.run(UiActionMethods.HOVER,e,""); }

    public void doubleClickAction(WebElement e){ safeExecutor.run(UiActionMethods.DOUBLE_CLICK,e,""); }

    public void scrollToElement(WebElement e){ safeExecutor.run(UiActionMethods.SCROLL_TO_ELEMENT,e,""); }

    public void rightClickAction(WebElement e){ safeExecutor.run(UiActionMethods.CONTEXT_CLICK,e,"");}
    public void dropDownSelectByVisibleText(WebElement e, String text){
        safeExecutor.run(() -> new Select(e).selectByVisibleText(text));
    }

    public void dropDownSelectByIndex(WebElement e, int index){
        safeExecutor.run(() -> new Select(e).selectByIndex(index));
    }

    public void dropDownSelectByValue(WebElement e, String value){
        safeExecutor.run(() -> new Select(e).selectByValue(value));
    }

    public void handleSimpleAlert(){
        safeExecutor.run(()->{
            explicitWait(10).until(ExpectedConditions.alertIsPresent());
            alert().accept();
        });
    }

    public void handleConfirmationAlert(String value){
        safeExecutor.run(()->{
            explicitWait(10).until(ExpectedConditions.alertIsPresent());
            if (value.equalsIgnoreCase("accept")) alert().accept();
            else alert().dismiss();
        });
    }

    public void handlePromtAlert(String message, String acceptance){
        safeExecutor.run(()->{
            explicitWait(10).until(ExpectedConditions.alertIsPresent());
            alert().sendKeys(message);
            if (acceptance.equalsIgnoreCase("accept")) alert().accept();
            else alert().dismiss();
        });
    }

    public String readAlertText(){
        String alertText = safeExecutor.get(() -> {
            explicitWait(10).until(ExpectedConditions.alertIsPresent());
            return alert().getText();
        });
        return alertText;
    }

    String parentWindow;
    public void switchToNewWindow(int totalWindows){
        /*
        if it is single new tab gets open
         */
        safeExecutor.run(() ->{
            parentWindow = driver.getDriver().getWindowHandle();
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
            for(String newWindow : driver.getDriver().getWindowHandles()){
                if(!newWindow.equals(parentWindow)){
                    driver.getDriver().switchTo().window(newWindow);
                    break;
                }
            }
        });

    }

    public void switchToWindowByIndex(int index, int totalWindows){
        safeExecutor.run(() -> {
            parentWindow = driver.getDriver().getWindowHandle();
            WebDriverWait wait = explicitWait(10);
            wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
            List<String> windows = new ArrayList<>(driver.getDriver().getWindowHandles());
            driver.getDriver().switchTo().window(windows.get(index));
        });
    }
    public void switchToWindowByTitle(String title){
        safeExecutor.run(() ->{
            parentWindow = driver.getDriver().getWindowHandle();
            List<String> windows = new ArrayList<>(driver.getDriver().getWindowHandles());
            for(String newWindow : windows){
                driver.getDriver().switchTo().window(newWindow);
                if(driver.getDriver().getTitle().equals(title)){
                    break;
                }
            }
        });

    }

    public void existFromNewWindow(){
        safeExecutor.run(() ->{
            driver.getDriver().close();
            driver.getDriver().switchTo().window(parentWindow);
        });
    }

    public void switchToFramebyId(String id){
        safeExecutor.run(()->{
            driver.getDriver().switchTo().frame(id);
        });
    }

    public void switchToDefault(){
        safeExecutor.run(() -> {
            driver.getDriver().switchTo().defaultContent();
        });
    }

    public void uploadFile(WebElement e, String path){
        /*
        Only if the tags are like <input type ='file'>
        if input taggis hidden then use javaScript executor
         */
        safeExecutor.run(() -> {
            safeExecutor.run(UiActionMethods.SEND_KEYS, e, path);
        });

    }

    public void uploadFileUsingjavaScript(WebElement e, String path){
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("arguments[0].style.display='block');",e);
        safeExecutor.run(UiActionMethods.SEND_KEYS, e, path);
    }

    public void compareValue(String expected, String actual){
        safeExecutor.run(() -> {
            Assert.assertEquals(expected, actual);
        });
    }

    public String getText(WebElement e){
        return safeExecutor.apply(s -> s.getText(), e);
    }

    public String getTagName(WebElement e){
        return safeExecutor.apply(s -> s.getTagName(), e);
    }

    public int getSize(List<WebElement> e){
        return safeExecutor.apply(s -> s.size(),e);
    }

    public boolean elementIsDisplayed(WebElement e){
        return safeExecutor.test(s -> s.isDisplayed(), e);
    }

    public boolean elementIsSelected(WebElement e){
        return safeExecutor.test(s -> s.isSelected(), e);
    }

    public boolean elementIsEnabled(WebElement e){
        return safeExecutor.test(s -> s.isEnabled(), e);
    }

    public void returnData(Object data){
        safeExecutor.accept(e -> e.toString(), data);
    }


    public void moveToOffset(WebElement slider, int offset){

        safeExecutor.run(()-> {
            new Actions(driver.getDriver())
                    .clickAndHold(slider)
                    .moveByOffset(offset, 0)
                    .release()
                    .perform();
        });
    }

}
