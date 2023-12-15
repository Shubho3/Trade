package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.WebResourceResponse
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.request.Disposable
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentViewReportBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File

@AndroidEntryPoint
class ViewReportFragment : BaseFragment(R.layout.fragment_view_report) {
    lateinit var mBinding: FragmentViewReportBinding
    private lateinit var context: Context
    var ID: String = ""
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var disposable: Disposable
    private var imageUrl: String? = null

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentViewReportBinding
        context = requireActivity()
        init()

    }

    private fun init() {
//        mBinding.headerLay.tvLogo.text = requireArguments().getString("title").toString()
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://secure.ontime360.com/sites/NRSales/api/order/report?type=SL&id=1cd120f5-01b8-4c06-a5ba-06ea549f8c68")
            .addHeader("Accept", "application/pdf")
            .addHeader("Authorization", "867bb48f-ade8-4688-954b-12668ea07977")
            .build()
        val response = client.newCall(request).execute()
        Log.e("TAG", "saveImage2: "+response )
       // if (arguments != null) {
            /*    RetrievePDFFromURL(mBinding.idPDFView).execute("https://secure.ontime360.com/sites/NRSales/api/order/report?type="+
                    requireArguments().getString("type").toString()+"&id="+requireArguments().getString("ID").toString())*/
            //  GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
            //  mBinding.webView.getSettings().setJavaScriptEnabled(true);

            //var filename = "type=" + requireArguments().getString("type").toString() + "&id=" + requireArguments().getString("ID").toString()

          //  Log.e(TAG, "init: --------------------$filename")
        /*    val map: HashMap<String, Any> = HashMap()
          //  map["type"] =  requireArguments().getString("type").toString()
            map["type"] =  "SL"
           // map["id"] =  requireArguments().getString("ID").toString()
            map["id"] =  "1cd120f5-01b8-4c06-a5ba-06ea549f8c68"
            mainViewModel.downloadImage(map)
            mainViewModel.response1.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            Log.e(TAG, "init: $it")
                        }
                    }
                    is NetworkResult.Error -> {

                        Log.e(TAG, "init: "+response.message )
                        Log.e(TAG, "init: "+response.data )
                        Log.e(TAG, "init: $response")
                        Toast.makeText(
                            requireContext(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                    }
                }
            }*/
            /*    mBinding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    GlobalUtility.hideProgressMessage()
                }

                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                  //  return super.shouldInterceptRequest(view, request)

                    val url = request!!.url.toString()

                    return getNewResponse(url)


                }

            }
            val headerMap = HashMap<String,String>()
            headerMap["Authorization"] = "867bb48f-ade8-4688-954b-12668ea07977"

            mBinding.webView.loadUrl("" + filename);
           Log.e("load url====","http://docs.google.com/gview?embedded=true&url=" + filename)
       */
            //  observeDownloadResponse()


        //}
    }

    private fun downloadImage(url: String?) {
        url?.let {
            /*val di = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/" +
                    resources.getString(R.string.dogs) + "/"*/

            /* val path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)*/
            val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/" +
                    resources.getString(R.string.app_name) + "/"

            val dir = File(dirPath)

            val fileName: String = url.substring(url.lastIndexOf('/') + 1)

            val imageLoader = ImageLoader(requireContext())
            /*  val request = ImageRequest.Builder(requireContext())
                  .data(url)
                  .target { drawable ->
                  }
                  .build()
              disposable = imageLoader.enqueue(request)*/
        }
    }

    private fun observeDownloadResponse() {
        mainViewModel.downloadResponse.observe(this) { response ->
            if (response) {
                Toast.makeText(requireContext(), "Saved !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Unable to save image !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
     //   disposable.dispose()
        super.onDestroy()
    }


    private fun getNewResponse(url: String): WebResourceResponse? {
        return try {
            val httpClient = OkHttpClient()
            val request: Request = Request.Builder()
                .url(url.trim { it <= ' ' })
                .addHeader("Authorization", "867bb48f-ade8-4688-954b-12668ea07977") // Example header
                //.addHeader("api-key", "YOUR_API_KEY") // Example header
                .build()
            val response: Response = httpClient.newCall(request).execute()
            WebResourceResponse(
                null,
                response.header("content-encoding", "utf-8"),
                response.body!!.byteStream()
            )
        } catch (e: Exception) {
            null
        }
    }


    /*
       class RetrievePDFFromURL(pdfView: PDFView) :
            AsyncTask<String, Void, InputStream>() {
           // on below line we are creating a variable for our pdf view.
           val mypdfView: PDFView = pdfView

           // on below line we are calling our do in background method.
           override fun doInBackground(vararg params: String?): InputStream? {
               // on below line we are creating a variable for our input stream.
               var inputStream: InputStream? = null
               try {
                   // on below line we are creating an url
                   // for our url which we are passing as a string.
                   val url = URL(params.get(0))

                   // on below line we are creating our http url connection.
                   val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                   // on below line we are checking if the response
                   // is successful with the help of response code
                   // 200 response code means response is successful
                   if (urlConnection.responseCode == 200) {
                       // on below line we are initializing our input stream
                       // if the response is successful.
                       inputStream = BufferedInputStream(urlConnection.inputStream)
                   }
               }
               // on below line we are adding catch block to handle exception
               catch (e: Exception) {
                   // on below line we are simply printing
                   // our exception and returning null
                   e.printStackTrace()
                   return null;
               }
               // on below line we are returning input stream.
               return inputStream;
           }

           // on below line we are calling on post execute
           // method to load the url in our pdf view.
           override fun onPostExecute(result: InputStream?) {
               // on below line we are loading url within our
               // pdf view on below line using input stream.
               mypdfView.fromStream(result).load()

           }

       }*/


    companion object {
        val TAG = ViewReportFragment::class.qualifiedName
        //fun getInstance(bundle: Bundle): ViewReportFragment {
           // val fragment = ViewReportFragment()
           // fragment.arguments = bundle
         //   return fragment
      //  }
    }

}