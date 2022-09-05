package idnull.znz.illumination2.presentation.login

import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.domain.FirebaseRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    fun initDatabase() {
        firebaseRepository.initDatabase()
    }
}