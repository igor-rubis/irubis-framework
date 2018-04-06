/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiActions

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.Actions
import groovy.json.JsonBuilder
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import ru.yandex.qatools.allure.annotations.Attachment

/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class WebUiActions extends Actions {
    protected eventually(interval = WAITING_INTERVAL, closure) {
        eventually(interval, POLLING_INTERVAL, closure)
    }

    protected eventually(interval, pollingInterval, closure) {
        try {
            super.eventually(interval, pollingInterval, closure)
        } catch (Throwable e) {
            takeScreenShot()
            dumpPageSource()
            throw e
        }
    }

    @Attachment(value = 'Page screenshot', type = 'image/png')
    def takeScreenShot() throws IOException {
        return ((TakesScreenshot) Browser.instance).getScreenshotAs(OutputType.BYTES)
    }

    @Attachment(value = 'Page source', type = "text/html")
    def dumpPageSource() {
        return Browser.instance.getPageSource()
    }

    @Attachment(value = 'Console log', type = 'application/json')
    static dumpConsoleLog(logs) {
        return new JsonBuilder(logs).toPrettyString()
    }
}