package com.ironsource.bootapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ironsource.bootapp.data.BootEventRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Battery Resource Utilization
 * If the user places your app in the "restricted" state for background battery usage while your app targets Android 13,
 * the system doesn't deliver the BOOT_COMPLETED broadcast or the LOCKED_BOOT_COMPLETED broadcast until the app is started for other reasons.
 */
@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver(), CoroutineScope by MainScope() {
    @Inject
    lateinit var repository: BootEventRepo

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            launch {
                repository.saveBootEvent()
            }.invokeOnCompletion {
                // Coroutine completed or cancelled, perform any cleanup if needed
                cancel()
            }
        }
    }
}