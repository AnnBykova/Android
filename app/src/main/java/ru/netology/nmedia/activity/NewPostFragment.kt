package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment (){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = :: requireParentFragment)
        arguments?.textArg?.let(binding.edit :: setText)
        // аналогичная запись
        //val text = arguments?.textArg
        //text?.let { binding.edit.setText(it) }
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val text = binding.edit.text
            if (!text.isNullOrBlank()) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
            findNavController().navigate(R.id.action_newPostFragment_to_feedFragment)
        }
        return binding.root
    }

//    object ResultContract : ActivityResultContract<String?, String?>(){
//        override fun createIntent(context: Context, input: String?)=
//            Intent (context, NewPostFragment ::class.java).putExtra(Intent.EXTRA_TEXT,input)
//
//
//        // разбираем результат, который получили
//        override fun parseResult(resultCode: Int, intent: Intent?)=
//            if (resultCode == Activity.RESULT_OK) {
//                intent?.getStringExtra(RESULT_KEY)
//            } else null
//    }

    companion object{
        private const val RESULT_KEY = "PostNewContent"
        var Bundle.textArg : String? by StringArg
    }
}