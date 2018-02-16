package com.irubis_framework.helpers.currentSession

class CurrentSession {
    private static instance

    private CurrentSession() {}

    static getInstance() {
        instance = instance ?: new HashMap()
    }

    static get(key) {
        getInstance()[key]
    }

    static put(key, value) {
        getInstance()[key] = value
    }

    static clear() {
        instance = null
    }
}
