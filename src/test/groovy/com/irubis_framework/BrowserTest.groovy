package com.irubis_framework

import com.irubis_framework.helpers.browser.Browser
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class BrowserTest {
    @AfterAll
    static void tearDown() {
        Browser.clear()
    }

    @Test
    void chromeOptionsTest() {
        System.setProperty('testsMode', 'local')
        System.setProperty('browser', 'chrome')
        System.setProperty('chromeOptions', '--allow-running-insecure-content,--start-maximized')
        def browser = Browser.instance
        System.out.println()
    }
}
