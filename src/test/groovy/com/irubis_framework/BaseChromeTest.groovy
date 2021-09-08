package com.irubis_framework

import com.irubis_framework.helpers.browser.Browser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll

abstract class BaseChromeTest {
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
