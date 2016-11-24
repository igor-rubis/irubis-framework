/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.userActionsLevelSteps.navigation

import com.irubis_framework.steps.webUiSteps.WebUiSteps
import ru.yandex.qatools.allure.annotations.Step

import static com.irubis_framework.helpers.stepsProvider.pageLevelSteps.GetPageLevelSteps.homePageSteps
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis. 9/15/16.
 */
class HomePageActions extends WebUiSteps {

    @Step
    def verifyTitleIs(text) {
        eventually() {
            assertThat(homePageSteps().getTitle(), equalTo(text))
        }
    }
}