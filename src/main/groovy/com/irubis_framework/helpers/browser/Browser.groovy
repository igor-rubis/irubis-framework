/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.irubis_framework.helpers.configuration.PropertiesProvider
import com.irubis_framework.helpers.threadSafeObject.ThreadSafeObject
import com.machinepublishers.jbrowserdriver.JBrowserDriver
import com.machinepublishers.jbrowserdriver.Settings
import com.machinepublishers.jbrowserdriver.UserAgent
import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    private static webDriver

    private Browser() {}

    static WebDriver getInstance() {
        if (!webDriver) {
            def drvr = PropertiesProvider.get('browser')

            try {
                def clazz
                def coercionClass
                switch (drvr) {
                    case ['firefox', 'chrome'] : (clazz, coercionClass) = ["org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver", "org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver"]; break
                    case 'jBrowser' : (clazz, coercionClass) = ["""${JBrowserDriver.canonicalName}(
                                                                    ${Settings.canonicalName}.builder()
                                                                        .hostnameVerification(false)
                                                                        .userAgent(${UserAgent.canonicalName}.CHROME)
                                                                        .build())""", JBrowserDriver.canonicalName]; break
                }
                webDriver = ThreadSafeObject.create(clazz, coercionClass)
            } catch (IllegalStateException ignored) {
                switch (drvr) {
                    case 'chrome': ChromeDriverManager.getInstance().setup(); break
                }
                getInstance()
            }
        }
        return webDriver
    }

    static clear() {
        !webDriver ?: webDriver.quit()
        webDriver = null
    }
}