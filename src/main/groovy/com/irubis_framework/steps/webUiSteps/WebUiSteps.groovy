/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.Steps
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import ru.yandex.qatools.allure.annotations.Attachment

/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class WebUiSteps extends Steps {
    @Override
    protected eventually(interval = INTERVAL, closure) {
        eventually(interval, POLLING_INTERVAL, closure)
    }

    @Override
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
        return ((TakesScreenshot)Browser.getInstance()).getScreenshotAs(OutputType.BYTES)
    }

    @Attachment(value = 'Page source', type = "text/html")
    def dumpPageSource() {
        return Browser.getInstance().getPageSource()
    }
}