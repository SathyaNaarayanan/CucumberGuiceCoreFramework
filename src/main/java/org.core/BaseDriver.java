package org.core;

import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@ScenarioScoped
public class BaseDriver {


    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver initDriver() {
        if (driver == null) {
            driver = new ChromeDriver(chromeOpt());
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private ChromeOptions chromeOpt() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return options;
    }
}