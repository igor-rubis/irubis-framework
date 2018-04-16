package ui

import com.irubis_framework.helpers.testing.test.ui.BaseUiTest
import org.junit.Test

import static com.irubis_framework.helpers.StepsAndActionsProvider.getTestPageActions
import static com.irubis_framework.helpers.StepsAndActionsProvider.getTestPageSteps
import static org.hamcrest.CoreMatchers.is

class ExampleUiTest extends BaseUiTest {
//    @Test
    void failingTest() {
        testPageSteps.openTestPage()
        testPageActions.verifyPageTitle(is('#######'))
    }

//    @Test
    void errorTest() {
        testPageSteps.openTestPage()
        throw new RuntimeException('smth went wrong!')
    }

    @Test
    void pageStepsMethodsTest() {
        testPageSteps.openTestPage()
        testPageActions.verifyPageTitle(is('Google'))
        testPageActions.searchForTerm('Cheese!')
        testPageActions.verifySearchInputCoordinates()
        println()
    }
}