package com.techsultan.bookxchange.application

import android.app.Application
import sdk.chat.app.firebase.ChatSDKFirebase
import sdk.chat.core.session.ChatSDK
import sdk.chat.firebase.adapter.module.FirebaseModule

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate();

        val module = FirebaseModule.builder()
            .firebaseRootPath
        
        ChatSDKFirebase.quickStart(
            this, "chat", "AIzaSyCd5uVviUdyu8bfX_adcVKc792ShBdTJRo", true )

        ChatSDK.builder()
            .setGoogleMaps("AIzaSyCd5uVviUdyu8bfX_adcVKc792ShBdTJRo")
    }
}