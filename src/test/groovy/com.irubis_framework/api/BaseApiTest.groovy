package com.irubis_framework.api

import com.irubis_framework.BaseTest
import com.irubis_framework.helpers.configuration.PropertiesProvider
import com.irubis_framework.steps.apiSteps.ApiSteps
import org.junit.Before

/**
 * Created by Igor_Rubis. 10/6/16.
 */
abstract class BaseApiTest extends BaseTest {
    def abstract protected service()
    def protected baseUrl = PropertiesProvider.get('url')
    def protected client
    def protected action
    def protected response

    @Before
    def void createClient() {
        client = new ApiSteps().getSoapClient(baseUrl, service())
    }
}
