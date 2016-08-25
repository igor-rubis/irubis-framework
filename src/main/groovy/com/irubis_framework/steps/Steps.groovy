/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps

import org.openqa.selenium.WebDriver

/**
 * Created by Igor_Rubis. 7/29/16.
 */
abstract class Steps {
    private interval = 15000
    protected WebDriver driver

    public Steps(driver) {
        this.driver = driver
    }

    protected eventually(interval=this.interval, closure) {
        long end = new Date().getTime() + interval;
        Exception exception = null

        while (new Date().getTime() <= end) {
            try {
                return closure()
            } catch (Exception e) {
                exception = e
            }
        }
        throw exception
    }
}
