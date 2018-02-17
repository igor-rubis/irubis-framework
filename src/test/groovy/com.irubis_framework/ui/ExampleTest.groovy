/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.ui

import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title

import static com.irubis_framework.helpers.stepsAndPagesProvider.pageLevelSteps.GetPageLevelSteps.getHomePageSteps
import static com.irubis_framework.helpers.stepsAndPagesProvider.userActionsLevelSteps.GetUserActionsLevelSteps.getHomePageActions

/**
 * Created by Igor_Rubis. 7/29/16.
 */

@Description("This is an example test suite")
class ExampleTest extends BaseUiTest {

    @Title("Failing test")
    @Test
    void openHomePage() {
        homePageSteps.open()
        homePageActions.verifyTitleIs('Google_')
    }

    @Title("Passing test")
    @Test
    void openHomePage2() {
        homePageSteps.open()
        homePageActions.verifyTitleIs('Google')
    }

    @Title("Failing test 2")
    @Test
    void openHomePage3() {
        homePageSteps.open()
        homePageActions.verifyTitleIs('Google+')
    }

    @Title("Errored test")
    @Test
    void openHomePage4() {
        homePageSteps.open()
        throw new RuntimeException('Smth went wrong!!!')
    }
}