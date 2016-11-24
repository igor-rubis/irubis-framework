/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps
/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class Steps {
    protected final INTERVAL = 15000

    protected eventually(interval = INTERVAL, closure) {
        long end = new Date().getTime() + interval
        Throwable exception = null

        while (new Date().getTime() <= end) {
            try {
                return closure()
            } catch (Throwable e) {
                exception = e
            }
        }
        throw exception
    }
}