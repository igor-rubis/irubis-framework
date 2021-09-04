/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.testing.test

import com.irubis_framework.helpers.currentSession.CurrentSession
import org.junit.jupiter.api.AfterAll

/**
 * Created by Igor_Rubis. 8/3/16.
 */

abstract class BaseTest {
    @AfterAll
    void tearDown() {
        CurrentSession.clear()
    }
}