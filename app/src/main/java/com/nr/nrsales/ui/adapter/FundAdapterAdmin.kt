package com.nr.nrsales.ui.adapter

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.nr.nrsales.R
import com.nr.nrsales.databinding.HistoryItemAdminBinding
import com.nr.nrsales.databinding.HistoryItemBinding
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.utils.customui.TouchImageView
import com.nr.nrsales.utils.customui.ZoomableImageView

class FundAdapterAdmin  (
    private val mContext: Context, private var arrayList: ArrayList<AddFundRes.Result>
    , private val listener: OnFundClickListener
) : RecyclerView.Adapter<FundAdapterAdmin.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: HistoryItemAdminBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
            , R.layout.history_item_admin, parent, false)
        return MyViewHolder(binding)
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
          if (data.status=="0")  listener.onItemClick(data,1)
        }
        }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: HistoryItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {}
    interface OnFundClickListener {
        fun onItemClick(model: AddFundRes.Result, int: Int)
    }

}
