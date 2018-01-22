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
                                                            org.openqa.selenium.remote.DesiredCapabilities.${drvr}()
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
                            case CHROME: ChromeDriverManager.getInstance().setup(); break
                            case FIREFOX: FirefoxDriverManager.getInstance().setup(); break
                        }
                        getInstance()
                    }
                    break
            }
        }
        return WEB_DRIVER
    }

    static clear() {
        !WEB_DRIVER ?: WEB_DRIVER.quit()
        WEB_DRIVER = null
    }
}