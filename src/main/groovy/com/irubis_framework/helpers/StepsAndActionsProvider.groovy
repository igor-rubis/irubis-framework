package com.irubis_framework.helpers

import com.irubis_framework.steps.pageSteps.TestPageSteps
import com.irubis_framework.steps.pages.TestPage
import com.irubis_framework.steps.webUiActions.TestPageActions

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