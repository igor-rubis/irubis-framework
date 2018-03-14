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

    protected abstract open()

    protected WebElement element(By by) {
        return Browser.instance.findElement(by)
    }

    protected clickElement(By by) {
        eventually() {
            element(by).click()
        }
    }

    protected clickVisibleElement(By by) {
        eventually() {
            Browser.instance.findElements(by).find { element ->
                element.isDisplayed()
            }.click()
        }
    }

    protected goToUrl(String url) {
        Browser.instance.navigate().to(url)
    }

    protected String getElementText(By by) {
        return ((JavascriptExecutor) Browser.instance).executeScript('return arguments[0].innerHTML', element(by)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
    }

    def typeInto(By by, String text) {
        eventually() {
            element(by).clear()
            element(by).sendKeys(text)
        }
    }

    def jumpToIFrame(By by) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(by))
        }
    }
    def getcurrentUrl() {
        Browser.instance.currentUrl
    }
}