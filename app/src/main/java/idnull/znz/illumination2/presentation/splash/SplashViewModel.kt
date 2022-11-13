package idnull.znz.illumination2.presentation.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.domain.FireBaseInit
import idnull.znz.illumination2.domain.FirebaseRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _dataFromDB = MutableLiveData<List<ChatMassage>>()
    val dataFromDB: LiveData<List<ChatMassage>>
        get() = _dataFromDB

    fun loadData() {
        FireBaseInit.refDataBase?.get()
            ?.addOnSuccessListener { snapshot ->
                _dataFromDB.value = snapshot.children.map {
                    it.getValue(ChatMassage::class.java) ?: ChatMassage()
                }
            }
            ?.addOnFailureListener {

            }
    }

    fun initDatabase() {
        firebaseRepository.initDatabase()
    }

}