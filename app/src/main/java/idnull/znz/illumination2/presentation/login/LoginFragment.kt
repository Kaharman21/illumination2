package idnull.znz.illumination2.presentation.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.databinding.LoginFragmentBinding
import idnull.znz.illumination2.domain.FireBaseInit
import idnull.znz.illumination2.presentation.chat.ChatFragment
import idnull.znz.illumination2.utils.makeLinks
import idnull.znz.illumination2.utils.showToast
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.login_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as? App)?.appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = injectViewModel(factory = viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)

        setupUi()
        setupListeners()
        setupObservers()

        checkIsFieldsFilled()
    }

    private fun setupUi() {
        binding.policyTextView.apply {
            text = getString(R.string.privacy_policy)
            makeLinks(
                ContextCompat.getColor(requireContext(), R.color.colorBlueMedium),
                Pair(
                    getString(R.string.privacy_policy_short),
                    View.OnClickListener {
                        openPrivacyPolicy()
                    })
            )
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            signInSuccess.observe(viewLifecycleOwner) {
                goToChat()
            }
            signInFail.observe(viewLifecycleOwner) {
                showErrorDialog(it)
            }
            progress.observe(viewLifecycleOwner) {
                handleProgress(it)
            }
        }
    }

    private fun goToChat() {
        FireBaseInit.currentUser = Firebase.auth.currentUser
        findNavController().navigate(
            R.id.action_loginFragment_to_chatFragment
        )
    }

    private fun showErrorDialog(mess: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(mess)
            .setPositiveButton("Закрыть", object : OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }
            })
            .show()
    }

    private fun setupListeners() {
        binding.enterEmailEditText.addTextChangedListener {
            checkIsFieldsFilled()
        }
        binding.enterPasswordEditText.addTextChangedListener {
            checkIsFieldsFilled()
        }
        binding.policyCheckBox.setOnCheckedChangeListener { _, _ ->
            checkIsFieldsFilled()
        }
        binding.signInButton.setOnClickListener {
            signIn()
        }
        binding.policyCheckBox.text
    }

    private fun signIn() {
        viewModel.signIn(
            email = binding.enterEmailEditText.text.toString(),
            password = binding.enterPasswordEditText.text.toString()
        )
    }

    private fun checkIsFieldsFilled() {
        var allFilled = true

        if (binding.enterEmailEditText.text.isEmpty()) allFilled = false
        if (binding.enterPasswordEditText.text.isEmpty()) allFilled = false
        if (!binding.policyCheckBox.isChecked) allFilled = false

        handleButtonEnable(allFilled)
    }

    private fun handleButtonEnable(isEnable: Boolean) = with(binding) {
        if (isEnable) {
            signInButton.apply {
                isEnabled = true
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
            }
        } else {
            signInButton.apply {
                isEnabled = false
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_enable))
            }
        }
    }

    private fun handleProgress(show: Boolean) {
        if (show) {
            binding.loginProgressBar.isVisible = true
            binding.loginContainer.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.loading
                )
            )
        } else {
            binding.loginProgressBar.isVisible = false
            binding.loginContainer.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }

    private fun openPrivacyPolicy() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL))
        startActivity(intent)
    }

    companion object {
        const val TAG = "TAGTAG"
        private const val PRIVACY_POLICY_URL = "https://raion-nibco.ru.gg/"
        const val RC_SIGN_IN = 9001
    }
}