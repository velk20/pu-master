package com.fmi.master.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWithSelenium {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void testHomePage() {
        driver.get("https://www.wikipedia.org/");
        String title = driver.getTitle();
        assertEquals("Wikipedia", title);

        List<WebElement> searchInput = driver.findElements(By.id("searchInput"));
        assertEquals(1, searchInput.size());
    }
}
