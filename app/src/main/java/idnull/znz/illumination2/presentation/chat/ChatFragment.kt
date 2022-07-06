package idnull.znz.illumination2.presentation.chat

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import idnull.znz.illumination2.App
import idnull.znz.illumination2.R
import idnull.znz.illumination2.utils.APP_ACTIVITY
import idnull.znz.illumination2.utils.ChatMapper
import idnull.znz.illumination2.utils.ShowToast
import idnull.znz.illumination2.dagger.viewmodel.injectViewModel
import idnull.znz.illumination2.data.ChatMassage
import idnull.znz.illumination2.databinding.ChatFragmentBinding
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class ChatFragment : Fragment() {

    private var _binding: ChatFragmentBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var recyclerView:RecyclerView
    private lateinit var mAdapter: RecyclerViewAllMessage
    private val args by lazy { arguments?.getParcelable<Args>(KEY_ARGS) }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ChatViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as? App)?.appComponent?.inject(this)
        viewModel = injectViewModel(factory = viewModelFactory)
        super.onCreate(savedInstanceState)



    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = ChatFragmentBinding.inflate(layoutInflater, container, false)


        mAdapter = RecyclerViewAllMessage()


        recyclerView = mBinding.messagesRecycler
        recyclerView.adapter = mAdapter

        mBinding.progressBar.isVisible = true

        args?.allMessages?.let {
            mAdapter.setData(it.map { ChatMapper().map(chatMassage = it) })
            mBinding.progressBar.isVisible = false
        }
        viewModel.allMessage.observe(viewLifecycleOwner, {
            mAdapter.setData(it.map { ChatMapper().map(chatMassage = it) })
            mBinding.progressBar.isVisible = false
        })


        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        setHasOptionsMenu(true)

        mBinding.send.setOnClickListener {
            val text = mBinding.input.text.toString()
            if (text.isEmpty()) {
                ShowToast("Введите сообщение")
            } else {
                viewModel.insertMessage(text)
                mBinding.input.setText("")
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
                APP_ACTIVITY.navController.navigate(R.id.action_chatFragment_to_loginFragment)

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