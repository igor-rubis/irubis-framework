/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.pageLevelSteps.homePage

import com.irubis_framework.pages.homePage.HomePage
import com.irubis_framework.steps.pageLevelSteps.PageSteps
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 7/29/16.
 */
class HomePageSteps extends PageSteps {
    private HomePage page

    HomePageSteps(WebDriver driver) {
        super(driver)
        page = new HomePage()
    }

    def open() {
        goToUrl(page.url())
    }
}