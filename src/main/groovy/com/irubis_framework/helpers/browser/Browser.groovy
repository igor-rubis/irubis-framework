/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.browser

import com.irubis_framework.helpers.configuration.PropertiesProvider
import com.irubis_framework.helpers.threadSafeObject.ThreadSafeObject

/**
 * Created by Igor_Rubis. 8/3/16.
 */
class Browser {
    def private static webDriver

    private Browser() {}

    static getInstance() {
        if (!webDriver) {
            def drvr = PropertiesProvider.get('browser')
            def clazz = "org.openqa.selenium.${drvr}.${drvr.capitalize()}Driver"
            webDriver = ThreadSafeObject.create(clazz)
        }
        return webDriver
    }

    static clear() {
        !webDriver ?: webDriver.quit()
        webDriver = null
    }
}