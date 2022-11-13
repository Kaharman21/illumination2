package idnull.znz.illumination2.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.domain.FireBaseInit
import idnull.znz.illumination2.domain.FirebaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            _dataFromDB.value = snapshot.children.map {
                it.getValue(ChatMassage::class.java) ?: ChatMassage()
            }
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    private val _dataFromDB = MutableLiveData<List<ChatMassage>>()
    val dataFromDB: LiveData<List<ChatMassage>>
        get() = _dataFromDB

    fun signOut(){
        firebaseRepository.signOut()
    }

    fun insertMessage(text:String, onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null){
        viewModelScope.launch {
            firebaseRepository.insertMessage(text, onSuccess, onFailure)
        }
    }

    fun listenDataFromDB() {
        FireBaseInit.refDataBase?.addValueEventListener(listener)
    }

    private fun cancelListenDataFromDB() {
        FireBaseInit.refDataBase?.removeEventListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        cancelListenDataFromDB()
    }
}