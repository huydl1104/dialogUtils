package com.android.dialoglibrary.dialogMenu

import android.graphics.drawable.Drawable

class CustomItem {
    var id = 0
    var title: String? = null
    var icon: Drawable? = null

    constructor() {}
    constructor(id: Int, title: String?, icon: Drawable?) {
        this.id = id
        this.title = title
        this.icon = icon
    }

}