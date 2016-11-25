/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.configuration

/**
 * Created by Igor_Rubis. 8/3/16.
 */

class FrameworkProperties {
    def private static instance

    private FrameworkProperties() {}

    def static getInstance() {
        return instance = instance ?: new ConfigSlurper().parse(new File('src/test/groovy/resources/Properties.groovy').toURL())
    }
}