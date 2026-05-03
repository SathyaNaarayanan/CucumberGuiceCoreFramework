package org.core;

import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Map;
import java.util.function.Supplier;

@ScenarioScoped
public class BaseDriver {

    private WebDriver driver;

    /*
    Supplier is a lazy initialization.
    Only lambda are stored (no driver created)
    driver created only when .get() is used.
     */
    final Map<String, Supplier<WebDriver>> driverMap = Map.of(

        "chrome", () -> new ChromeDriver(chromeOpt()),
        "edge", () -> new EdgeDriver()
    );

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver initDriver(String browserType) {
        if (driver == null) {
            driver = switch(browserType.toLowerCase()) {
                case "chrome" -> driverMap.get(browserType).get();
                case "edge" -> driverMap.get(browserType).get();
                default -> throw new IllegalStateException("Unexpected value: " + browserType);
            };
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