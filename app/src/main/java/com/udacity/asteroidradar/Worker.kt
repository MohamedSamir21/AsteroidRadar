package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase.Companion.getInstance
import com.udacity.repository.AsteroidRepository
import retrofit2.HttpException

class Worker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val asteroidRepository = AsteroidRepository(database)

        try {
            asteroidRepository.updateAsteroids()
            asteroidRepository.updatePictureOfDay()
            return Result.success()
        }catch (e: HttpException){
            return Result.retry()
        }
    }
}