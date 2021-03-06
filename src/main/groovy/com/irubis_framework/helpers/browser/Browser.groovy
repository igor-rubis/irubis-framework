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

import static com.irubis_framework.helpers.systemProp.SystemProp.*

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    private static WEB_DRIVER

    private Browser() {}

    static WebDriver getInstance() {
        if (!WEB_DRIVER) {
            def testsModes = [
                    mobile: 'mobile',
                    remote: 'remote',
                    local : 'local'
            ]
            def browsers = [
                    firefox : 'firefox',
                    chrome  : 'chrome',
                    jBrowser: 'jBrowser',
                    electron: 'electron',
            ]

            try {
                switch (TESTS_MODE) {
                    case testsModes.mobile:
                        def capabilitiesMap = """[
                        'browserName' : '',
                        'deviceName'  : '',
                        'platformName': 'Android'
                        ]"""
                        def capabilities = "org.openqa.selenium.remote.DesiredCapabilities(${capabilitiesMap})"

                        WEB_DRIVER = Eval.me("return new org.openqa.selenium.remote.RemoteWebDriver(new URL('${MOBILE_HUB_URL}'), new ${capabilities})")
                        break
                    case testsModes.remote:
                        WEB_DRIVER = Eval.me("""return new org.openqa.selenium.remote.RemoteWebDriver(
                                                            new URL('${UI_HUB_URL}'),
                                                            new org.openqa.selenium.${BROWSER}.${BROWSER.capitalize()}Options()
                                                        )""")
                        WEB_DRIVER.manage().window().maximize()
                        break
                    case testsModes.local:
                        switch (BROWSER) {
                            case [browsers.firefox, browsers.chrome]: WEB_DRIVER = Eval.me("return new org.openqa.selenium.${BROWSER}.${BROWSER.capitalize()}Driver()"); break
                            case browsers.jBrowser: WEB_DRIVER = Eval.me("""return new ${JBrowserDriver.canonicalName}(
                                                                    ${Settings.canonicalName}.builder()
                                                                        .hostnameVerification(false)
                                                                        .userAgent(${UserAgent.canonicalName}.CHROME)
                                                                        .build()
                                                                        )"""); break
                            case browsers.electron:
                                ChromeOptions options = new ChromeOptions()
                                options.setBinary(ELECTRON_BINARY)

                                WEB_DRIVER = new ChromeDriver(options)
                                break
                            default: throw new RuntimeException("Please specify 'browser' system property. Available options are: ${browsers.values()}")
                        }
                        break
                    default: throw new RuntimeException("Please specify 'testsMode' system property. Available options are: ${testsModes.values()}")
                }
            } catch (IllegalStateException ignored) {
                switch (BROWSER) {
                    case [browsers.chrome, browsers.electron]: ChromeDriverManager.instance.setup(); break
                    case browsers.firefox: FirefoxDriverManager.instance.setup(); break
                }
                instance
            }
        }
        return WEB_DRIVER
    }

    static void clear() {
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