package com.nr.nrsales.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.NotificationItemBinding
import com.nr.nrsales.model.AddFundRes

class NotificationsAdapter(
    private val mContext: Context, private var arrayList: ArrayList<AddFundRes.Result>
) : RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: NotificationItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.notification_item, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayList[position]
        holder.binding.data = data
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}