/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.irubis_framework.helpers.configuration.PropertiesProvider
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    private static ThreadLocal driver
    private static WebDriver webDriver

    private Browser() {}

    static getInstance() {
        if (!driver) {
            def drvr = PropertiesProvider.get('browser')
            def clazz = "org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver"

            driver = new ThreadLocal()
            driver.set(Eval.me("return new ${clazz}()"))
        }
        webDriver = (WebDriver) driver.get()
        return webDriver
    }

    static clear() {
        !webDriver ?: webDriver.quit()
        driver = null
    }
}