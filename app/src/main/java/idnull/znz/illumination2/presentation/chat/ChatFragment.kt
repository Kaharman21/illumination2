package idnull.znz.illumination2.presentation.chat

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.utils.ChatMapper
import idnull.znz.illumination2.utils.showToast
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.databinding.ChatFragmentBinding
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class ChatFragment : Fragment(R.layout.chat_fragment) {

    private lateinit var binding: ChatFragmentBinding
    private val args by lazy { arguments?.getParcelable<Args>(KEY_ARGS) }
    private lateinit var recyclerView:RecyclerView
    private lateinit var mAdapter: RecyclerViewAllMessage

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ChatViewModel

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as? App)?.appComponent?.inject(this)
        viewModel = injectViewModel(factory = viewModelFactory)
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("139888563907-ovejcs9p68f283ben7apglk4r9rbm5bk.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChatFragmentBinding.bind(view)
        mAdapter = RecyclerViewAllMessage()

        recyclerView = binding.messagesRecycler
        recyclerView.adapter = mAdapter

        binding.progressBar.isVisible = true

        args?.allMessages?.let {
            mAdapter.setData(it.map { ChatMapper().map(chatMassage = it) })
            binding.progressBar.isVisible = false
        }
        viewModel.allMessage.observe(viewLifecycleOwner) { messageList ->
            mAdapter.setData(messageList.map { ChatMapper().map(chatMassage = it) })
            binding.progressBar.isVisible = false
        }
    }

    override fun onStart() {
        super.onStart()

        setHasOptionsMenu(true)

        binding.send.setOnClickListener {
            val text = binding.input.text.toString()
            if (text.isEmpty()) {
                showToast(requireContext(), "Введите сообщение")
            } else {
                viewModel.insertMessage(text)
                binding.input.setText("")
                recyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.smoothScrollToPosition(mAdapter.itemCount)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> {
                viewModel.signOut()
                googleSignInClient.signOut()
                findNavController().navigate(R.id.action_chatFragment_to_loginFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_ARGS = "KEY_ARGS"

        @Parcelize
        data class Args(
            val allMessages: List<ChatMassage>
        ): Parcelable

        fun createArgs(
            allMessages: List<ChatMassage>
        ) = bundleOf(
            KEY_ARGS to Args(allMessages)
        )
    }
}