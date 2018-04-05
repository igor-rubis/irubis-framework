/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.userActionsLevelSteps.navigation

import com.irubis_framework.steps.webUiSteps.WebUiActions
import ru.yandex.qatools.allure.annotations.Step

import static com.irubis_framework.helpers.stepsAndPagesProvider.pageLevelSteps.GetPageLevelSteps.getHomePageSteps
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis. 9/15/16.
 */
class HomePageActions extends WebUiActions {

    @Step
    verifyTitleIs(text) {
        eventually() {
            assertThat(homePageSteps.getTitle(), equalTo(text))
        }
    }

    @Step
    verifyUrlIs(url) {
        eventually() {
            assertThat(homePageSteps.getcurrentUrl(), equalTo(url))
        }
    }
}