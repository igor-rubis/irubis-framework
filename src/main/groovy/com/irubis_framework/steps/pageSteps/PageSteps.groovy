/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.pageSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.webUiActions.WebUiActions
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Point
import org.openqa.selenium.WebElement

import static com.irubis_framework.helpers.systemProp.SystemProp.WAITING_INTERVAL
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsNot.not

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class PageSteps extends WebUiActions {
    WebElement element(By by) {
        Browser.instance.findElement(by)
    }

    WebElement element(List<By> locators) {
        WebElement elem
        locators.each { locator ->
            elem = elem ? elem.findElement(locator) : element(locator)
        }
        elem
    }

    List<WebElement> elements(By by) {
        Browser.instance.findElements(by)
    }

    List<WebElement> elements(List<By> locators) {
        element(locators[0..-2]).findElements(locators.last())
    }

    void evaluateJavascript(script, Object... args) {
        ((JavascriptExecutor) Browser.getInstance()).executeScript(script, args)
    }

    void clickElement(By by) {
        eventually() {
            element(by).click()
        }
    }

    void clickVisibleElement(By by) {
        eventually() {
            Browser.instance.findElements(by).find { element ->
                element.isDisplayed()
            }.click()
        }
    }

    void jsClickElement(By by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('arguments[0].click();', element(by))
        }
    }

    String getElementAttribute(By by, attr, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(by).getAttribute(attr)
        }
    }

    String getElementClassAttribute(By by, interval = WAITING_INTERVAL) {
        getElementAttribute(by, 'class', interval)
    }

    String getElementSrcAttribute(By by, interval = WAITING_INTERVAL) {
        getElementAttribute(by, 'src', interval)
    }

    void goToUrl(String url) {
        Browser.instance.navigate().to(url)
    }

    String getElementText(By by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(by).getText()
        }
    }

    String getElementsInnerHtml(By by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].innerHTML', element(by)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
        }
    }

    String getElementTextByJs(By by, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].value', element(by))
        }
    }

    void typeInto(By by, String text) {
        eventually() {
            element(by).clear()
            element(by).sendKeys(text)
            sleep 100
            assertThat(getElementText(by), equalTo(text))
        }
    }

    void jumpToIFrame(By by) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(by))
        }
    }

    String getcurrentUrl() {
        Browser.instance.currentUrl
    }

    Boolean isDisplayedElement(By by) {
        try {
            element(by).isDisplayed()
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false
        }
    }

    void waitForElementToAppear(By by) {
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

    void scrollPageWithOffsetFromElement(By by, x, y) {
        def elementLocation = getElementsLocation(by)
        evaluateJavascript("window.scroll(${elementLocation.x + x}, ${elementLocation.y + y});")
    }

    void scrollPageToElement(By by) {
        evaluateJavascript('arguments[0].scrollIntoView();', element(by))
    }

    Point getElementsLocation(By by) {
        eventually() {
            element(by).getLocation()
        } as Point
    }

    void scrollPageToBottom() {
        evaluateJavascript('window.scrollTo(0, document.body.scrollHeight);')
    }

    void scrollPageToTop() {
        evaluateJavascript('window.scrollTo(0, 0);')
    }

    void confirmAlert() {
        eventually() {
            Browser.instance.switchTo().alert().accept()
        }
    }

    void dontShowModalWindow() {
        evaluateJavascript('window.showModalDialog = window.open;')
    }

    void switchToBrowserTabByIndex(int index) {
        eventually() {
            Browser.instance.switchTo().window(Browser.instance.getWindowHandles()[index - 1])
        }
    }
}