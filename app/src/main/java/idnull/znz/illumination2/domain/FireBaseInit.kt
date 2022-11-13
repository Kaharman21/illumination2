package idnull.znz.illumination2.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

object FireBaseInit {
    var aunt: FirebaseAuth? = null
    var refDataBase: DatabaseReference? = null
    var currentUser: FirebaseUser? = null
}



