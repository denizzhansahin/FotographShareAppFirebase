package com.denizhan.fotografpaylasmafirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denizhan.fotografpaylasmafirebase.R
import com.denizhan.fotografpaylasmafirebase.model.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_raw.view.*


class HaberRecyclerAdapter(val postList:ArrayList<Post>):RecyclerView.Adapter<HaberRecyclerAdapter.PostHolder>() {

    class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_raw,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recycler_raw_email.text = postList[position].kullaniciemail
        holder.itemView.recycler_raw_yorum.text = postList[position].kullaniciyorum
        Picasso.get().load(postList[position].gorselurl).into(holder.itemView.recycler_raw_imageview)

    }

    override fun getItemCount(): Int {
        //kaç tane eleman olacak
        return postList.size

    }

}