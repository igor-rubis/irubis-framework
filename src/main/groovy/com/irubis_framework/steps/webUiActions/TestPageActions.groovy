package com.irubis_framework.steps.webUiActions

import com.irubis_framework.steps.pageSteps.TestPageSteps
import ru.yandex.qatools.allure.annotations.Step

import static org.hamcrest.MatcherAssert.assertThat

class TestPageActions extends WebUiActions {
    @Step
    def verifyPageTitle(matcher) {
        eventually() {
            assertThat(new TestPageSteps().getPageTitle(), matcher)
        }
    }
}