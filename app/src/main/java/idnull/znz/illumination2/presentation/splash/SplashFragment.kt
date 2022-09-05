package idnull.znz.illumination2.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.presentation.chat.ChatFragment
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.splash_fragment) {

    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = injectViewModel(factory = viewModelFactory)
        mAuth = Firebase.auth
        currentUser = mAuth.currentUser
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserAuth()
    }

    private fun checkUserAuth() {
//        if (currentUser != null) {
//            goToChat()
//        } else {
//            goToLogin()
//        }

        Log.d("TAGTAG", "currentUser = $currentUser")
        currentUser?.let {
            goToChat()
       //     goToLogin()
        } ?: run {
            goToLogin()
        //    goToChat()
        }
    }

    private fun goToLogin() {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                findNavController().navigate(
                    R.id.action_splashFragment_to_loginFragment
                )
            },
            2000
        )
    }

    private fun goToChat() {
        viewModel.initDatabase()

        viewModel.allMessage.observe(viewLifecycleOwner){
            findNavController().navigate(
                R.id.action_splashFragment_to_chatFragment,
                ChatFragment.createArgs(it)
            )
        }
    }
}