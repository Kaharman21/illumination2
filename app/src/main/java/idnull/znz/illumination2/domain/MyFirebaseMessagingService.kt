package idnull.znz.illumination2.domain

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        Log.d("TAGMYSERVICI","my token:  $p0")
    }
}