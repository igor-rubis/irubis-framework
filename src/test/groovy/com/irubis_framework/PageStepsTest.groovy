package com.irubis_framework

import com.irubis_framework.steps.pageSteps.PageSteps
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue

class PageStepsTest extends BaseChromeTest {
    class TestPageSteps extends PageSteps {

    }
    TestPageSteps testPageSteps
    def text = 'test'
    def css = '[name="q"]'
    def xpath = '//*[@name="q"]'
    def invalidLocator = 'name="q"'

    @BeforeEach
    void setUpEach() {
        System.setProperty('waitingInterval', '2000')
        testPageSteps = new TestPageSteps()
        testPageSteps.goToUrl('https://google.com/ncr')
    }

    @Test
    void locatorTest() {
        testPageSteps.typeInto(css, text)
        testPageSteps.typeInto(xpath, text)
        assertThrows(RuntimeException.class, { testPageSteps.typeInto(invalidLocator, text) });
    }

    @Test
    void compositeLocatorTest() {
        testPageSteps.typeInto([css], 'test')
        def parentCss = '[action="/search"]'
        def parentXpath = '//*[@action="/search"]'
        testPageSteps.typeInto([parentCss, css], 'test')
        testPageSteps.typeInto([parentXpath, css], 'test')
        testPageSteps.typeInto([parentCss, xpath], 'test')
        testPageSteps.typeInto([parentXpath, xpath], 'test')
        assertThrows(RuntimeException.class, { testPageSteps.typeInto([parentCss, invalidLocator], text) });
        assertThrows(RuntimeException.class, { testPageSteps.typeInto([parentXpath, invalidLocator], text) });
    }

    @Test
    void webElementsTest() {
        testPageSteps.typeInto(css, text)
        assertTrue(testPageSteps.elements('div').size() > 1)
        assertTrue(testPageSteps.elements(['//*[@action="/search"]', 'div']).size() > 1)
    }
}
