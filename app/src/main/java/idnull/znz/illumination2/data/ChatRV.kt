package idnull.znz.illumination2.data


sealed class ChatRV
data class ChatOtherMassage (val name:String, val text:String): ChatRV()
data class ChatMeMassage (val name:String, val text:String):ChatRV()