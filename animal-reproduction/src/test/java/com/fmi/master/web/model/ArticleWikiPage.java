package com.fmi.master.web.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticleWikiPage {

    WebDriver driver;

    public ArticleWikiPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#firstHeading > span")
    WebElement articleTitle;

    @FindBy(name = "search")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"searchform\"]/button")
    public WebElement searchButton;

    public void search(String query) {
        searchInput.clear();
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public String getArticleTitle() {
        return articleTitle.getText();
    }

    public String pageTitle() {
        return driver.getTitle();
    }
}
