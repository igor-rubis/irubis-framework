/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps

import com.irubis_framework.helpers.currentSession.CurrentSession
import groovy.json.JsonBuilder
import groovy.transform.PackageScope
import org.apache.commons.lang.exception.ExceptionUtils
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.allure.annotations.Step

import static com.irubis_framework.helpers.systemProp.SystemProp.POLLING_INTERVAL
import static com.irubis_framework.helpers.systemProp.SystemProp.WAITING_INTERVAL

/**
 * Created by Igor_Rubis. 7/29/16.
 */
@PackageScope
abstract class Actions {
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
        throw exception
    }

    @Step
    protected void waitABit(timestamp, interval) {
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
    String dumpCurrentSession() {
        new JsonBuilder(CurrentSession.instance).toPrettyString()
    }

    @Attachment(value = 'Stack trace', type = 'text/plain')
    String dumpStackTrace(exception) {
        ExceptionUtils.getStackTrace(exception)
    }
}