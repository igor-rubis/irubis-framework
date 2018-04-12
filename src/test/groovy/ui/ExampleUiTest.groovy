package ui

import com.irubis_framework.helpers.testing.test.ui.BaseUiTest
import com.irubis_framework.steps.pageSteps.TestPageSteps
import com.irubis_framework.steps.webUiActions.TestPageActions
import org.junit.Test

import static org.hamcrest.CoreMatchers.is

class ExampleUiTest extends BaseUiTest {
    @Test
    void failingTest() {
        new TestPageSteps().openTestPage()
        new TestPageActions().verifyPageTitle(is('#######'))
    }

    @Test
    void passingTest() {
        new TestPageSteps().openTestPage()
        new TestPageActions().verifyPageTitle(is('Google'))
    }

    @Test
    void errorTest() {
        new TestPageSteps().openTestPage()
        throw new RuntimeException('smth went wrong!')
    }
}