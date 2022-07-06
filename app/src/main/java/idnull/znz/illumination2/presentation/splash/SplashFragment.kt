package idnull.znz.illumination2.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.presentation.chat.ChatFragment
import idnull.znz.illumination2.presentation.login.LoginViewModel
import idnull.znz.illumination2.utils.APP_ACTIVITY
import idnull.znz.illumination2.utils.AppPreference
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.splash_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = injectViewModel(factory = viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserAuth()
    }

    private fun checkUserAuth() {
        if (AppPreference.getInitUser()) {
            goToChat()
        } else {
            goToLogin()
        }
    }

    private fun goToLogin() {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                APP_ACTIVITY.navController.navigate(
                    R.id.action_splashFragment_to_loginFragment
                )
            },
            2000
        )
    }

    private fun goToChat() {
        viewModel.initial()

        viewModel.allMessage.observe(viewLifecycleOwner){
            APP_ACTIVITY.navController.navigate(
                R.id.action_splashFragment_to_chatFragment,
                ChatFragment.createArgs(it)
            )
        }
    }
}