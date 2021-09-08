package com.irubis_framework

import com.irubis_framework.helpers.browser.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.JavascriptExecutor

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

class BrowserTest extends BaseChromeTest {
    @Test
    void chromeDriverTest() {
        Browser.getInstance()
    }

    @Test
    void chromeOptionsTest() {
        def userAgent = 'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko'
        System.setProperty('chromeOptions', "user-agent=${userAgent}")
        System.setProperty('webdriverNavigatorUndefined', 'true')
        System.setProperty('excludeSwitches', 'enable-automation')
        System.setProperty('turnOffUseAutomationExtension', 'true')
        assertThat(((JavascriptExecutor) Browser.getInstance()).executeScript("return navigator.userAgent"), equalTo(userAgent))
    }
}
