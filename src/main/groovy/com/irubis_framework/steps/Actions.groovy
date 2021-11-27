/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps

import com.irubis_framework.helpers.currentSession.CurrentSession
import groovy.json.JsonBuilder
import io.qameta.allure.Attachment
import org.apache.commons.lang.exception.ExceptionUtils

import static com.irubis_framework.helpers.systemProp.SystemProp.POLLING_INTERVAL
import static com.irubis_framework.helpers.systemProp.SystemProp.WAITING_INTERVAL

/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class Actions {
    protected eventually(int interval = WAITING_INTERVAL, Closure closure) {
        eventually(interval, POLLING_INTERVAL, closure)
    }

    protected eventually(int interval, int pollingInterval, Closure closure) {
        long end = new Date().getTime() + interval
        Throwable exception = null
        def poll = 0

        while (new Date().getTime() <= end || poll == 0) {
            try {
                ++poll
                return closure()
            } catch (URISyntaxException e) {
                exception = e
                break
            } catch (Throwable e) {
                sleep(pollingInterval)
                exception = e
            }
        }
        attachStackTrace(exception)
        attachCurrentSession()
        throw exception
    }

    protected void waitABit(int timestamp, int interval) {
        eventually(interval) {
            if (timestamp) {
                def current = new Date().getTime() + POLLING_INTERVAL
                def expected = timestamp.getTime() + interval
                if (current < expected) {
                    throw new RuntimeException()
                }
            }
        }
    }

    @Attachment(value = 'Current session', type = 'application/json')
    String attachCurrentSession() {
        new JsonBuilder(CurrentSession.instance).toPrettyString()
    }

    @Attachment(value = 'Stack trace', type = 'text/plain')
    String attachStackTrace(exception) {
        ExceptionUtils.getStackTrace(exception)
    }
}