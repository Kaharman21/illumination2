package idnull.znz.illumination2.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import idnull.znz.illumination2.data.ChatMassage
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        FireBaseInit.aunt?.signInWithEmailAndPassword(email, password)
            ?.addOnSuccessListener {
                Log.d(
                    "SALAM",
                    "FirebaseRepository   signInWithEmailAndPassword    addOnSuccessListener"
                )
                initDatabase()
                onSuccess()
            }
            ?.addOnFailureListener {
                Log.d(
                    "SALAM",
                    "FirebaseRepository   signInWithEmailAndPassword    addOnFailureListener"
                )
                createUserWithEmailAndPassword(
                    email = email,
                    password = password,
                    onSuccess = onSuccess,
                    onFail = onFail
                )
            }
    }

    private fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        FireBaseInit.aunt?.createUserWithEmailAndPassword(email, password)
            ?.addOnSuccessListener {
                Log.d(
                    "SALAM",
                    "FirebaseRepository   createUserWithEmailAndPassword    addOnSuccessListener"
                )
                initDatabase()
                onSuccess()
            }
            ?.addOnFailureListener {
                onFail(it.message.toString())
                Log.d(
                    "SALAM",
                    "FirebaseRepository   createUserWithEmailAndPassword    addOnFailureListener"
                )
            }
    }

    fun initDatabase() {
        FireBaseInit.refDataBase =
            FirebaseDatabase.getInstance().reference
    }

    fun insertMessage(
        text: String,
        onSuccess: (() -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    ) {
        Log.d("SALAMSALAM", "REPOSITORY insert -> email = ${FireBaseInit.currentUser?.email.toString()}    |   text = $text")
        FireBaseInit.refDataBase?.push()?.setValue(
            ChatMassage(name = FireBaseInit.currentUser?.email.toString(), text = text)
        )
            ?.addOnSuccessListener {
                if (onSuccess != null) {
                    onSuccess()
                }
            }
            ?.addOnFailureListener {
                if (onFailure != null) {
                    onFailure()
                }
            }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}


