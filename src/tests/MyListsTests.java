package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.closeLoginToSyncPopup();
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticles() // Ex.11 Рефакторинг тестов
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        // add the 1st article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();
        System.out.println("The first article has been added");

        // add the 2nd article
        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchField();

        SearchPageObject.typeSearchLine("Swift");
        SearchPageObject.clickByArticleWithSubstring("General-purpose, multi-paradigm, " +
                "compiled programming language");

        String second_article_title = "Swift (programming language)";

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();
        System.out.println("The second article has been added");

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        MyListsPageObject.closeLoginToSyncPopup();

        List<String> ArticlesBeforeDelete = MyListsPageObject.addArticlesToList();

        System.out.println(ArticlesBeforeDelete.size());

        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(first_article_title);

        System.out.println("The first article was deleted");

        List<String> ArticlesAfterDelete = MyListsPageObject.addArticlesToList();

        System.out.println(ArticlesAfterDelete.size());

        System.out.println("Before " + ArticlesBeforeDelete.get(0));
        System.out.println("After " + ArticlesAfterDelete.get(0));


        Assert.assertTrue("Articles does not match",
                ArticlesBeforeDelete.get(0) == ArticlesAfterDelete.get(0));

        Assert.assertTrue("It seems that we don't delete an article",
                ArticlesAfterDelete.size() == 1 && ArticlesBeforeDelete.size() == 2);

    }

}
