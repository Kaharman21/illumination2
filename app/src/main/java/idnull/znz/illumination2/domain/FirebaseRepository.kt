package idnull.znz.illumination2.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.utils.*
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val signWithEmailAndPasswordUseCase: SignWithEmailAndPasswordUseCase
){

    init {
        FireBaseInit.aunt = FirebaseAuth.getInstance()
    }

    val allMessage: LiveData<List<ChatMassage>> = AllMessageLiveData()

    fun connectToDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        signWithEmailAndPasswordUseCase.invoke(email, password, onSuccess, onFail)


//        FireBaseInit.aunt.signInWithEmailAndPassword(email, password)
//            .addOnSuccessListener {
//                Log.d("SALAM", "FirebaseRepository   signInWithEmailAndPassword    addOnSuccessListener")
//                initFB()
//                onSuccess()
//            }
//            .addOnFailureListener {
//                Log.d("SALAM", "FirebaseRepository   signInWithEmailAndPassword    addOnFailureListener")
//                FireBaseInit.aunt.createUserWithEmailAndPassword(email, password)
//                    .addOnSuccessListener {
//                        Log.d("SALAM", "FirebaseRepository   createUserWithEmailAndPassword    addOnSuccessListener")
//                        initFB()
//                        onSuccess()
//                    }
//                    .addOnFailureListener {
//                        onFail(it.message.toString())
//                        Log.d("SALAM", "FirebaseRepository   createUserWithEmailAndPassword    addOnFailureListener")
//                    }
//            }
    }



     fun initFB() {
         Log.d("SALAM", "FirebaseRepository   initFB()")
         //  FireBaseInit.currentId = FireBaseInit.aunt.currentUser?.uid.toString()
        FireBaseInit.refDataBase =
            FirebaseDatabase.getInstance().reference
                //.child(FireBaseInit.currentId)
    }


    fun insertMessage(text: String, onSuccess: () -> Unit) {

          FireBaseInit.refDataBase.push().setValue(ChatMassage
              (name = FireBaseInit.aunt.currentUser?.email.toString(),text = text))
              .addOnSuccessListener { onSuccess()  }
              .addOnFailureListener { ShowToast(it.message.toString()) }
    }

    fun signOut() {
        FireBaseInit.aunt.signOut()
        AppPreference.setInitUser(false)
    }

}


