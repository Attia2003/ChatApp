package com.example.chatapptest.ui.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.databinding.ItemSpinerBinding

class CatogriesAdapter (val items : List<CategoryData>):BaseAdapter(){
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val  itmeviewholder : Itemviewholder

            if(convertView ==null ){

                val itembinding = ItemSpinerBinding.inflate(LayoutInflater.
                from(parent?.context),parent,false)
                itmeviewholder =   Itemviewholder(itembinding)
                itembinding.root.tag = itmeviewholder
            }else {
                 itmeviewholder = convertView.tag as Itemviewholder
            }
                itmeviewholder.itembinding.title.text = items[position].name
                itmeviewholder.itembinding.imageIcon.setImageResource(items[position].imagesres)

                return itmeviewholder.itembinding.root
    }
    class  Itemviewholder(val itembinding : ItemSpinerBinding){

    }
}