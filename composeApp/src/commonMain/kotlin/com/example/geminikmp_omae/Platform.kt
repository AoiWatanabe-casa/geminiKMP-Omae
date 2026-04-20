package com.example.geminikmp_omae

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform