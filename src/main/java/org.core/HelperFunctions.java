package org.core;

import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class HelperFunctions {


    public void dropDownSelect(WebElement e, String value){
        new Select(e).selectByVisibleText(value);
    }


    public void scrollTOE(BaseDriver driver, WebElement e){
        Actions act = new Actions((WebDriver) driver);
        act.scrollToElement(e).build().perform();
    }
    public void sendKeys(WebElement ele, String value) {
        ele.sendKeys(value);
    }

}
