package com.irubis_framework.api

import com.irubis_framework.BaseTest
import com.irubis_framework.helpers.configuration.PropertiesProvider

/**
 * Created by Igor_Rubis. 10/6/16.
 */
abstract class BaseApiTest extends BaseTest {
    def final protected BASE_URL = PropertiesProvider.get('url')
}