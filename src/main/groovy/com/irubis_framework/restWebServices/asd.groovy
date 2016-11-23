package com.irubis_framework.restWebServices

//def closure = {println 'c1'}
def closure = null

if (closure) {
    println 'c2'
    closure()
}
println 'c3'