package ui

import com.irubis_framework.helpers.testing.test.ui.BaseUiTest
import org.junit.Test
import org.openqa.selenium.Point

import static org.hamcrest.CoreMatchers.is
import static testData.StepsAndActionsProvider.getTestPageActions
import static testData.StepsAndActionsProvider.getTestPageSteps

class ExampleUiTest extends BaseUiTest {
    @Test
    void failingTest() {
        testPageSteps.openTestPage()
        testPageActions.verifyPageTitle(is('#######'))
    }

    @Test
    void errorTest() {
        testPageSteps.openTestPage()
        throw new RuntimeException('smth went wrong!')
    }

    @Test
    void pageStepsMethodsTest() {
        testPageSteps.openTestPage()
        testPageActions.searchForTerm('Cheese!')
        testPageActions.verifySearchInputCoordinates(is(new Point(166, 25)))
        testPageSteps.scrollPageToBottom()
        testPageSteps.scrollPageToSearchInput()
        testPageSteps.scrollPageFromSearchInputByOffset(0, 300)
        testPageSteps.scrollPageWithOffset(0, 300)
        testPageSteps.scrollPageToTop()
        println()
    }
}