package testData

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.pageSteps.PageSteps
import ru.yandex.qatools.allure.annotations.Step

import static testData.StepsAndActionsProvider.getTestPage

class TestPageSteps extends PageSteps {
    @Step
    def openTestPage() {
        Browser.instance.get('https://www.google.com/')
    }

    @Step
    def populateSearchInput(term) {
        typeInto(testPage.searchInput, term)
    }

    @Step
    def clickSearchButton() {
        element(testPage.searchInput).submit()
    }

    def getSearchInputLocation() {
        getElementsLocation(testPage.searchInput)
    }

    @Step
    def scrollPageToSearchInput() {
        scrollPageToElement(testPage.searchInput)
    }

    @Step
    def scrollPageFromSearchInputByOffset(x, y) {
        scrollPageWithOffsetFromElement(testPage.searchInput, x, y)
    }
}