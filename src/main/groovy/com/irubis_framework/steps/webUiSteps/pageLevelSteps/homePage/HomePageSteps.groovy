/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.pageLevelSteps.homePage

import com.irubis_framework.pages.homePage.HomePage
import com.irubis_framework.steps.webUiSteps.pageLevelSteps.PageSteps
import ru.yandex.qatools.allure.annotations.Step

import java.util.logging.Logger

/**
 * Created by Igor_Rubis. 7/29/16.
 */
class HomePageSteps extends PageSteps {
    def log = Logger.getAnonymousLogger()

    private HomePage page

    HomePageSteps() {
        page = new HomePage()
    }

    @Step
    def open() {
        log.info('opening the home page')
        goToUrl(page.url())
    }

    def String getTitle() {
        getElementText(page.title)
    }
}