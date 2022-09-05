package idnull.znz.illumination2.domain

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

@Deprecated("Not NEDDED")
class SignWithEmailAndPasswordUseCase @Inject constructor(){

    @Deprecated("Not NEDDED")
    fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ){
      //  FireBaseInit.aunt.
        FireBaseInit.aunt.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("SALAM", "FirebaseRepository   signInWithEmailAndPassword    addOnSuccessListener")
                initFB()
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("SALAM", "FirebaseRepository   signInWithEmailAndPassword    addOnFailureListener")
                FireBaseInit.aunt.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Log.d("SALAM", "FirebaseRepository   createUserWithEmailAndPassword    addOnSuccessListener")
                        initFB()
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                        Log.d("SALAM", "FirebaseRepository   createUserWithEmailAndPassword    addOnFailureListener")
                    }
            }
    }

    @Deprecated("Not NEDDED")
    private fun initFB() {
        Log.d("SALAM", "FirebaseRepository   initFB()")
        //  FireBaseInit.currentId = FireBaseInit.aunt.currentUser?.uid.toString()
        FireBaseInit.refDataBase =
            FirebaseDatabase.getInstance().reference
        //.child(FireBaseInit.currentId)
    }
}