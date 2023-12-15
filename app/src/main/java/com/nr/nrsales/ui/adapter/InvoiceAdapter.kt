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
import com.nr.nrsales.model.CsvResModel2
import com.nr.nrsales.model.InvoiceModel

class InvoiceAdapter  (val mContext: Context, var arrayList: ArrayList<InvoiceModel>
) : RecyclerView.Adapter<InvoiceAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding: ItemInvoiceBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_invoice, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var data = arrayList!!.get(position)
        holder.binding.tvNumber.text = "Invoice Number "+data.InvoiceNumber
        if(data.IsPaid==true){
            holder.binding.tvType.text= "Paid"
            holder.binding.tvType.setTextColor(mContext.getColor(R.color.color_primary))
            holder.binding.viewType.setBackgroundColor(mContext.getColor(R.color.color_primary))

        }
        else{
            holder.binding.tvType.text= "Unpaid"
            holder.binding.viewType.setBackgroundColor(mContext.getColor(R.color.red))
        }




        holder.binding.btnDetail.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Customer",arrayList!!.get(position).Customer.toString() )
            bundle.putString("ID", arrayList!!.get(position).ID.toString())
            bundle.putString("InvoiceNumber",arrayList!!.get(position).InvoiceNumber.toString() )
            bundle.putString("Date",arrayList!!.get(position).Date.toString() )
            bundle.putString("DueDate", arrayList!!.get(position).DueDate.toString())
            bundle.putString("IsPaid",arrayList!!.get(position).IsPaid.toString())
            bundle.putString("Memo", arrayList!!.get(position).Memo.toString())
            bundle.putString("TransferredToQuickBooksDesktop",arrayList!!.get(position).TransferredToQuickBooksDesktop.toString() )
            bundle.putString("TotalAmount", arrayList!!.get(position).TotalAmount.toString())

            Navigation.findNavController(it).navigate(R.id.action_invoiceFragment_to_invoiceDetailFragment,bundle)


        }


    }

    override fun getItemCount(): Int {
        return if (arrayList == null) 0 else arrayList!!.size
    }

    class MyViewHolder(var binding: ItemInvoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}