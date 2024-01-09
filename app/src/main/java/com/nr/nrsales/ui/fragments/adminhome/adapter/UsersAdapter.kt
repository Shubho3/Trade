package com.nr.nrsales.ui.fragments.adminhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.UserItemBinding
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.User
import java.util.Locale

class UsersAdapter(
    private val mContext: Context,
    private val click: UsersAdapterClickListener
) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>(), Filterable {
    var arrayListFiltered: ArrayList<User> = ArrayList()
    var arrayList: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: UserItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.user_item, parent, false
        )
        return MyViewHolder(binding)
    }

    fun addData(list: List<User>) {
        arrayList = list as ArrayList<User>
        arrayListFiltered = arrayList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrayListFiltered[position]
        holder.binding.data = data
        if (data.status == "1") holder.binding.changeStatus.text = "Active"
        else holder.binding.changeStatus.text = "InActive"
        holder.binding.changeStatus.setOnClickListener {
            click.onItemClick(data, 1)
        }
        holder.binding.contFind.setOnClickListener {
            click.onItemClick(data, 0)
        }
        holder.binding.viewDetails.setOnClickListener {
            click.onItemClick(data, 11)
        }
    }

    override fun getItemCount(): Int {
        return arrayListFiltered.size
    }

    class MyViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {}
    interface UsersAdapterClickListener {
        fun onItemClick(model: User, int: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) arrayListFiltered = arrayList else {
                    val filteredList = ArrayList<User>()
                    arrayList.filter {
                        (it.first_name.contains(constraint!!))
                    }.forEach { filteredList.add(it) }
                    arrayListFiltered = filteredList

                }
                return FilterResults().apply { values = arrayListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                arrayListFiltered = if (results?.values == null) ArrayList()
                else results.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}
