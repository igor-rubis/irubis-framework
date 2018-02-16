/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.pageLevelSteps.homePage

import com.irubis_framework.steps.webUiSteps.pageLevelSteps.PageSteps
import ru.yandex.qatools.allure.annotations.Step

import java.util.logging.Logger

import static com.irubis_framework.helpers.stepsAndPagesProvider.pages.GetPages.getHomePage

/**
 * Created by Igor_Rubis. 7/29/16.
 */
class HomePageSteps extends PageSteps {
    def log = Logger.getAnonymousLogger()

    @Step
    open() {
        log.info('opening the home page')
        goToUrl(homePage.url())
    }

    String getTitle() {
        getElementText(homePage.title)
    }
}