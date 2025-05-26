package com.example.chatapptest.ui.chatpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.model.MessageData
import com.example.chatapptest.databinding.ItemReceiveBinding
import com.example.chatapptest.databinding.ItemSentBinding
import com.example.chatapptest.ui.home.RoomsAdapterRecyler

class MessageAdapter (var messages : MutableList<MessageData>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==messageType.send.value){
            val itembinding = ItemSentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return SentMessageViewHolder(itembinding)
        }else{
            val itembinding = ItemReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ReceiveMessageViewHolder(itembinding)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        if (message.senderId == SessionProvider.user?.id){
            return messageType.send.value
        }else{
            return messageType.recevie.value
        }
    }

    override fun getItemCount(): Int = messages.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is SentMessageViewHolder){
            holder.bind(messages[position])
        } else if (holder is ReceiveMessageViewHolder){
            holder.bind(messages[position])
        }
    }

    fun addnewmessage(newmessages : List<MessageData>){
          if (newmessages.isEmpty()) return
            val lastsize = messages.size
            messages.addAll(newmessages)
            notifyItemRangeInserted(lastsize,newmessages.size)
    }
}

class SentMessageViewHolder(val binding: ItemSentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message:MessageData){
       binding.setMessage(message )
       binding.executePendingBindings()
}


}


class ReceiveMessageViewHolder(val binding: ItemReceiveBinding) : RecyclerView.ViewHolder(binding.root) {
     fun bind(message:MessageData){
         binding.setMessage(message )
         binding.executePendingBindings()
     }
}

enum class messageType(val value:Int) {
    send(100)
    , recevie(200)
}