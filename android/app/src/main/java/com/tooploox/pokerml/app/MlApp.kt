package com.tooploox.pokerml.app

import android.app.Application
import android.content.Context

class MlApp : Application() {

    lateinit var injection: Injection
    override fun onCreate() {
        super.onCreate()
        injection = Injection(this)
    }
}

val Context.injection: Injection
    get() = (this.applicationContext as MlApp).injection
