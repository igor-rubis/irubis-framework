/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.configuration

/**
 * Created by Igor_Rubis. 8/3/16.
 */

class FrameworkProperties {
    private static Properties instance

    private FrameworkProperties() { }

    static Properties getInstance() {
        if (!instance) {
            ThreadLocal trdlcl = new ThreadLocal()
            trdlcl.set(new Properties())
            instance = (Properties) trdlcl.get()
            new File('./src/test/groovy/resources/framework.properties').withInputStream {
                instance.load(it)
            }
        }
        return instance
    }
}