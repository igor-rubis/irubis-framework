package testData

class StepsAndActionsProvider {
    static testPage
    static testPageSteps
    static testPageActions

    static TestPageSteps getTestPageSteps() {
        testPageSteps = testPageSteps ?: new TestPageSteps()
    }

    static TestPageActions getTestPageActions() {
        testPageActions = testPageActions ?: new TestPageActions()
    }

    static TestPage getTestPage() {
        testPage = testPage ?: new TestPage()
    }
}