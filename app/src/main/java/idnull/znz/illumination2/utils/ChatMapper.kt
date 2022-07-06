package idnull.znz.illumination2.utils

import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.data.ChatMeMassage
import idnull.znz.illumination2.data.ChatOtherMassage
import idnull.znz.illumination2.data.ChatRV
import idnull.znz.illumination2.domain.FireBaseInit

class ChatMapper {
    fun map(chatMassage: ChatMassage):ChatRV{



      //  if (chatMassage.name == FireBaseInit.aunt.)

        return when (chatMassage.name) {
            FireBaseInit.aunt.currentUser?.email.toString() -> ChatMeMassage(name = chatMassage.name, text = chatMassage.text.toString())
            else -> ChatOtherMassage(name = chatMassage.name.toString(), text = chatMassage.text.toString())
        }

    }
}