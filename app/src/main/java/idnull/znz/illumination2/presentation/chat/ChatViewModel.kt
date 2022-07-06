package idnull.znz.illumination2.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import idnull.znz.illumination2.domain.FirebaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    val allMessage = firebaseRepository.allMessage
    fun signOut(){

        firebaseRepository.signOut()
    }


    fun insertMessage(text:String){




        viewModelScope.launch {
            firebaseRepository.insertMessage(text,){}
        }


    }

}