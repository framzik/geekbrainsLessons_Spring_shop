package ru.khrebtov.shopuitests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

public class DriverInitializer {
    private static Properties properties = null;

    static {
        try {
            properties = new Properties();
            properties.load(DriverInitializer.class.getClassLoader()
                                                   .getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("enable-automation");
//        options.addArguments("--headless");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-extensions");
//        options.addArguments("--dns-prefetch-disable");
//        options.addArguments("--disable-gpu");
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return new ChromeDriver();
    }

    public static String getProperty(String key) {
        return properties == null ? null : properties.getProperty(key, "");
    }
}
