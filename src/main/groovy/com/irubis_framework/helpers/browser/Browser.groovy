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
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    private static WEB_DRIVER

    private Browser() {}

    static WebDriver getInstance() {
        if (!WEB_DRIVER) {
            def firefox = 'firefox'
            def chrome = 'chrome'
            def jBrowser = 'jBrowser'
            def electron = 'electron'
            def drvr = System.getProperty('browser')

            try {
                switch (System.getProperty('testsMode')) {
                    case 'mobile':
                        def capabilitiesMap = """[
                        'browserName' : '',
                        'deviceName'  : '',
                        'platformName': 'Android'
                        ]"""
                        def capabilities = "org.openqa.selenium.remote.DesiredCapabilities(${capabilitiesMap})"

                        WEB_DRIVER = Eval.me("return new org.openqa.selenium.remote.RemoteWebDriver(new URL('${System.getProperty('mobileHubUrl')}'), new ${capabilities})")
                        break
                    case 'remote':
                        WEB_DRIVER = Eval.me("""return new org.openqa.selenium.remote.RemoteWebDriver(
                                                            new URL('${System.getProperty('uiHubUrl')}'),
                                                            new org.openqa.selenium.${drvr}.${drvr.capitalize()}Options()
                                                        )""")
                        WEB_DRIVER.manage().window().maximize()
                        break
                    case 'local':
                        switch (drvr) {
                            case [firefox, chrome]: WEB_DRIVER = Eval.me("return new org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver()"); break
                            case jBrowser: WEB_DRIVER = Eval.me("""return new ${JBrowserDriver.canonicalName}(
                                                                    ${Settings.canonicalName}.builder()
                                                                        .hostnameVerification(false)
                                                                        .userAgent(${UserAgent.canonicalName}.CHROME)
                                                                        .build()
                                                                        )"""); break
                            case electron:
                                ChromeOptions options = new ChromeOptions()
                                options.setBinary(System.getProperty('electronBinary'))

                                WEB_DRIVER = new ChromeDriver(options)
                                break
                        }
                        break
                }
            } catch (IllegalStateException ignored) {
                switch (drvr) {
                    case [chrome, electron]: ChromeDriverManager.instance.setup(); break
                    case firefox: FirefoxDriverManager.instance.setup(); break
                }
                instance
            }
        }
        return WEB_DRIVER
    }

    static clear() {
        try {
            if (WEB_DRIVER) {
                WEB_DRIVER.close()
                WEB_DRIVER.quit()
            }
        } catch (WebDriverException ignored) {
        } finally {
            WEB_DRIVER = null
        }
    }
}