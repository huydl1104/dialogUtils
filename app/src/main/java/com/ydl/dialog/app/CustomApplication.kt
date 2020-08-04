package com.ydl.dialog.app

import android.app.Application
import com.android.dialoglibrary.toast.ToastUtils

class CustomApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}