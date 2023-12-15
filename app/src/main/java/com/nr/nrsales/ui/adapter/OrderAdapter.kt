package com.nr.nrsales.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.ItemInvoiceBinding
import com.nr.nrsales.databinding.ItemOrderBinding
import com.nr.nrsales.model.InvoiceModel
import com.nr.nrsales.model.OrderModel

class OrderAdapter (val mContext: Context, var arrayList: ArrayList<OrderModel>
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding: ItemOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_order, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var data = arrayList!!.get(position)
        holder.binding.tvOrder.text = "Order No. "+ position+1





        holder.binding.tvOrder.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ID", arrayList!!.get(position).ID.toString())
            bundle.putString("TrackingNumber",arrayList!!.get(position).TrackingNumber.toString() )
            bundle.putString("CurrentStatus",arrayList!!.get(position).CurrentStatus.toString() )
            bundle.putString("DateSubmitted",arrayList!!.get(position).DateSubmitted.toString() )
            bundle.putString("RequestedBy", arrayList!!.get(position).RequestedBy.toString())
            bundle.putString("TotalCost",arrayList!!.get(position).TotalCost.toString())
            bundle.putString("Description", arrayList!!.get(position).Description.toString())
            bundle.putString("Weight",arrayList!!.get(position).Weight.toString() )
            bundle.putString("CollectionLocation", arrayList!!.get(position).CollectionLocation.toString())
            bundle.putString("DeliveryLocation", arrayList!!.get(position).DeliveryLocation.toString())
            bundle.putString("Distance", arrayList!!.get(position).Distance.toString())



            Navigation.findNavController(it).navigate(R.id.action_orderListFragment_to_orderDetailFragment,bundle)


        }


    }

    override fun getItemCount(): Int {
        return if (arrayList == null) 0 else arrayList!!.size
    }

    class MyViewHolder(var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}