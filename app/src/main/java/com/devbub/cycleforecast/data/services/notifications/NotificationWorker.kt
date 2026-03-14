package com.devbub.cycleforecast.data.services.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            NotificationHelper(context).showNotification()
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}


