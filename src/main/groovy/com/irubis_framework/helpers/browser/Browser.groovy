/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import com.machinepublishers.jbrowserdriver.Settings
import com.machinepublishers.jbrowserdriver.UserAgent
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities

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
                            case [browsers.firefox]: WEB_DRIVER = Eval.me("return new org.openqa.selenium.${BROWSER}.${BROWSER.capitalize()}Driver()"); break
                            case [browsers.chrome]:
                                ChromeOptions options = new ChromeOptions()

                                CHROME_OPTIONS.each { option ->
                                    if (option) options.addArguments(option)
                                }

                                def excludeSwitches = []
                                EXCLUDE_SWITCHES.each {
                                    if (it) excludeSwitches << it
                                }
                                if (excludeSwitches.size() > 0) options.setExperimentalOption('excludeSwitches', excludeSwitches)

                                if (TURN_OFF_USE_AUTOMATION_EXTENSION) options.setExperimentalOption('useAutomationExtension', false)

                                DesiredCapabilities capabilities = new DesiredCapabilities()
                                capabilities.setCapability(ChromeOptions.CAPABILITY, options)
                                WEB_DRIVER = new ChromeDriver(capabilities)
                                break
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
                    case [browsers.chrome, browsers.electron]: WebDriverManager.chromedriver().setup(); break
                    case browsers.firefox: WebDriverManager.firefoxdriver().setup(); break
                }
                instance
            }
        }
        if (WEBDRIVER_NAVIGATOR_UNDEFINED) ((JavascriptExecutor) WEB_DRIVER).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined, configurable: true})")
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