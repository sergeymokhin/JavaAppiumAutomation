package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line= "Linking Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amountOfSearchResults = SearchPageObject.getAmountOfFindArticles();

        assertTrue(
                "We found too few results",
                amountOfSearchResults > 0
        );

    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "qwessdgcf";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCancelOfSearch() // Ex.3 Тест: отмена поиска
    {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Swift";
        SearchPageObject.typeSearchLine(search_line);
        int amountOfSearchResultsBeforeWeCancelSearch = SearchPageObject.getAmountOfFindArticles();

        assertTrue(
                "We found too few results",
                amountOfSearchResultsBeforeWeCancelSearch > 0
        );

        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertSearchInputIsPresentAndEmpty();
    }

    @Test
    public void testSearchResultsContainsMultipleMatching() // Ex.4 Тест: проверка слов в поиске
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Nirvana";
        SearchPageObject.assertSearchResultsContainsMultipleMatchingOfSearchedText(search_line);
    }


}
