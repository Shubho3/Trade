package com.nr.nrsales.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.nr.nrsales.MainApplication
import com.nr.nrsales.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random


/**
 * Created by Deepak Sharma on 01/07/19.
 */
class GlobalUtility {

    companion object {

        private var mDialog: Dialog? = null
        private var isProgressDialogRunning = false
        fun persistImage(bitmap: Bitmap, cOntext: Context): File {
            val filesDir = cOntext.cacheDir
            @SuppressLint("SimpleDateFormat") val imageFile = File(filesDir, SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg")
            var os: OutputStream? = null
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    os = Files.newOutputStream(imageFile.toPath())
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, os!!)
                os.flush()
                os.close()
            } catch (e: java.lang.Exception) {
                Log.e("TAG", "persistImage: " + e.message)
            }
            return imageFile
        }

        @SuppressLint("Range")
        fun getPath(uri: Uri?, activity: Activity): String {
            var path: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = uri?.let { activity.contentResolver.query(it, null, null, null, null) }
            cursor?.moveToFirst()
            var document_id = cursor?.getString(0)
            document_id = document_id?.substring(document_id.lastIndexOf(":") + 1)
            cursor?.close()
            cursor = activity.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                arrayOf(document_id), null
            )
            if (cursor?.moveToFirst() == true) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor?.close()
            return "" + path
        }

        fun saveToInternalStorage(bitmapImage: Bitmap, activity: Activity): String? {
            val today = Date()
            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
            val dateToStr = format.format(today)
            val cw = ContextWrapper(activity)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val mypath = File(directory, "profile_$dateToStr.JPEG")
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath.absolutePath
        }

        fun saveToInternalStorageFile(bitmapImage: Bitmap, activity: Activity): File {
            val today = Date()
            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
            val dateToStr = format.format(today)
            val cw = ContextWrapper(activity)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val mypath = File(directory, "profile_$dateToStr.JPEG")
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath
        }

        fun showProgressMessage(dialogActivity: Activity?, msg: String?) {
            try {
                if (isProgressDialogRunning) {
                    hideProgressMessage()
                }
                isProgressDialogRunning = true
                mDialog = Dialog(dialogActivity!!)
                mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog!!.setContentView(R.layout.dialog_loading)
                mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val lp = mDialog!!.window!!.attributes
                lp.dimAmount = 0.5f
                mDialog!!.window!!.attributes = lp
                mDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(true)
                mDialog!!.show()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun showNoNetworkMessage(activity: Activity) {
            try {
                val mNoNetworkDialog = Dialog(activity)
                mNoNetworkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mNoNetworkDialog.setContentView(R.layout.dialog_no_internet)
                mNoNetworkDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val lp = mNoNetworkDialog.window!!.attributes
                lp.dimAmount = 0.1f
                mNoNetworkDialog.window!!.attributes = lp
                mNoNetworkDialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                mNoNetworkDialog.setCancelable(true)
                mNoNetworkDialog.setCanceledOnTouchOutside(true)
                val txt: Button = mNoNetworkDialog.findViewById(R.id.go_on)
                txt.setOnClickListener {
                    mNoNetworkDialog.dismiss()
                }
                mNoNetworkDialog.show()


            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun showSuccessMessage(dialogActivity: Activity?, titleMsg: String?, msg: String?) {
            try {
                var mSuccessDialog: Dialog? = Dialog(dialogActivity!!)
                mSuccessDialog!!.setTitle(titleMsg)
                //  mSuccessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mSuccessDialog.setContentView(R.layout.dialog_success)
                mSuccessDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val lp = mSuccessDialog.window!!.attributes
                lp.dimAmount = 0.1f
                mSuccessDialog.window!!.attributes = lp
                mSuccessDialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                mSuccessDialog.setCancelable(true)
                mSuccessDialog.setCanceledOnTouchOutside(true)
                var txt: TextView = mSuccessDialog.findViewById(R.id.msg)
                txt.text = msg
                txt.setOnClickListener {
                    mSuccessDialog.dismiss()
                }
                mSuccessDialog.show()


            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun hideProgressMessage() {
            isProgressDialogRunning = true
            try {
                if (mDialog != null) {
                    mDialog!!.dismiss()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }


        private var snackbar: Snackbar? = null
        fun print(tag: String?, msg: String) {
            Log.d(tag, msg)
        }

        fun showToast(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } fun showToast(message: String) {
            Toast.makeText(MainApplication.appContext, message, Toast.LENGTH_LONG).show()
        }

        fun initSnackBar(context: Activity, networkConnected: Boolean) {
            if (networkConnected && snackbar != null && snackbar?.isShown!!) {
                updateSnackbar(snackbar!!)
                return
            }
            snackbar =
                Snackbar.make(
                    context.findViewById<View>(android.R.id.content),
                    "",
                    Snackbar.LENGTH_INDEFINITE
                )//.setBehavior(NoSwipeBehavior())
            snackbar?.let {
                val layoutParams =
                    (it.view.layoutParams as FrameLayout.LayoutParams)
                        .also { lp -> lp.setMargins(0, 0, 0, 0) }
                it.view.layoutParams = layoutParams
                it.view.alpha = 0.90f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    it.view.elevation = 0f
                }
                val message = "no internet connection"
                if (networkConnected) updateSnackbar(it)
                else it.view.setBackgroundColor(Color.RED)
                val spannableString = SpannableString(message).apply {
                    setSpan(ForegroundColorSpan(Color.WHITE), 0, message.length, 0)
                }
                it.setText(spannableString)
                it.show()
            }
        }

        private fun updateSnackbar(view: Snackbar) {
            if (view != null) {
                val color = arrayOf(
                    ColorDrawable(Color.RED),
                    ColorDrawable(Color.GREEN)
                )
                val trans = TransitionDrawable(color)
                view.view.background = (trans)
                trans.startTransition(500)
                val handler = Handler()
                handler.postDelayed({ view.dismiss() }, 1300)
                view.setText("back online")
            }
        }


        fun hideKeyboard(activity: Activity) {
            try {
                val inputManager =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            } catch (ignored: Exception) {
                ignored.printStackTrace()
                Log.d("TAG", "hideKeyboard: " + ignored.message)
            }

        }

        /***
         * Show SoftInput Keyboard
         * @param activity reference of current activity
         */
        fun showKeyboard(activity: Activity?) {
            if (activity != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }
        }

        fun handleUI(activity: Activity, view: View, isBlockUi: Boolean) {
            if (isBlockUi) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                view.visibility = View.VISIBLE
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                view.visibility = View.GONE
            }
        }

        fun getLayoutBinding(context: Context?, layout: Int): ViewDataBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(context),
                layout,
                null, false
            )
        }

        /**
         * two digit random number
         *
         * @return random number
         */
        fun getTwoDigitRandomNo(): Int {
            return Random().nextInt(90) + 10
        }

        fun setSpannable(textView: TextView, txtSpannable: String, starText: Int, endText: Int) {
            val spannableString = SpannableString(txtSpannable)
            val foregroundSpan = ForegroundColorSpan(Color.GREEN)
            //            BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(Color.GRAY);
            spannableString.setSpan(
                foregroundSpan,
                starText,
                endText,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //            spannableString.setSpan(backgroundSpan, starText, endText, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.text = spannableString
        }

        fun btnClickAnimation(view: View) {
            val fadeAnimation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
            view.startAnimation(fadeAnimation)
        }


        fun avoidDoubleClicks(view: View) {
            val DELAY_IN_MS: Long = 500
            if (!view.isClickable) {
                return
            }
            view.isClickable = false
            view.postDelayed({ view.isClickable = true }, DELAY_IN_MS)
        }

        fun closeAppDialog(context: Context){
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder
                .setMessage("are sure you want to logout").setCancelable(false)
                .setPositiveButton(
                    "yes"
                ) { dialog, _ ->

                    dialog.cancel()
                    return@setPositiveButton
                }
                .setNegativeButton("no")
                { dialog, _ ->
                    return@setNegativeButton
                    dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
    }
}
