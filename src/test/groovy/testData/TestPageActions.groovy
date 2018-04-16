package testData

import com.irubis_framework.steps.webUiActions.WebUiActions
import ru.yandex.qatools.allure.annotations.Step

import static StepsAndActionsProvider.getTestPageSteps
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
    def verifySearchInputCoordinates(matcher) {
        eventually() {
            assertThat(testPageSteps.getSearchInputLocation(), matcher)
        }
    }
}