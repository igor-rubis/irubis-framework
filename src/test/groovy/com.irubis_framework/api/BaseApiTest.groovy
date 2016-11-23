/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.api

import com.irubis_framework.BaseTest
import com.irubis_framework.helpers.configuration.PropertiesProvider

/**
 * Created by Igor_Rubis. 10/6/16.
 */
abstract class BaseApiTest extends BaseTest {
    def final protected BASE_URL = PropertiesProvider.get('url')
}