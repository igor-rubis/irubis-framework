/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.stepsProvider.userActionsLevelSteps

import com.irubis_framework.steps.webUiSteps.userActionsLevelSteps.navigation.HomePageActions

/**
 * Created by Igor_Rubis, 11/23/2016.
 */
class GetUserActionsLevelSteps {
    def private static homePageActions

    def static homePageActions() {
        homePageActions = homePageActions ?: new HomePageActions()
    }
}