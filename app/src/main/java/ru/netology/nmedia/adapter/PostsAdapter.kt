package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Posts
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding

//typealias OnButtonListener = (Posts) -> Unit

class PostsAdapter(
    private val interactionListener: PostInteractionListener
//    private val onLikeClicked: (Posts) -> Unit,
//    private val onShareClicked: (Posts) -> Unit
) : ListAdapter<Posts, PostsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener
//        private val onLikeClicked: OnButtonListener,
//        private val onShareClicked: OnButtonListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Posts

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likes.setOnClickListener {
                listener.onLikeClicked(post)
            }

            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }

            binding.menu.setOnClickListener { popupMenu.show() }

            binding.imageviewVideo.setOnClickListener{
                listener.onVideoClicked(post)
            }

            binding.buttonPlay.setOnClickListener{
                listener.onVideoClicked(post)
            }
        }

        fun bind(post: Posts) {
            this.post = post
            with(binding) {
                textViewAuthor.text = post.author
                textViewDate.text = post.published
                postText.text = post.content
                likes.isChecked=post.isLiked
                share.isChecked = post.isShared
                likes.text = getCountToString(post.likes)
                share.text = getCountToString(post.share)
                showCount.text = getCountToString(post.show)
                group.isVisible = post.video!= null
            }
        }

        private fun getCountToString(count: Int): String {
            return when {
                count in 1000..9_999 -> {
                    if (count % 1000 >= 100) {
                        "%.1f K".format(count / 1_000.0)
                    } else {
                        "${count / 1_000} K "
                    }
                }
                count in 10_000..999_999 -> {
                    "${count / 1_000} K "
                }
                count >= 1_000_000 -> {
                    if (count % 1000_000 >= 100_000) {
                        "%.1f M".format(count / 1_000_000.0)
                    } else {
                        "${count / 1_000_000} M "
                    }
                }
                else -> {
                    "$count"
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ViewHolder", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        //создаем view
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        метод перезаполняет неиспользуемую вью и подставляет ее
        Log.d("ViewHolder", "onBindViewHolder")
        val post = getItem(position)
        holder.bind(post)

    }


    private object DiffCallback : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem == newItem

    }
}