/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.helpers.currentSession.CurrentSession
import groovy.json.JsonBuilder
import org.apache.commons.lang.exception.ExceptionUtils
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.allure.annotations.Step

/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class Actions {
    def WAITING_INTERVAL = System.getProperty('waitingInterval', '15000') as Integer
    def POLLING_INTERVAL = System.getProperty('pollingInterval', '500') as Integer

    protected eventually(interval = WAITING_INTERVAL, closure) {
        eventually(interval, POLLING_INTERVAL, closure)
    }

    protected eventually(interval, pollingInterval, closure) {
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
        dumpStackTrace(exception)
        dumpCurrentSession()
        dumpConsoleLog()
        throw exception
    }

    @Step
    protected waitABit(timestamp, interval) {
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
    def dumpCurrentSession() {
        new JsonBuilder(CurrentSession.instance).toPrettyString()
    }

    @Attachment(value = 'Stack trace', type = 'text/plain')
    def dumpStackTrace(exception) {
        ExceptionUtils.getStackTrace(exception)
    }

    @Attachment(value = 'Console log', type = 'application/json')
    def dumpConsoleLog() {
        try {
            def logs = Browser.instance.manage().logs()
            def json = [
                    browser: logs.get('browser'),
                    driver : logs.get('driver'),
                    client : logs.get('client')
            ]
            return new JsonBuilder(json).toPrettyString()
        } catch (Throwable ignored) {
            return 'Could not parse console logs'
        }
    }
}