package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentInvoiceBinding
import com.nr.nrsales.model.InvoiceModel
import com.nr.nrsales.ui.adapter.InvoiceAdapter
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InvoiceFragment : BaseFragment(R.layout.fragment_invoice){
    lateinit var mBinding : FragmentInvoiceBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var arrayList : ArrayList<InvoiceModel>

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentInvoiceBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Withdrawal Amount"

        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        arrayList = ArrayList()

    }
    companion object {
        val TAG = InvoiceFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): InvoiceFragment {
            val fragment = InvoiceFragment()
            fragment.arguments = bundle
            return fragment
        }
    }







}
