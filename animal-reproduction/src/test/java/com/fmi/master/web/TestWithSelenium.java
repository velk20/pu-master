package com.fmi.master.web;

import com.fmi.master.web.model.ArticleWikiPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWithSelenium {
    private static final String SEARCH_BUTTON_XPATH = "//*[@id=\"search-form\"]/fieldset/button";
    private static final String SEARCH_INPUT_ID = "searchInput";
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    public void testHomePage() throws InterruptedException {
        driver.get("https://www.wikipedia.org/");
        String title = driver.getTitle();
        assertEquals("Wikipedia", title);

        List<WebElement> searchInput = driver.findElements(By.id(SEARCH_INPUT_ID));
        assertEquals(1, searchInput.size());

        searchInput.get(0).sendKeys("Bulgaria");

        List<WebElement> searchButton = driver.findElements(By.xpath(SEARCH_BUTTON_XPATH));
        assertEquals(1, searchButton.size());

        searchButton.get(0).click();
        Thread.sleep(500);

        ArticleWikiPage page = new ArticleWikiPage(driver);

        assertEquals("Bulgaria - Wikipedia", page.pageTitle());
        assertEquals("Bulgaria", page.getArticleTitle());

        page.search("Poland");
        Thread.sleep(500);

        assertEquals("Poland - Wikipedia", page.pageTitle());
        assertEquals("Poland", page.getArticleTitle());
    }
}
