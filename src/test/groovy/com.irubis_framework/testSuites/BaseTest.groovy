/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.testSuites

import com.irubis_framework.helpers.currentSession.CurrentSession
import com.irubis_framework.testRunner.AllureTestRunner
import org.junit.After
import org.junit.runner.RunWith

/**
 * Created by Igor_Rubis. 8/3/16.
 */

@RunWith(AllureTestRunner.class)
abstract class BaseTest {
    @After
    void tearDown() {
        CurrentSession.clear()
    }
}