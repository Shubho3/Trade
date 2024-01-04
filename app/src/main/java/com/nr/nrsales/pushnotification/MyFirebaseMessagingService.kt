package com.nr.nrsales.pushnotification

/**
 * @package com.trioangle.gofer
 * @subpackage pushnotification
 * @category FirebaseMessagingService
 * @author Trioangle Product Team
 *
 */


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_DESCRIPTION
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_TITLE
import com.nr.nrsales.utils.SharedPrf
import org.json.JSONObject
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onNewToken(s: String) {
        super.onNewToken(s)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
          if (remoteMessage.notification?.title!=null){
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()
            val onetimeWork = OneTimeWorkRequest.Builder(OnetimeBackgroundNotification::class.java)
            onetimeWork.setConstraints(constraints)
            val data = Data.Builder()
            data.putString(ONETIME_WORK_DESCRIPTION,remoteMessage.notification?.body)
            data.putString(ONETIME_WORK_TITLE,remoteMessage.notification?.title)
            onetimeWork.setInputData(data.build())
            WorkManager.getInstance(this).enqueue(onetimeWork.build())
        }
        if (remoteMessage.data.isNotEmpty()) {
            try {
                val json = (remoteMessage.data as Map<*, *>?)?.let { JSONObject(it) }
                handleDataMessage(json!!, applicationContext)

            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
            }

        }

    }


    @SuppressLint("SuspiciousIndentation")
    fun handleDataMessage(json: JSONObject, context: Context) {
        Log.e(TAG, "Data Payload: " + json.toString())
        Log.e(TAG, "Data Payload: " + json.getString("message"))
        Log.e(TAG, "Data Payload: " + json.getString("key"))
        val title :String=json.getString("key").toString()
        val desc :String=json.getString("message").toString()
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val onetimeWork = OneTimeWorkRequest.Builder(OnetimeBackgroundNotification::class.java)
        onetimeWork.setConstraints(constraints)
        val data = Data.Builder()
        data.putString(ONETIME_WORK_DESCRIPTION,desc)
        data.putString(ONETIME_WORK_TITLE,title)
        onetimeWork.setInputData(data.build())
        WorkManager.getInstance(this).enqueue(onetimeWork.build())
        val pushNotification = Intent(Config.PUSH_NOTIFICATION)
        pushNotification.putExtra("message", desc)
        LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotification)

    }
    companion object {

        val TAG = MyFirebaseMessagingService::class.java.simpleName

        public fun getFirebaseToken(context: Context){
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result

                Log.d("TAG", "Get new FCM registration token --- $token")
                SharedPrf(context).setStoredTag(SharedPrf.FIREBASE_TOKEN,token)
            })

        }
    }


}
