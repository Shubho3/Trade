package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentOrderListBinding
import com.nr.nrsales.model.Position
import com.nr.nrsales.model.User
import com.nr.nrsales.ui.fragments.adminhome.adapter.PositionAdapter
import com.nr.nrsales.ui.fragments.adminhome.adapter.UsersAdapter
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.viewmodel.home.PositionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : BaseFragment(R.layout.fragment_order_list) ,PositionAdapter.UsersAdapterClickListener{
    lateinit var mBinding: FragmentOrderListBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<PositionViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(requireActivity()) }
    private var funds: ArrayList<Position> = ArrayList()
    private lateinit var adapter: PositionAdapter

    private var url = "https://www.moneycontrol.com//us-markets/index/chart?symbol=INDU"
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentOrderListBinding
        context = requireActivity()
        init()
        adapter = PositionAdapter(requireActivity(),this)
        mBinding.historyRecycleView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter = adapter
        GetFund()
    }
    private fun GetFund() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetch_position_list(map)
        viewmodel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            mBinding.noData.visibility=View.GONE
                            funds.clear()
                            funds.addAll(it.result)
                            //  funds.removeAt(0)
                            adapter.addData(funds)
                            adapter.notifyDataSetChanged()
                        } else {

                            mBinding.noData.visibility=View.VISIBLE

                        }
                    }
                }

                is NetworkResult.Error -> {
                    GlobalUtility.hideProgressMessage()
                    Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                    mBinding.noData.visibility=View.VISIBLE

                }

                is NetworkResult.Loading -> {
                    GlobalUtility.hideProgressMessage()
                }
            }
        }


    }

    private fun init() {
        url = requireArguments().getString("url").toString()
        if (url == "1") {
            mBinding.bottom.visibility = View.GONE
            mBinding.webView.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;
            mBinding.webView.requestLayout();
            var d =
                "<!-- TradingView Widget BEGIN -->\n" + "<div class=\"tradingview-widget-container\" style=\"height:100%;width:100%\">\n" + "  <div class=\"tradingview-widget-container__widget\" style=\"height:calc(100% - 32px);width:100%\"></div>\n" + "  <div class=\"tradingview-widget-copyright\"><a href=\"https://in.tradingview.com/\" rel=\"noopener nofollow\" target=\"_blank\"><span class=\"blue-text\">Track all markets on TradingView</span></a></div>\n" + "  <script type=\"text/javascript\" src=\"https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js\" async>\n" + "  {\n" + "  \"autosize\": true,\n" + "  \"symbol\": \"NSE:NIFTY\",\n" + "  \"interval\": \"D\",\n" + "  \"timezone\": \"Etc/UTC\",\n" + "  \"theme\": \"light\",\n" + "  \"style\": \"1\",\n" + "  \"locale\": \"in\",\n" + "  \"enable_publishing\": false,\n" + "  \"allow_symbol_change\": true,\n" + "  \"support_host\": \"https://www.tradingview.com\"\n" + "}\n" + "  </script>\n" + "</div>\n" + "<!-- TradingView Widget END -->"
            mBinding.webView.settings.javaScriptEnabled = true
            mBinding.webView.loadUrl("https://www.moneycontrol.com//us-markets/index/chart?symbol=INDU");

            //  mBinding.webView.loadDataWithBaseURL(null, d, "text/html", "UTF-8", null)
        } else {
            mBinding.webView.settings.javaScriptEnabled = true
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

    override fun onItemClick(model: Position, int: Int) {

    }


}