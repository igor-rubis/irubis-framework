/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.pageLevelSteps

import com.irubis_framework.steps.Steps
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class PageSteps extends Steps {

    PageSteps(WebDriver driver) {
        super(driver)
    }

    private element(By by) {
        driver.findElement(by)
    }

    protected clickElement(By by) {
        eventually(15000) {
            element(by).click()
        }
    }

    protected goToUrl(String url) {
        eventually {
            driver.navigate().to(url)
        }
    }

    protected abstract open()
}
