package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentOrderListBinding
import com.nr.nrsales.utils.BaseFragment

class OrderListFragment : BaseFragment(R.layout.fragment_order_list) {
    lateinit var mBinding: FragmentOrderListBinding
    private lateinit var context: Context
private var url ="https://www.moneycontrol.com//us-markets/index/chart?symbol=INDU"
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentOrderListBinding
        context = requireActivity()
        init()

    }

    private fun init() {
        url= requireArguments().getString("url").toString()

         if (url =="1"){var d   = "<!-- TradingView Widget BEGIN -->\n" +
                 "<div class=\"tradingview-widget-container\" style=\"height:100%;width:100%\">\n" +
                 "  <div class=\"tradingview-widget-container__widget\" style=\"height:calc(100% - 32px);width:100%\"></div>\n" +
                 "  <div class=\"tradingview-widget-copyright\"><a href=\"https://in.tradingview.com/\" rel=\"noopener nofollow\" target=\"_blank\"><span class=\"blue-text\">Track all markets on TradingView</span></a></div>\n" +
                 "  <script type=\"text/javascript\" src=\"https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js\" async>\n" +
                 "  {\n" +
                 "  \"autosize\": true,\n" +
                 "  \"symbol\": \"NASDAQ:AAPL\",\n" +
                 "  \"interval\": \"D\",\n" +
                 "  \"timezone\": \"Etc/UTC\",\n" +
                 "  \"theme\": \"light\",\n" +
                 "  \"style\": \"1\",\n" +
                 "  \"locale\": \"in\",\n" +
                 "  \"enable_publishing\": false,\n" +
                 "  \"allow_symbol_change\": true,\n" +
                 "  \"support_host\": \"https://www.tradingview.com\"\n" +
                 "}\n" +
                 "  </script>\n" +
                 "</div>\n" +
                 "<!-- TradingView Widget END -->"
             mBinding.webView.settings.javaScriptEnabled=true
             mBinding.webView.loadDataWithBaseURL(null, d, "text/html", "UTF-8", null)
         }else{
             mBinding.webView.settings.javaScriptEnabled=true
             mBinding.webView.loadUrl(url);
         }

    }


    companion object {
        val TAG = OrderListFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): OrderListFragment {
            val fragment = OrderListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}