package com.irubis_framework.steps.webUiActions

import ru.yandex.qatools.allure.annotations.Step

import static com.irubis_framework.helpers.StepsAndActionsProvider.getTestPageSteps
import static org.hamcrest.MatcherAssert.assertThat

class TestPageActions extends WebUiActions {
    @Step
    def verifyPageTitle(matcher) {
        eventually() {
            assertThat(testPageSteps.getPageTitle(), matcher)
        }
    }

    @Step
    def searchForTerm(term) {
        testPageSteps.populateSearchInput(term)
        testPageSteps.clickSearchButton()
    }

    @Step
    def verifySearchInputCoordinates() {
        eventually() {
            assertThat(testPageSteps.getSearchInputCoordinates(), matcher)
        }
    }
}