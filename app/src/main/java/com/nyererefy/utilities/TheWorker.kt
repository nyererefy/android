package com.nyererefy.utilities

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class TheWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Timber.d("Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return Result.success()
    }
}