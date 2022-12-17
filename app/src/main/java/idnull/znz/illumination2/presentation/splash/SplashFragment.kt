package idnull.znz.illumination2.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.domain.FireBaseInit
import idnull.znz.illumination2.presentation.chat.ChatFragment
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.splash_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = injectViewModel(factory = viewModelFactory)

        FireBaseInit.aunt = Firebase.auth
        FireBaseInit.currentUser = Firebase.auth.currentUser
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserAuth()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.apply {
            dataFromDB.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_splashFragment_to_chatFragment,
                    ChatFragment.createArgs(it)
                )
            }
        }
    }

    private fun checkUserAuth() {
        FireBaseInit.currentUser?.let {
            goToChat()
        } ?: run {
            goToLogin()
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
        viewModel.apply {
            initDatabase()
            loadData()
        }
    }
}