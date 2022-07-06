package idnull.znz.illumination2.presentation.splash

import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.domain.FirebaseRepository
import idnull.znz.illumination2.utils.ShowToast
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    val allMessage = firebaseRepository.allMessage

    fun initial() {
        firebaseRepository.initFB()
    }

}