package com.gh.mp3player.thekidszone.model

class MessageModel(var name: String?, var mes: String?, var link:String?,var email: String?){
    override fun toString(): String {
        return "$name-$mes-$link-$email"
    }
}