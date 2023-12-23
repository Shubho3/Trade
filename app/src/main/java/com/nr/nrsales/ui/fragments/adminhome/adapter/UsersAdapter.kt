package com.nr.nrsales.ui.fragments.adminhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.UserItemBinding
import com.nr.nrsales.model.User

class UsersAdapter(
    private val mContext: Context, private var arrayList: ArrayList<User>
) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: UserItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
            , R.layout.user_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayList[position]
        holder.binding.data=data
        }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}