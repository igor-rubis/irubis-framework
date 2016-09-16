/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.userActionsLevelSteps.navigation

import com.irubis_framework.steps.webUiSteps.WebUiSteps
import com.irubis_framework.steps.webUiSteps.pageLevelSteps.homePage.HomePageSteps
import org.openqa.selenium.WebDriver
import ru.yandex.qatools.allure.annotations.Step

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis. 9/15/16.
 */
class HomePageActions extends WebUiSteps{
    private HomePageSteps steps

    HomePageActions(WebDriver driver) {
        super(driver)
        steps = new HomePageSteps(driver)
    }

    @Step
    def verifyTitleIs(text) {
        eventually() {
            assertThat(steps.getTitle(), equalTo(text))
        }
    }
}