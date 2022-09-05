package idnull.znz.illumination2.domain

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import idnull.znz.illumination2.data.ChatMassage
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    init {
        FireBaseInit.aunt = FirebaseAuth.getInstance()
    }

    val allMessage: LiveData<List<ChatMassage>> = AllMessageLiveData()

    fun initDatabase() {
        FireBaseInit.refDataBase =
            FirebaseDatabase.getInstance().reference
    }

    fun insertMessage(text: String, onSuccess: (() -> Unit)? = null, onFailure: (() -> Unit)? = null) {
        FireBaseInit.refDataBase.push().setValue(
            ChatMassage(name = FireBaseInit.aunt.currentUser?.email.toString(), text = text)
        )
            .addOnSuccessListener {
                if (onSuccess != null) {
                    onSuccess()
                }
            }
            .addOnFailureListener {
                if (onFailure != null) {
                    onFailure()
                }
            }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}


