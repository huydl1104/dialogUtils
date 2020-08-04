package com.ydl.dialog.bean

 class DialogInfo {
     private var logo: String? = null
     private var name: String? = null
     private var title: String? = null

      constructor(
         logo: String?,
         name: String?,
         title: String?
     ) {
         this.logo = logo
         this.name = name
         this.title = title
     }

     fun getLogo(): String? {
         return logo
     }

     fun setLogo(logo: String?) {
         this.logo = logo
     }

     fun getName(): String? {
         return name
     }

     fun setName(name: String?) {
         this.name = name
     }

     fun getTitle(): String? {
         return title
     }

     fun setTitle(title: String?) {
         this.title = title
     }
}