package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Posts
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding

typealias OnButtonListener = (Posts) -> Unit

class PostsAdapter (

    private val onLikeClicked: (Posts) -> Unit,
    private val onShareClicked: (Posts) -> Unit
        ): ListAdapter<Posts, PostsAdapter.ViewHolder> (DiffCallback){

    class ViewHolder(
        private val binding : PostListItemBinding,
        private val onLikeClicked: OnButtonListener,
        private val onShareClicked: OnButtonListener
    ): RecyclerView.ViewHolder (binding.root) {

        private lateinit var post: Posts
        init {
            binding.likes.setOnClickListener {
                onLikeClicked(post)
            }

            binding.share.setOnClickListener {
                onShareClicked(post)
            }
        }

        fun bind(post: Posts) {
            this.post= post
            with(binding) {
                textViewAuthor.text = post.author
                textViewDate.text = post.published
                postText.text = post.content
                likes.setImageResource(if (post.isLiked) R.drawable.ic_baseline_favorite_red_24 else R.drawable.ic_baseline_favorite_24)
                likesCount.text = getCountToString(post.likes)
                shareCount.text = getCountToString(post.share)
                showCount.text = getCountToString(post.show)
            }
        }

        fun getCountToString (count: Int): String {
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
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //создаем view
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        метод перезаполняет неиспользуемую вью и подставляет ее
        val post = getItem(position)
        holder.bind(post)

    }


    private object DiffCallback: DiffUtil.ItemCallback<Posts> (){
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts)=
            oldItem.id==newItem.id


        override fun areContentsTheSame(oldItem: Posts, newItem: Posts)=
            oldItem==newItem

    }
}