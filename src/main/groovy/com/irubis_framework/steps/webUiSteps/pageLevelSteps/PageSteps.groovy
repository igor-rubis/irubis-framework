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

    protected WebElement element(List locators) {
        def chain = 'com.irubis_framework.helpers.browser.Browser.instance'
        locators.each { locator ->
            def locStr = locator.toString()
            chain = "${chain}.findElement(org.openqa.selenium.${locStr.replace(locStr[locStr.indexOf(':') + 1..-1], "\"${locStr[locStr.indexOf(':') + 1..-1]}\")").replace(':', '(')})"
        }
        Eval.me("return ${chain}")
    }

    protected WebElement element(By by) {
        Browser.instance.findElement(by)
    }

    protected evaluateJavascript(script, Object... args) {
        ((JavascriptExecutor) Browser.getInstance()).executeScript(script, args)
    }

    protected clickElement(by) {
        eventually() {
            element(by).click()
        }
    }

    protected clickVisibleElement(by) {
        eventually() {
            Browser.instance.findElements(by).find { element ->
                element.isDisplayed()
            }.click()
        }
    }

    protected jsClickElement(by, interval = INTERVAL) {
        eventually(interval) {
            evaluateJavascript('arguments[0].click();', element(by))
        }
    }

    protected String getElementAttribute(by, attr, interval = INTERVAL) {
        eventually(interval) {
            element(by).getAttribute(attr)
        }
    }

    protected String getElementClassAttribute(by, interval = INTERVAL) {
        getElementAttribute(by, 'class', interval)
    }

    protected String getElementSrcAttribute(by, interval = INTERVAL) {
        getElementAttribute(by, 'src', interval)
    }

    protected goToUrl(String url) {
        Browser.instance.navigate().to(url)
    }

    protected String getElementText(by) {
        return ((JavascriptExecutor) Browser.instance).executeScript('return arguments[0].innerHTML', element(by)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
    }

    def getElementTextByJs(by, interval = INTERVAL) {
        eventually(interval) {
            ((JavascriptExecutor) Browser.getInstance()).executeScript('return arguments[0].value', element(by))
        }
    }

    def typeInto(by, String text) {
        eventually() {
            element(by).clear()
            element(by).sendKeys(text)
        }
    }

    def jumpToIFrame(by) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(by))
        }
    }

    def getcurrentUrl() {
        Browser.instance.currentUrl
    }

    def isDisplayedElement(by) {
        try {
            element(by).isDisplayed()
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false
        }
    }

    def waitForElementToAppear(by) {
        eventually() {
            if (!isDisplayedElement(by)) {
                throw new AssertionError('Element did not appear on the page')
            }
        }
    }
}