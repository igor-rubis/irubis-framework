/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.stepsProvider.pageLevelSteps

import com.irubis_framework.helpers.threadSafeObject.ThreadSafeObject
import com.irubis_framework.steps.webUiSteps.pageLevelSteps.homePage.HomePageSteps

/**
 * Created by Igor_Rubis, 11/23/2016.
 */
class GetPageLevelSteps {
    private static homePageSteps
    static HomePageSteps homePageSteps() {
        homePageSteps = homePageSteps ?: new HomePageSteps()
    }
}