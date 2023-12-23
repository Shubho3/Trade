package com.nr.nrsales.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.AdminOutItemBinding
import com.nr.nrsales.databinding.HistoryItem2Binding
import com.nr.nrsales.databinding.HistoryItemBinding
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.OutFundResAdmin

class OutFundAdapterAdmin  (
    private val mContext: Context, private var arrayList: ArrayList<OutFundResAdmin.Result>
) : RecyclerView.Adapter<OutFundAdapterAdmin.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdminOutItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
            , R.layout.admin_out_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayList[position]
        holder.binding.data=data
        }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: AdminOutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}