package com.irubis_framework.steps.pageSteps

import com.irubis_framework.helpers.browser.Browser
import ru.yandex.qatools.allure.annotations.Step

import static com.irubis_framework.helpers.StepsAndActionsProvider.getTestPage

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

    def getSearchInputCoordinates() {
        getElementCoordinates(testPage.searchInput)
    }
}