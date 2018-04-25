package com.irubis_framework.helpers.currentSession

class CurrentSession {
    private static instance

    private CurrentSession() {}

    static private getInstance() {
        instance = instance ?: new HashMap()
    }

    static Object get(Object key) {
        getInstance()[key]
    }

    static void put(Object key, Object value) {
        getInstance()[key] = value
    }

    static void clear() {
        instance = null
    }
}