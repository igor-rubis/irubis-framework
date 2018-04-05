/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.testing.test.ui

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.helpers.testing.test.BaseTest
import org.junit.After

/**
 * Created by Igor_Rubis. 8/3/16.
 */

abstract class BaseUiTest extends BaseTest {
    @After
    void tearDown() {
        Browser.clear()
        super.tearDown()
    }
}