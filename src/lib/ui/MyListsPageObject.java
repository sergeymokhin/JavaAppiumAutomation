package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        ARTICLES_LIST;

    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByName(String article_title)
    {
         return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name" + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByName(article_title);
//        System.out.println(article_xpath);
//        System.out.println(article_title);
        this.waitForElementPresent(article_xpath,
                "Cannot find saved article by title '" + article_title + "'", 10);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByName(article_title);
        this.waitForElementNotPresent(article_xpath,
                "Saved article still present with title" + article_title, 10);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByName(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        this.waitForArticleToDisappearByTitle(article_title );
    }

    public void clickOnArticleByTitle(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByName(article_title);
        waitForElementAndClick(article_xpath, "I cannot click on article title", 5);
    }

    public void closeLoginToSyncPopup()
    {
        this.waitForElementAndClick("id:places auth close", "Cannot close Login To Sync popup",
                5);
    }

    public List<String> addArticlesToList()
    {
        List<String> articles = new LinkedList<>();

        By by = getLocatorByString(ARTICLES_LIST);
        List<WebElement> elements = driver.findElements(by);
        for(WebElement element : elements) {
            articles.add(element.getAttribute("name"));
        }
        return articles;
    }

}
