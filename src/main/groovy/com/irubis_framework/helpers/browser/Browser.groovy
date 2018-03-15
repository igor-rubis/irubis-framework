/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.irubis_framework.helpers.jvmProperties.JVMProperties
import com.machinepublishers.jbrowserdriver.JBrowserDriver
import com.machinepublishers.jbrowserdriver.Settings
import com.machinepublishers.jbrowserdriver.UserAgent
import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

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
            def drvr = JVMProperties.BROWSER

            switch (JVMProperties.TESTS_MODE) {
                case 'mobile':
                    def capabilitiesMap = """[
                        'browserName' : '',
                        'deviceName'  : '',
                        'platformName': 'Android'
                        ]"""
                    def capabilities = "org.openqa.selenium.remote.DesiredCapabilities(${capabilitiesMap})"

                    WEB_DRIVER = Eval.me("return new org.openqa.selenium.remote.RemoteWebDriver(new URL('${JVMProperties.MOBILE_HUB_URL}'), new ${capabilities})")
                    break
                case 'remote':
                    WEB_DRIVER = Eval.me("""return new org.openqa.selenium.remote.RemoteWebDriver(
                                                            new URL('${JVMProperties.UI_HUB_URL}'),
                                                            new org.openqa.selenium.${drvr}.${drvr.capitalize()}Options()
                                                        )""")
                    WEB_DRIVER.manage().window().maximize()
                    break
                case 'local':
                    try {
                        switch (drvr) {
                            case [FIREFOX, CHROME]: WEB_DRIVER = Eval.me("return new org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver()"); break
                            case J_BROWSER: WEB_DRIVER = Eval.me("""return new ${JBrowserDriver.canonicalName}(
                                                                    ${Settings.canonicalName}.builder()
                                                                        .hostnameVerification(false)
                                                                        .userAgent(${UserAgent.canonicalName}.CHROME)
                                                                        .build()
                                                                        )"""); break
                        }
                    } catch (IllegalStateException ignored) {
                        switch (drvr) {
                            case CHROME: ChromeDriverManager.instance.setup(); break
                            case FIREFOX: FirefoxDriverManager.instance.setup(); break
                        }
                        instance
                    }
                    break
                case 'electron':
                    try {
                        def options = new ChromeOptions()
                        options.setBinary(JVMProperties.ELECTRON_BINARY)
                        options.setCapability('browserName', 'electron')
                        WEB_DRIVER = new ChromeDriver(options)
                    } catch (IllegalStateException ignored) {
                        ChromeDriverManager.instance.setup()
                        instance
                    }
                    break
            }
        }
        return WEB_DRIVER
    }

    static clear() {
        try {
            if (WEB_DRIVER) {
                WEB_DRIVER.quit()
            }
        } catch (WebDriverException ignored) {
        } finally {
            WEB_DRIVER = null
        }
    }
}