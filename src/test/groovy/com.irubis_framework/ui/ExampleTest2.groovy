/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.ui

import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title

import static com.irubis_framework.helpers.stepsProvider.pageLevelSteps.GetPageLevelSteps.homePageSteps
import static com.irubis_framework.helpers.stepsProvider.userActionsLevelSteps.GetUserActionsLevelSteps.homePageActions

/**
 * Created by Igor_Rubis. 7/29/16.
 */

@Description("This is an example test suite")
class ExampleTest2 extends BaseUiTest {

    @Title("Failing test")
    @Test
    def void openHomePage() {
        homePageSteps().open()
        homePageActions().verifyTitleIs('Google_')
    }

    @Title("Passing test")
    @Test
    def void openHomePage2() {
        homePageSteps().open()
        homePageActions().verifyTitleIs('Google')
    }

    @Title("Failing test")
    @Test
    def void openHomePage3() {
        homePageSteps().open()
        homePageActions().verifyTitleIs('Google+')
    }

    @Title("Errored test")
    @Test
    def void openHomePage4() {
        homePageSteps().open()
        throw new RuntimeException('Smth went wrong!!!')
    }
}