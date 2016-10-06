/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.ui

import com.irubis_framework.helpers.browser.Browser
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 8/3/16.
 */

@RunWith(AllureTestRunner.class)
abstract class BaseTest {
    WebDriver driver

    @Before
    def void initializeDriver() {
        driver = Browser.getInstance()
    }

    @After
    def void killDriver() {
        driver.quit()
        Browser.clear()
    }
}
