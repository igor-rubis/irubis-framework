/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.testing.test.ui

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.helpers.testing.test.BaseTest
import com.irubis_framework.steps.webUiActions.WebUiActions
import org.junit.After

/**
 * Created by Igor_Rubis. 8/3/16.
 */

abstract class BaseUiTest extends BaseTest {
    @After
    void tearDown() {
        def browserLogs = Browser.logs()

        if (browserLogs.errorsPresent) {
            WebUiActions.dumpConsoleLog(browserLogs)
        }

        Browser.clear()
        super.tearDown()

        if (browserLogs.errorsPresent && System.getProperty('failOnBrowserConsoleError')) {
            throw new AssertionError('There are some errors in browser console.')
        }
    }
}