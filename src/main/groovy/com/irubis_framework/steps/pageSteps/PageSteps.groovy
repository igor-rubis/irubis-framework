/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.pageSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.webUiActions.WebUiActions
import io.qameta.allure.Step
import org.openqa.selenium.*

import static com.irubis_framework.helpers.systemProp.SystemProp.WAITING_INTERVAL

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class PageSteps extends WebUiActions {
    private applyBy(String locator, Closure closure) {
        try {
            closure(By.cssSelector(locator))
        } catch (InvalidSelectorException ignored) {
            try {
                closure(By.xpath(locator))
            } catch (InvalidSelectorException ignored2) {
                throw new RuntimeException("Incorrect locator `${locator}`. Please use valid `cssSelector`, or `xpath`.")
            }
        }
    }

    WebElement element(String locator) {
        applyBy(locator) { by ->
            Browser.instance.findElement(by)
        }
    }

    WebElement element(List<String> locators) {
        WebElement elem
        for (locator in locators) {
            elem = elem ? applyBy(locator) { by -> elem.findElement(by) } : element(locator)
        }
        elem
    }

    List<WebElement> elements(String locator) {
        applyBy(locator) { by ->
            Browser.instance.findElements(by)
        }
    }

    List<WebElement> elements(List<String> locators) {
        applyBy(locators.last()) { by ->
            element(locators[0..-2]).findElements(by)
        }
    }

    void evaluateJavascript(script, Object... args) {
        ((JavascriptExecutor) Browser.getInstance()).executeScript(script, args)
    }

    @Step('Click element: {locator}')
    void clickElement(String locator) {
        eventually() {
            element(locator).click()
        }
    }

    @Step('Click element: {locators}')
    void clickElement(List<String> locators) {
        eventually() {
            element(locators).click()
        }
    }

    @Step('Click visible element: {locator}')
    void clickVisibleElement(String locator) {
        eventually() {
            elements(locator).find { element -> element.isDisplayed() }.click()
        }
    }

    @Step('Click visible element: {locators}')
    void clickVisibleElement(List<String> locators) {
        eventually() {
            elements(locators).find { element -> element.isDisplayed() }.click()
        }
    }

    @Step('Click element with JS: {locator}')
    void jsClickElement(String locator, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('arguments[0].click();', element(locator))
        }
    }

    @Step('Click element with JS: {locators}')
    void jsClickElement(List<String> locators, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('arguments[0].click();', element(locators))
        }
    }

    @Step('Get element aatribute `{attr}`: {locator}')
    String getElementAttribute(String locator, attr, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(locator).getAttribute(attr)
        }
    }

    @Step('Get element aatribute `{attr}`: {locators}')
    String getElementAttribute(List<String> locators, attr, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(locators).getAttribute(attr)
        }
    }

    String getElementClassAttribute(List<String> locators, interval = WAITING_INTERVAL) {
        getElementAttribute(locators, 'class', interval)
    }

    String getElementClassAttribute(String locator, interval = WAITING_INTERVAL) {
        getElementAttribute(locator, 'class', interval)
    }

    String getElementSrcAttribute(List<String> locators, interval = WAITING_INTERVAL) {
        getElementAttribute(locators, 'src', interval)
    }

    String getElementSrcAttribute(String locator, interval = WAITING_INTERVAL) {
        getElementAttribute(locator, 'src', interval)
    }

    String getElementValueAttribute(List<String> locators, interval = WAITING_INTERVAL) {
        getElementAttribute(locators, 'value', interval)
    }

    String getElementValueAttribute(String locator, interval = WAITING_INTERVAL) {
        getElementAttribute(locator, 'value', interval)
    }

    @Step('Open url: {url}')
    void goToUrl(String url) {
        Browser.instance.navigate().to(url)
    }

    @Step('Get element text: {locator}')
    String getElementText(String locator, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(locator).getText()
        }
    }

    @Step('Get element text: {locators}')
    String getElementText(List<String> locators, interval = WAITING_INTERVAL) {
        eventually(interval) {
            element(locators).getText()
        }
    }

    @Step('Get element inner HTML: {locator}')
    String getElementsInnerHtml(String locator, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].innerHTML', element(locator)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
        }
    }

    @Step('Get element inner HTML: {locators}')
    String getElementsInnerHtml(List<String> locators, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].innerHTML', element(locators)).toString().replaceAll('\n', '').replaceAll('\t', '').trim()
        }
    }

    @Step('Get element text with JS: {locator}')
    String getElementTextByJs(String locator, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].value', element(locator))
        }
    }

    @Step('Get element text with JS: {locators}')
    String getElementTextByJs(List<String> locators, interval = WAITING_INTERVAL) {
        eventually(interval) {
            evaluateJavascript('return arguments[0].value', element(locators))
        }
    }

    @Step('Populate `{locator}` input: {text}')
    void typeInto(String locator, String text) {
        eventually() {
            element(locator).clear()
            element(locator).sendKeys(text)
        }
    }

    @Step('Populate `{locators}` input: {text}')
    void typeInto(List<String> locators, String text) {
        eventually() {
            element(locators).clear()
            element(locators).sendKeys(text)
        }
    }

    @Step('Jump into frame: {locator}')
    void jumpToIFrame(String locator) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(locator))
        }
    }

    @Step('Jump into frame: {locators}')
    void jumpToIFrame(List<String> locators) {
        eventually() {
            Browser.instance.switchTo().defaultContent()
            Browser.instance.switchTo().frame(element(locators))
        }
    }

    String getcurrentUrl() {
        Browser.instance.currentUrl
    }

    Boolean isDisplayedElement(String locator) {
        try {
            element(locator).isDisplayed()
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false
        }
    }

    Boolean isDisplayedElement(List<String> locators) {
        try {
            element(locators).isDisplayed()
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false
        }
    }

    @Step('Wait for element to appear: {locator}')
    void waitForElementToAppear(String locator) {
        eventually() {
            if (!isDisplayedElement(locator)) {
                throw new AssertionError('Element did not appear on the page')
            }
        }
    }

    @Step('Wait for element to appear: {locators}')
    void waitForElementToAppear(List<String> locators) {
        eventually() {
            if (!isDisplayedElement(locators)) {
                throw new AssertionError('Element did not appear on the page')
            }
        }
    }

    String getPageTitle() {
        Browser.instance.title
    }

    @Step('Scroll page by offset: {x}:{y}')
    void scrollPageWithOffset(x, y) {
        evaluateJavascript("window.scrollBy(${x}, ${y});")
    }

    @Step('Scroll page by offset from element `{locator}`: {x}:{y}')
    void scrollPageWithOffsetFromElement(String locator, x, y) {
        def elementLocation = getElementsLocation(locator)
        evaluateJavascript("window.scroll(${elementLocation.x + x}, ${elementLocation.y + y});")
    }

    @Step('Scroll page by offset from element `{locators}`: {x}:{y}')
    void scrollPageWithOffsetFromElement(List<String> locators, x, y) {
        def elementLocation = getElementsLocation(locators)
        evaluateJavascript("window.scroll(${elementLocation.x + x}, ${elementLocation.y + y});")
    }

    @Step('Scroll page to element: {locator}')
    void scrollPageToElement(String locator) {
        evaluateJavascript('arguments[0].scrollIntoView();', element(locator))
    }

    @Step('Scroll page to element: {locators}')
    void scrollPageToElement(List<String> locators) {
        evaluateJavascript('arguments[0].scrollIntoView();', element(locators))
    }

    Point getElementsLocation(String locator) {
        eventually() {
            element(locator).getLocation()
        } as Point
    }

    Point getElementsLocation(List<String> locators) {
        eventually() {
            element(locators).getLocation()
        } as Point
    }

    @Step('Scroll page to bottom')
    void scrollPageToBottom() {
        evaluateJavascript('window.scrollTo(0, document.body.scrollHeight);')
    }

    @Step('Scroll page to top')
    void scrollPageToTop() {
        evaluateJavascript('window.scrollTo(0, 0);')
    }

    @Step('Confirm alert')
    void confirmAlert() {
        eventually() {
            Browser.instance.switchTo().alert().accept()
        }
    }

    @Step('Disable modal windows')
    void dontShowModalWindow() {
        evaluateJavascript('window.showModalDialog = window.open;')
    }

    @Step('Switch browser tab by index: {index - 1}')
    void switchToBrowserTabByIndex(int index) {
        eventually() {
            Browser.instance.switchTo().window(Browser.instance.getWindowHandles()[index - 1])
        }
    }
}