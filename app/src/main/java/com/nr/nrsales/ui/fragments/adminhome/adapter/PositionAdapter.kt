package com.nr.nrsales.ui.fragments.adminhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.PositionItemBinding
import com.nr.nrsales.databinding.UserItemBinding
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.Position
import com.nr.nrsales.model.User

class PositionAdapter(
    private val mContext: Context,
    private val click: UsersAdapterClickListener
) : RecyclerView.Adapter<PositionAdapter.MyViewHolder>(), Filterable {
    var arrayListFiltered: ArrayList<Position> = ArrayList()
    var arrayList: ArrayList<Position> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: PositionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.position_item, parent, false
        )
        return MyViewHolder(binding)
    }

    fun addData(list: List<Position>) {
        arrayList = list as ArrayList<Position>
        arrayListFiltered = arrayList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayListFiltered[position]
        holder.binding.data = data

    }

    override fun getItemCount(): Int {
        return arrayListFiltered.size
    }

    class MyViewHolder(var binding: PositionItemBinding) : RecyclerView.ViewHolder(binding.root) {}
    interface UsersAdapterClickListener {
        fun onItemClick(model: Position, int: Int)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) arrayListFiltered = arrayList else {
                    val filteredList = ArrayList<Position>()
                    arrayList.filter {
                            (it.id.contains(constraint!!)) or (it.share_name.contains(constraint))
                        }.forEach { filteredList.add(it) }
                    arrayListFiltered = filteredList

                }
                return FilterResults().apply { values = arrayListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                arrayListFiltered = if (results?.values == null) ArrayList()
                else results.values as ArrayList<Position>
                notifyDataSetChanged()
            }
        }
    }
}