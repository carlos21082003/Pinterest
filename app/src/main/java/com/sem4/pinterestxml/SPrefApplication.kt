package com.sem4.pinterestxml
import android.app.Application

class SPrefApplication:Application() {

    companion object {
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }
}