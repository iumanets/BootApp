package com.ironsource.bootapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ironsource.bootapp.R
import com.ironsource.bootapp.data.BootEventRepo
import com.ironsource.bootapp.data.local.model.BootEventDto
import com.ironsource.bootapp.data.local.model.SortingOrder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

private const val NOTIFICATION_WORK_NAME = "notification_work"
private const val NOTIFICATION_CHANNEL_ID = "reboot_tracker_channel"
private const val NOTIFICATION_ID = 1

object NotificationWorkManager {
    fun enque(context: Context) {
        val request = PeriodicWorkRequestBuilder<NotifierWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        )
    }
}

@HiltWorker
class NotifierWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val bootEventRepo: BootEventRepo
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val events = bootEventRepo.getEvents(SortingOrder.DESC).take(2)
        showNotification(getBody(events))

        return Result.success()
    }

    private fun showNotification(body: String) {
        val notificationManager = context.getSystemService<NotificationManager>()!!
        val title = context.getString(R.string.app_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(false)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun getBody(events: List<BootEventDto>): String {
        return when {
            events.isEmpty() -> context.getString(R.string.no_boot)

            events.size == 1 -> String.format(
                context.getString(R.string.one_boot),
                events.first().date.time
            )

            events.size == 2 -> {
                val delta = events[0].date.time - events[1].date.time
                String.format(context.getString(R.string.one_boot), delta)
            }

            else -> ""
        }
    }
}