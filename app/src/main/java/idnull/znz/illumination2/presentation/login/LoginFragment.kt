package idnull.znz.illumination2.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.utils.APP_ACTIVITY
import idnull.znz.illumination2.utils.AppPreference
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.databinding.LoginFragmentBinding
import javax.inject.Inject

class LoginFragment : Fragment() {


    private var _binding: LoginFragmentBinding? = null
    private val mBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as? App)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = injectViewModel(factory = viewModelFactory)

        mBinding.login.setOnClickListener {
            AppPreference.setInitUser(true)
            val login = mBinding.email.text.toString()
            val password = mBinding.password.text.toString()
            viewModel.initDataBase(login, password) {
                APP_ACTIVITY.navController.navigate(R.id.action_loginFragment_to_chatFragment)
            }
        }

        mBinding.privacyPolicyText.setOnClickListener {
            openPrivacyPolicy()
        }
    }

    private fun openPrivacyPolicy(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL))
        startActivity(intent)
    }

    companion object {
        private const val PRIVACY_POLICY_URL = "https://raion-nibco.ru.gg/"
    }
}