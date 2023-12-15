package com.nr.nrsales.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.nr.nrsales.R
import com.nr.nrsales.model.CsvResModel2
import okhttp3.ResponseBody
import retrofit2.Callback

/*class AutoCompleteAdapter(private val mContext: Context,
                          private val viewResourceId: Int,
                          private val items: ArrayList<CsvResModel2.Datas>) : ArrayAdapter<CsvResModel2.Datas?>(mContext, viewResourceId, items.toList()) {

    private val itemsAll = items.clone() as ArrayList<CsvResModel2.Datas>
    private var suggestions = ArrayList<CsvResModel2.Datas>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if (v == null) {
            val vi = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val street: CsvResModel2.Datas? = items[position]
        if (street != null) {
            val title = v?.findViewById(R.id.tvTitle) as TextView?
            title?.text = street.TARIFF_ALL
        }
        return v!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as CsvResModel2.Datas).TARIFF_ALL.toString()
        }



        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                for (street in itemsAll) {
                    if (street.TARIFF_ALL!!.toLowerCase()!!.startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(street)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filteredList =  results?.values as ArrayList<CsvResModel2.Datas>?

            if (results != null && results.count > 0) {
                clear()
                for (c: CsvResModel2.Datas in filteredList ?: listOf<CsvResModel2.Datas>()) {
                    add(c)
                }
                notifyDataSetChanged()
            }
            else notifyDataSetInvalidated();
        }
    }
}*/


class AutoCompleteAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    cities: List<CsvResModel2.Datas>
) :
    ArrayAdapter<CsvResModel2.Datas>(mContext, mLayoutResourceId, cities) {
    private val city: MutableList<CsvResModel2.Datas> = ArrayList(cities)
    private var allCities: List<CsvResModel2.Datas> = ArrayList(cities)

    override fun getCount(): Int {
        return city.size
    }
    override fun getItem(position: Int): CsvResModel2.Datas {
        return city[position]
    }
    override fun getItemId(position: Int): Long {
        return city[position].id!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val city: CsvResModel2.Datas = getItem(position)
            val cityAutoCompleteView = convertView!!.findViewById<View>(R.id.tvTitle) as TextView
            cityAutoCompleteView.text = city.TARIFF_ALL
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any) :String {
                return (resultValue as CsvResModel2.Datas).TARIFF_ALL!!
            }
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val citySuggestion: MutableList<CsvResModel2.Datas> = ArrayList()
                    for (city in allCities) {
                        if (city.TARIFF_ALL!!.toLowerCase().startsWith(constraint.toString().toLowerCase())
                        ) {
                            citySuggestion.add(city)
                        }
                    }
                    filterResults.values = citySuggestion
                    filterResults.count = citySuggestion.size
                }
                return filterResults
            }
            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                city.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is CsvResModel2.Datas) {
                            city.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    city.addAll(allCities)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}


