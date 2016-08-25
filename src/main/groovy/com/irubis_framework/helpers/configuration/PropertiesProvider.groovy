/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.configuration

/**
 * Created by Igor_Rubis. 8/4/16.
 */
class PropertiesProvider {
    private PropertiesProvider() {}

    def static get(String prop) {
        String property = System.properties[prop]
        if (property == null) {
            property = FrameworkProperties.getInstance()."${prop}"
        }
        return property
    }
}
