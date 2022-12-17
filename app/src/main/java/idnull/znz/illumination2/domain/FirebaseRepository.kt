package idnull.znz.illumination2.domain

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
                initDatabase()
                onSuccess()
            }
            ?.addOnFailureListener {
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
                initDatabase()
                onSuccess()
            }
            ?.addOnFailureListener {
                onFail(it.message.toString())
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


