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

    static get(String prop) {
        return System.properties[prop] ?: FrameworkProperties.getInstance()."${prop}"
    }
}
