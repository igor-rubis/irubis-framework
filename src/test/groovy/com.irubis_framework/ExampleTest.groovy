/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework

import com.irubis_framework.steps.pageLevelSteps.homePage.HomePageSteps
import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis. 7/29/16.
 */

@Description("This is an example test suite")
class ExampleTest extends BaseTest{
    HomePageSteps homePage

    @Title("Failing test")
    @Test
    def void openHomePage() {
        homePage = new HomePageSteps(driver)
        homePage.open()
        assertThat(1, equalTo(2))
    }

    @Title("Passing test")
    @Test
    def void openHomePage2() {
        homePage = new HomePageSteps(driver)
        homePage.open()
        assertThat(1, equalTo(1))
    }

    @Title("Failing test")
    @Test
    def void openHomePage3() {
        homePage = new HomePageSteps(driver)
        homePage.open()
        assertThat(1, equalTo(2))
    }

    @Title("Failing test")
    @Test
    def void openHomePage4() {
        homePage = new HomePageSteps(driver)
        homePage.open()
        assertThat(1, equalTo(2))
    }
}