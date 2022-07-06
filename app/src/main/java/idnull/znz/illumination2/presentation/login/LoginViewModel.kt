package idnull.znz.illumination2.presentation.login

import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.utils.ShowToast
import idnull.znz.illumination2.domain.FirebaseRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {
//    fun initial() {
//        repository.initFB()
//    }

    fun initDataBase(email: String, password: String, onSuccess: () -> Unit) {
        repository.connectToDatabase(email, password, { onSuccess() }, { ShowToast(it) })
    }


}