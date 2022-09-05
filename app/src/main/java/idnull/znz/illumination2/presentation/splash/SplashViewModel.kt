package idnull.znz.illumination2.presentation.splash

import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.domain.FirebaseRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    val allMessage = firebaseRepository.allMessage

    fun initDatabase() {
        firebaseRepository.initDatabase()
    }

}