 package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

 class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel> ()
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         viewModel.data.observe(this) {post ->
         binding.render(post)}

        binding.likes.setOnClickListener {
            viewModel.onLikeClicked()
            binding.likesCount.text=getCountToString(viewModel.data.value?.likes ?: 0)
        }

        binding.share.setOnClickListener {
            viewModel.onShareClicked()
            binding.shareCount.text=getCountToString(viewModel.data.value?.share ?: 0)
        }


    }
        private fun ActivityMainBinding.render (post: Posts){
            textViewAuthor.text = post.author
            textViewDate.text = post.published
            postText.text=post.content
            likes.setImageResource(getLikeIconResId(post.isLiked))
            likesCount.text= getCountToString(post.likes)
            shareCount.text =getCountToString(post.share)
            showCount.text =getCountToString(post.show)
        }

     private fun getCountToString (count: Int): String {
         return when {
                  count in 1000..9_999 -> {
                      if (count % 1000 >= 100) {
                          "%.1f K".format(count/1_000.0)
                      } else {
                          "${count/1_000} K "
                      }
                  }
                  count in 10_000..999_999 -> {
                      "${count/1_000} K "
                  }
                  count >= 1_000_000 -> {
                      if (count % 1000_000 >=100_000) {
                          "%.1f M".format(count / 1_000_000.0)
                      } else{
                          "${count/1_000_000} M "
                      }}
                  else -> {"$count"}
                  }
     }

     @DrawableRes
     private fun getLikeIconResId(liked : Boolean) =
         if (liked) R.drawable.ic_baseline_favorite_red_24 else R.drawable.ic_baseline_favorite_24

    }
