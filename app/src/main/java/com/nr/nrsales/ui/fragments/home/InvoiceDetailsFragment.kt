package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.google.android.material.color.MaterialColors.getColor
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentInvoiceDetailBinding
import com.nr.nrsales.model.InvoiceModel
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays

class InvoiceDetailsFragment : BaseFragment(R.layout.fragment_invoice_detail){
    lateinit var mBinding : FragmentInvoiceDetailBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf



    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentInvoiceDetailBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Invoice Detail"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

        if(arguments!=null) {
            mBinding.tvInvoiceDate.text = requireArguments().getString("Date").toString()
            mBinding.tvTotalAmount.text = "$"+requireArguments().getString("TotalAmount").toString()
            mBinding.tvDueDate.text = requireArguments().getString("DueDate").toString()
            mBinding.tvBalanceDue.text = "$"+requireArguments().getString("TotalAmount").toString()
            mBinding.tvPaid.text = requireArguments().getString("IsPaid").toString()

            if(requireArguments().getString("IsPaid").toString().equals("true")){
                mBinding.tvStatus.text= "Already Paid"
                mBinding.tvStatus.setBackgroundColor(context.getColor(R.color.color_primary))

            }
            else{
                mBinding.tvStatus.text= "Not Paid"
                mBinding.tvStatus.setBackgroundColor(context.getColor(R.color.red))
            }

        }
    }





}