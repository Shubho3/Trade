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


    lateinit var sessionManager: SharedPrf

    override fun onNewToken(s: String) {
        super.onNewToken(s)

        //goferSinchService.getManagedPush(s).registerPushToken(this);
    }

    override fun onCreate() {
        super.onCreate()

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


/*package com.nr.nrsales.pushnotification

/**
 * @package com.trioangle.gofer
 * @subpackage pushnotification
 * @category FirebaseMessagingService
 * @author Trioangle Product Team
 *
 */


import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nr.nrsales.MainActivity
import com.nr.nrsales.MainApplication
import com.nr.nrsales.R
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_DESCRIPTION
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_TITLE
import com.nr.nrsales.utils.SharedPrf
import org.json.JSONObject
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {


    lateinit var sessionManager: SharedPrf

    override fun onNewToken(s: String) {
        super.onNewToken(s)

        //goferSinchService.getManagedPush(s).registerPushToken(this);
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "From tri: " + remoteMessage.from!!)
        Log.e(TAG, "From tri: $remoteMessage")
        if (remoteMessage.data.isNotEmpty()) {
            try {
                Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())
            val json = (remoteMessage.data as Map<*, *>?)?.let { JSONObject(it) }
            Log.e(TAG, "Data Payload: " + json.toString())
            Log.e(TAG, "Data Payload: " + json?.getString("message"))
            Log.e(TAG, "Data Payload: " + json?.getString("key"))
                val title :String=json?.getString("key").toString()
                val desc :String=json?.getString("message").toString()
               showNotification(MainApplication.appContext, title, desc)
            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
                Log.e(TAG, "Exception: " + e.localizedMessage)
                Log.e(TAG, "Exception: " + e.cause)
            }

        }
/*
        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())
            try {
                val json = (remoteMessage.data as Map<*, *>?)?.let { JSONObject(it) }
                // handleDataMessage(json)
                if (json != null) {
                    handleDataMessage(json, applicationContext)
                }else{
                    handleDataMessage(JSONObject(remoteMessage.data.toString()), applicationContext)

                }
                if (remoteMessage.notification != null) {
                    Log.e(TAG, "Notification Body: " + remoteMessage.notification!!.body!!)

                }

            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
                Log.e(TAG, "Exception: " + e.localizedMessage)
                Log.e(TAG, "Exception: " + e.cause)
            }

        }
*/

    }
    private fun showNotification(context: Context,title:String,desc:String) {
        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = TaskStackBuilder.create(context).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }

            val notification = NotificationCompat.Builder(
                context,
                Constants.CHANNEL_ID_ONE_TIME_WORK
            ).apply {
                setContentIntent(pendingIntent)
            }
            notification.setContentTitle(title)
            notification.setContentText(desc)
            notification.priority = NotificationCompat.PRIORITY_HIGH
            notification.setCategory(NotificationCompat.CATEGORY_ALARM)
            notification.setSmallIcon(R.drawable.logo_white)
            val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notification.setSound(sound)
            val vibrate = longArrayOf(0, 100, 200, 300)
            notification.setVibrate(vibrate)
            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext, Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(2, notification.build())
            }
        }catch (e:Exception){
            Log.e(TAG, "showNotification: "+e.message )
            Log.e(TAG, "showNotification: "+e.localizedMessage )
            Log.e(TAG, "showNotification: "+e.cause )
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun handleDataMessage(json: JSONObject, context: Context) {
        var message = json.getString("message")
        var status = ""
        var title = json.getString("key")
        var orderId = 0
  val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val onetimeWork = OneTimeWorkRequest.Builder(OnetimeBackgroundNotification::class.java)
        onetimeWork.setConstraints(constraints)
        val data = Data.Builder()
        data.putString(ONETIME_WORK_DESCRIPTION,message)
        data.putString(ONETIME_WORK_TITLE,title)
        onetimeWork.setInputData(data.build())
        WorkManager.getInstance(this).enqueue(onetimeWork.build())



          val pushNotification = Intent(Config.PUSH_NOTIFICATION)
        pushNotification.putExtra("message", json.getString("message"))
        LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotification)

    }
    companion object {

        val TAG = MyFirebaseMessagingService::class.java.simpleName

       public fun getFirebaseToken(context: Context):String{
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result

                Log.d("TAG", "Get new FCM registration token --- $token")
                SharedPrf(context).setStoredTag(SharedPrf.FIREBASE_TOKEN,token)
            })

           return  SharedPrf(context).getStoredTag(SharedPrf.FIREBASE_TOKEN)
        }

    }


}
*/
