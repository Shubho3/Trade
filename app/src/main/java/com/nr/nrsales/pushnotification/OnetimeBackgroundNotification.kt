package com.nr.nrsales.pushnotification

import android.Manifest
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nr.nrsales.MainActivity
import com.nr.nrsales.R
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_DESCRIPTION
import com.nr.nrsales.pushnotification.Constants.ONETIME_WORK_TITLE
import com.nr.nrsales.ui.LaunchingActivity

object Constants {
    const val CHANNEL_ID_PERIOD_WORK = "PERIODIC_APP_UPDATES"
    const val CHANNEL_ID_ONE_TIME_WORK = "1"
    const val ONETIME_WORK_DESCRIPTION = "ONETIME_WORK_DESCRIPTION"
    const val ONETIME_WORK_TITLE = "ONETIME_WORK_TITLE"
    const val LAST_PERIODIC_TIME = "LAST_PERIODIC_TIME"
}
class OnetimeBackgroundNotification(private val context: Context, private val workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }


    private fun showNotification() {

        val intent = Intent(context, LaunchingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = NotificationCompat.Builder(context,
            Constants.CHANNEL_ID_ONE_TIME_WORK
        ).apply {
            setContentIntent(pendingIntent)
        }
        notification.setContentTitle(workerParameters.inputData.getString(ONETIME_WORK_TITLE))
        notification.setContentText(workerParameters.inputData.getString(ONETIME_WORK_DESCRIPTION))
        notification.priority = NotificationCompat.PRIORITY_DEFAULT
        notification.setCategory(NotificationCompat.CATEGORY_ALARM)
        notification.setSmallIcon(R.drawable.logo_white)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notification.setSound(sound)
        val vibrate = longArrayOf(0, 100, 200, 300)
        notification.setVibrate(vibrate)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(applicationContext
                    , Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            notify(2, notification.build())
        }
    }
}
