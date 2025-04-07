package com.example.chatapptest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.databinding.ItemRoomBinding

class RoomsAdapterRecyler(var Roomitems :List<RommData>? = listOf()):
    RecyclerView.Adapter<RoomsAdapterRecyler.ItemViewHolder>() {

    class ItemViewHolder(val itembinidng : ItemRoomBinding): RecyclerView.ViewHolder(itembinidng.root){

            fun bind(room : RommData?){
                itembinidng.title.text = room?.title
                itembinidng.imageIcon.setImageResource(
                    CategoryData.getcatogriesbyid(room?.categoryid))

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itembinidng = ItemRoomBinding.inflate(LayoutInflater.
        from(parent.context),parent,false)

        return  ItemViewHolder(itembinidng)
    }

    override fun getItemCount(): Int = Roomitems?.size?:0

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(Roomitems?.get(position))
        onItemClickListener?.let { itemClickListener ->
            holder.itemView.setOnClickListener {
              itemClickListener.onItemClick(position,Roomitems!![position])
            }
        }
    }

    fun changeData(rooms: List<RommData>?){
        this.Roomitems = rooms
        notifyDataSetChanged()
    }
    var onItemClickListener : OnItemClickListener? = null
    fun interface OnItemClickListener{
        fun onItemClick(position: Int,room: RommData)
    }
}