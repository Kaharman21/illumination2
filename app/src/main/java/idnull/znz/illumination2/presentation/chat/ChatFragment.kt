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

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as? App)?.appComponent?.inject(this)
        viewModel = injectViewModel(factory = viewModelFactory)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChatFragmentBinding.bind(view)
        mAdapter = RecyclerViewAllMessage()

        setupUi()
        setupObservers()
        setupListeners()

        args?.allMessages?.let {
            mAdapter.setData(it.map { ChatMapper().map(chatMassage = it) })
            binding.progressBar.isVisible = false
        }

        viewModel.listenDataFromDB()
    }

    override fun onStart() {
        super.onStart()

        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        recyclerView.smoothScrollToPosition(mAdapter.itemCount)

    }

    private fun setupObservers() {
        viewModel.apply {
            dataFromDB.observe(viewLifecycleOwner) { messageList ->
                mAdapter.setData(messageList.map { ChatMapper().map(chatMassage = it) })
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun setupUi() {
        recyclerView = binding.messagesRecycler
        recyclerView.adapter = mAdapter

        binding.progressBar.isVisible = true
    }

    private fun setupListeners() {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> {
                viewModel.signOut()
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