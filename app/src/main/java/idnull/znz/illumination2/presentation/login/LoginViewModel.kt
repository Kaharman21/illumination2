package idnull.znz.illumination2.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.domain.FirebaseRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _signInSuccess = MutableLiveData<Boolean>()
    val signInSuccess: LiveData<Boolean>
        get() = _signInSuccess

    private val _signInFail = MutableLiveData<String>()
    val signInFail: LiveData<String>
        get() = _signInFail

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    fun signIn(email: String, password: String) {
        _progress.value = true
        firebaseRepository.signInWithEmailAndPassword(email, password,
            onSuccess = {
                _progress.value = false
                _signInSuccess.value = true
            }, onFail = {
                _progress.value = false
                _signInFail.value = it
            })
    }
}