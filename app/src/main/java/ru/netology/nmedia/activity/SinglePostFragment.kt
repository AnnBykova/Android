package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.IntArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class SinglePostFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = :: requireParentFragment)
        val viewHolder = PostsAdapter.ViewHolder (binding.postLayout, viewModel)
        viewModel.currentPost.value?.let { viewHolder.bind(it) }
        val adapter = PostsAdapter(viewModel)
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == viewModel.showSinglePost.value } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }
        viewModel.editPostContent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_singlePostFragment_to_newPostFragment,
                Bundle().apply { textArg=it }
            )
        }
//        viewModel.deleteSinglePost.observe(viewLifecycleOwner) {
//            findNavController().navigateUp()
//        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            startActivity(intent)
        }

       return binding.root
    }

    companion object{
        var Bundle.intArg : Int by IntArg
    }
}