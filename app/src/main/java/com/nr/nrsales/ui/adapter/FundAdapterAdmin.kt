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
) : RecyclerView.Adapter<FundAdapterAdmin.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: HistoryItemAdminBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
            , R.layout.history_item_admin, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayList[position]
        holder.binding.data=data
        holder.binding.root.setOnClickListener {
            val dialog = Dialog(mContext, R.style.DialogSlideAnim)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.show_image_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val camera = dialog.findViewById<View>(R.id.camera) as Button
            val gallary = dialog.findViewById<View>(R.id.gallary) as Button
            val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
            val trans = dialog.findViewById<View>(R.id.trans) as TouchImageView
          Glide.with(mContext).load(data.payment_receipt).into(trans)
            gallary.setOnClickListener {
                dialog.dismiss()
            }
            camera.setOnClickListener {
                dialog.dismiss()

            }
            cont_find.setOnClickListener {
                dialog.dismiss() }
            dialog.show()
        }
        }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(var binding: HistoryItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}