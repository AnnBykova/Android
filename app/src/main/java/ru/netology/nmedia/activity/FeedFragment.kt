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
import ru.netology.nmedia.activity.SinglePostFragment.Companion.intArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val viewModel:PostViewModel  by viewModels(ownerProducer = :: requireParentFragment)
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }


        viewModel.showSinglePost.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_singlePostFragment,
                Bundle().apply { intArg=it }
            )
            }

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

//        val postContentActivityLauncher = registerForActivityResult(
//            NewPostFragment.ResultContract
//        ) { postContent ->
//            postContent ?: return@registerForActivityResult
//            viewModel.onSaveButtonClicked(postContent)
//        }
//        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) {
//            postContentActivityLauncher.launch(it)
//        }
//
        viewModel.editPostContent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { textArg=it }
            )
        }
        return binding.root
    }
}




