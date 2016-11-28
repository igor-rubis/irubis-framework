/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.helpers.threadSafeObject

/**
 * Created by Igor_Rubis, 11/25/2016.
 */
class ThreadSafeObject {
    def static create(clazz) {
        Eval.me("""
            ThreadLocal trdlcl = new ThreadLocal()
            trdlcl.set(new ${clazz}())
            return (${clazz}) trdlcl.get()
            """)
    }
}