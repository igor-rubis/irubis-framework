package com.irubis_framework

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.helpers.testing.test.ui.BaseUiTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll

abstract class BaseChromeTest extends BaseUiTest {
    @BeforeAll
    static void setUp() {
        System.setProperty('testsMode', 'local')
        System.setProperty('browser', 'chrome')
    }

    @AfterEach
    void tearDown() {
        Browser.clear()
    }
}
