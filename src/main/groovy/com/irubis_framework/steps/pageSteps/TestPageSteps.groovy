package com.irubis_framework.steps.pageSteps

import com.irubis_framework.helpers.browser.Browser
import ru.yandex.qatools.allure.annotations.Step

class TestPageSteps extends PageSteps {
    @Step
    def openTestPage() {
        Browser.instance.get('https://www.google.com/')
    }
}
