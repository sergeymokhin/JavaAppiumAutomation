package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "id:org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
        EXISTING_LIST_NAME_TPL = "xpath://*[@text='{LIST_NAME}']";


    private static String getExistingListName(String name_of_folder)
    {
        return EXISTING_LIST_NAME_TPL.replace("{LIST_NAME}", name_of_folder);
    }

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find " +
                "article title on page", 10);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToNewList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can not find button to open article options",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find add to reading list in article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not click option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Can not find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void openMyListByListName(String name_of_folder)
    {
        String existing_list_xpath = getExistingListName(name_of_folder);

        this.waitForElementPresent(existing_list_xpath,
                "Cannot find my previously created list", 5
        );

        this.waitForElementAndClick(
                existing_list_xpath,
                "Cannot click existing list name",
                5
        );
    }

    public void addNewArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can not find button to open article options",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not find add to reading list in article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can not click option to add article to reading list",
                5
        );

        this.openMyListByListName(name_of_folder);
    }

    public void swipeElementToLeftToDelete(String first_article_title)
    {
        String existing_list_xpath = getExistingListName(first_article_title);
        this.swipeElementToLeft(
                existing_list_xpath,
                "Cannot find saved article to delete"
        );
    }

    public void makeSureArticleWasDeleted(String first_article_title)
    {
        String existing_list_xpath = getExistingListName(first_article_title);
        this.waitForElementNotPresent(
                existing_list_xpath,
                "Cannot delete saved article",
                5
        );
    }

    public void makeSureArticlePresentByTitle(String second_article_title)
    {
        String existing_list_xpath = getExistingListName(second_article_title);
        this.waitForElementPresent(
                existing_list_xpath,
                "Cannot find non deleted article",
                5
        );
    }

    public void clickOnUndeletedArticle(String second_article_title) //aka open article
    {
        String existing_list_xpath = getExistingListName(second_article_title);
        this.waitForElementAndClick(existing_list_xpath,
                "Can not click on non deleted article",
                5
                );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Can not close article, can not find X link",
                5
        );
    }

    public String assertElementPresent(String locator, String error_message)
    {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements == 0) {
            Assert.fail("An element '" + locator + "' is absent.");
        }
        return error_message;
    }

    public void assertElementNotPresent()
    {
        this.assertElementPresent(
                TITLE,
                "Element is Present, but we did not expected it"
        );
    }



}
