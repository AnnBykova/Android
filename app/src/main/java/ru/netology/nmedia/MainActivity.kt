package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }

        viewModel.currentPost.observe(this) { currentPost ->
            val content = currentPost?.content
            binding.content.setText(content)
                if (content != null) {
                    binding.content.requestFocus()
                    binding.content.showKeyboard()
                    binding.group.visibility= View.VISIBLE

                } else {
                    binding.content.clearFocus()
                    binding.content.hideKeyboard()
                    binding.group.visibility= View.GONE
                }
        }
    }
}




