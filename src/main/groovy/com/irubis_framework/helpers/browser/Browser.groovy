/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import com.machinepublishers.jbrowserdriver.Settings
import com.machinepublishers.jbrowserdriver.UserAgent
import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    private static FIREFOX = 'firefox'
    private static CHROME = 'chrome'
    private static J_BROWSER = 'jBrowser'
    private static WEB_DRIVER

    private Browser() {}

    static WebDriver getInstance() {
        if (!WEB_DRIVER) {
            def drvr = System.getProperty('browser')
            def initializeWebDriver

            try {
                switch (drvr) {
                    case [FIREFOX, CHROME] : initializeWebDriver = { Eval.me("return new org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver()") }; break
                    case J_BROWSER : initializeWebDriver = { Eval.me("""return new ${JBrowserDriver.canonicalName}(
                                                                    ${Settings.canonicalName}.builder()
                                                                        .hostnameVerification(false)
                                                                        .userAgent(${UserAgent.canonicalName}.CHROME)
                                                                        .build())""") }; break
                }
                WEB_DRIVER = initializeWebDriver()
            } catch (IllegalStateException ignored) {
                switch (drvr) {
                    case CHROME: ChromeDriverManager.getInstance().setup(); break
                    case FIREFOX: FirefoxDriverManager.getInstance().setup(); break
                }
                getInstance()
            }
        }
        return WEB_DRIVER
    }

    static clear() {
        !WEB_DRIVER ?: WEB_DRIVER.quit()
        WEB_DRIVER = null
    }
}