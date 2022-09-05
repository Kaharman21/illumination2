package idnull.znz.illumination2.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import idnull.znz.illumination2.data.ChatMassage

class AllMessageLiveData : LiveData<List<ChatMassage>>() {

    private val mAuth = FirebaseAuth.getInstance()
    private val mDataBaseReference = FirebaseDatabase.getInstance().reference

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            value = snapshot.children.map {
                Log.d("MYTAG", "respond:  $it")
                it.getValue(ChatMassage::class.java) ?: ChatMassage()
            }
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onActive() {
        mDataBaseReference.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        mDataBaseReference.removeEventListener(listener)
        super.onInactive()
    }
}