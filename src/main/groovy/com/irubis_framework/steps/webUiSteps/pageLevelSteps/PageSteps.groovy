/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps.pageLevelSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.webUiSteps.WebUiSteps
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class PageSteps extends WebUiSteps {

    def protected abstract open()

    def protected WebElement element(String cssSelector) {
        return Browser.getInstance().findElement(By.cssSelector(cssSelector))
    }

    def protected clickElement(String cssSelector) {
        eventually(15000) {
            element(cssSelector).click()
        }
    }

    def protected goToUrl(String url) {
        Browser.getInstance().navigate().to(url)
    }

    def protected String getElementText(String cssSelector) {
        return ((JavascriptExecutor) Browser.getInstance()).executeScript('return arguments[0].innerHTML', element(cssSelector))
    }
}