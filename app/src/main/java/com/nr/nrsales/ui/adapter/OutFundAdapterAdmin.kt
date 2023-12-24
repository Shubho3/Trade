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
    , private val listener: outFundClickListener

) : RecyclerView.Adapter<OutFundAdapterAdmin.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdminOutItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
            , R.layout.admin_out_item, parent, false)
        return MyViewHolder(binding)
    }
    interface outFundClickListener {
        fun onItemClick(model: OutFundResAdmin.Result, int: Int)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayList[position]
        holder.binding.data=data
        if (data.status=="0"){
            holder.binding.status.setTextColor(mContext.getColor(R.color.color_primary))
            holder.binding.status.setText("Pending")}
        else    if (data.status=="1"){
            holder.binding.status.setTextColor(mContext.getColor(R.color.green))
            holder.binding.status.setText("Accepted")}
        else if (data.status == "2") {
            holder.binding.status.setTextColor(mContext.getColor(R.color.red))
            holder.binding.status.setText("Rejected")
        }
        holder.binding.root.setOnClickListener {
            if (data.status=="0") listener.onItemClick(data,position)
        }
        }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: AdminOutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}
