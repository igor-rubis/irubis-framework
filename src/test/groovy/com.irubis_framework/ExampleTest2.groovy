/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework

import com.irubis_framework.steps.pageLevelSteps.homePage.HomePageSteps
import com.irubis_framework.steps.userActionsLevelSteps.navigation.HomePageActions
import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title

/**
 * Created by Igor_Rubis. 7/29/16.
 */

@Description("This is an example test suite")
class ExampleTest2 extends BaseTest{

    @Title("Failing test")
    @Test
    def void openHomePage() {
        def homePage = new HomePageSteps(driver)
        homePage.open()
        def homePageActions = new HomePageActions(driver)
        homePageActions.verifyTitleIs('Google_')
    }

    @Title("Passing test")
    @Test
    def void openHomePage2() {
        def homePage = new HomePageSteps(driver)
        homePage.open()
        def homePageActions = new HomePageActions(driver)
        homePageActions.verifyTitleIs('Google')
    }

    @Title("Failing test")
    @Test
    def void openHomePage3() {
        def homePage = new HomePageSteps(driver)
        homePage.open()
        def homePageActions = new HomePageActions(driver)
        homePageActions.verifyTitleIs('Google+')
    }

    @Title("Failing test")
    @Test
    def void openHomePage4() {
        def homePage = new HomePageSteps(driver)
        homePage.open()
        def homePageActions = new HomePageActions(driver)
        homePageActions.verifyTitleIs('Googl')
    }
}