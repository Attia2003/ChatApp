package com.example.chatapptest.ui.chatpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapptest.database.model.MessageData
import com.example.chatapptest.databinding.ItemReceiveBinding
import com.example.chatapptest.databinding.ItemSentBinding

class MessageAdapter(
    private val currentUserIdProvider: () -> String?
) : ListAdapter<MessageData, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MessageType.SEND.value) {
            val itemBinding = ItemSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(itemBinding)
        } else {
            val itemBinding = ItemReceiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiveMessageViewHolder(itemBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderId == currentUserIdProvider()) {
            MessageType.SEND.value
        } else {
            MessageType.RECEIVE.value
        }
    }

    override fun getItemId(position: Int): Long {
        val message = getItem(position)
        return message.id?.hashCode()?.toLong()
            ?: "${message.senderId}_${message.timestamp?.seconds}_${message.content}".hashCode().toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceiveMessageViewHolder) {
            holder.bind(message)
        }
    }

    fun submitMessages(newMessages: List<MessageData>, onCommitted: () -> Unit = {}) {
        submitList(newMessages.toList(), onCommitted)
    }
}

class SentMessageViewHolder(
    private val binding: ItemSentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageData) {
        binding.message = message
        binding.executePendingBindings()
    }
}

class ReceiveMessageViewHolder(
    private val binding: ItemReceiveBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageData) {
        binding.message = message
        binding.executePendingBindings()
    }
}

private class MessageDiffCallback : DiffUtil.ItemCallback<MessageData>() {
    override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem == newItem
    }
}

enum class MessageType(val value: Int) {
    SEND(100),
    RECEIVE(200)
}
