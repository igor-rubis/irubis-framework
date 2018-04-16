/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.pageSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.webUiActions.WebUiActions
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.internal.Locatable

import static com.irubis_framework.helpers.systemProp.SystemProp.WAITING_INTERVAL

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class PageSteps extends WebUiActions {
    WebElement element(List locators) {
        def chain = 'com.irubis_framework.helpers.browser.Browser.instance'
        locators.each { locator ->
            def locStr = locator.toString()
            chain = "${chain}.findElement(org.openqa.selenium.${locStr.replace(locStr[locStr.indexOf(':') + 2..-1], "\"${locStr[locStr.indexOf(':') + 2..-1]}\")").replace(':', '(')})"
        }
        Eval.me("return ${chain}")
    }

    WebElement element(By by) {
        Browser.instance.findElement(by)
    }

    def elements(By by) {
        Browser.instance.findElements(by)
    }

    void evaluateJavascript(script, Object... args) {
        ((JavascriptExecutor) Browser.getInstance()).executeScript(script, args)
    }

    void clickElement(by) {
        eventually() {
            element(by).click()
        }
    }

    void clickVisibleElement(by) {
        eventually() {
            Browser.instance.findElements(by).find { element ->
                element.isDisplayed()
            }.click()
        }
    }

    void jsClickElement(by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('arguments[0].click();', element(by))
        }
    }

    String getElementAttribute(by, attr, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(by).getAttribute(attr)
        }
    }

    String getElementClassAttribute(by, interval = WAITING_INTERVAL) {
        getElementAttribute(by, 'class', interval)
    }

    String getElementSrcAttribute(by, interval = WAITING_INTERVAL) {
        getElementAttribute(by, 'src', interval)
    }

    void goToUrl(String url) {
        Browser.instance.navigate().to(url)
    }

    String getElementText(by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(by).getText()
        }
    }

    String getElementsInnerHtml(by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].innerHTML', element(by)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
        }
    }

    String getElementTextByJs(by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].value', element(by))
        }
    }

    void typeInto(by, String text) {
        eventually() {
            element(by).clear()
            element(by).sendKeys(text)
        }
    }

    void jumpToIFrame(by) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(by))
        }
    }

    String getcurrentUrl() {
        Browser.instance.currentUrl
    }

    Boolean isDisplayedElement(by) {
        try {
            element(by).isDisplayed()
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false
        }
    }

    void waitForElementToAppear(by) {
        eventually() {
            if (!isDisplayedElement(by)) {
                throw new AssertionError('Element did not appear on the page')
            }
        }
    }

    String getPageTitle() {
        Browser.instance.title
    }

    void scrollPageWithOffset(x, y) {
        evaluateJavascript("window.scrollBy(${x}, ${y});")
    }

    void scrollPageWithOffsetFromElement(by, x, y) {
        def elementLocation = element(by).getLocation()
        evaluateJavascript("window.scroll(${elementLocation.x + x}, ${elementLocation.y + y});")
    }

    void scrollPageToElement(by) {
        evaluateJavascript('arguments[0].scrollIntoView();', element(by))
    }

    def getElementCoordinates(by) {
        ((Locatable) element(by)).getLocationOnScreenOnceScrolledIntoView()
//        ((Locatable) element(by)).getCoordinates()
    }

    void scrollPageToBottom() {
        evaluateJavascript('window.scrollTo(0, document.body.scrollHeight)')
    }
}