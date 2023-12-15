package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentInvoiceDetailBinding
import com.nr.nrsales.databinding.FragmentOrderDetailBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.SharedPrf

class OrderDetailFragment : BaseFragment(R.layout.fragment_order_detail){
    lateinit var mBinding : FragmentOrderDetailBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    var ID : String =""


    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentOrderDetailBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Tracking Detail"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

        if(arguments!=null) {
            ID = requireArguments().getString("ID").toString()
            mBinding.tvTrackingNumber.text = requireArguments().getString("TrackingNumber").toString()

            mBinding.tvDateSubmitted.text = requireArguments().getString("DateSubmitted").toString()
            mBinding.tvRequestedBy.text = requireArguments().getString("RequestedBy").toString()
            mBinding.tvTotalCost.text = "$"+requireArguments().getString("TotalCost").toString()
            mBinding.tvDescription.text = requireArguments().getString("Description").toString()
            mBinding.tvWeight.text = "$"+requireArguments().getString("Weight").toString()
            mBinding.tvCollectionLocation.text = requireArguments().getString("CollectionLocation").toString()
            mBinding.tvDeliveryLocation.text = requireArguments().getString("DeliveryLocation").toString()
            mBinding.tvDistance.text = requireArguments().getString("Distance").toString()


            if(requireArguments().getString("CurrentStatus").toString().equals("0")){

                mBinding.tvCurrentStatus.text =  "None"
            }
            else{
                mBinding.tvCurrentStatus.text =  "None"

            }

        }

        mBinding.btnViewShippingLevel.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("ID",ID)
            bundle.putString("type","SL")
            bundle.putString("title",getString(R.string.view_shipping_label))
            Navigation.findNavController(it).navigate(R.id.action_orderDetailFragment_to_viewReportFragment,bundle)

        }

        mBinding.btnViewWayBill.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ID",ID)
            bundle.putString("type","WB")
            bundle.putString("title",getString(R.string.view_waybill))
            Navigation.findNavController(it).navigate(R.id.action_orderDetailFragment_to_viewReportFragment,bundle)
        }

        mBinding.btnViewBillOfLanding.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ID",ID)
            bundle.putString("type","BOL")
            bundle.putString("title",getString(R.string.view_bill_of_landing))
            Navigation.findNavController(it).navigate(R.id.action_orderDetailFragment_to_viewReportFragment,bundle)
        }

    }









    companion object {
        val TAG = OrderDetailFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): OrderDetailFragment {
            val fragment = OrderDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }







}