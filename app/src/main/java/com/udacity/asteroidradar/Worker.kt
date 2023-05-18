package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.database.AsteroidsDatabase.Companion.getInstance
import com.udacity.repository.AsteroidRepository
import retrofit2.HttpException

class Worker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val asteroidRepository = AsteroidRepository(database)

        return try {
            asteroidRepository.updateAsteroids()
            asteroidRepository.updatePictureOfDay()
            Result.success()
        }catch (e: HttpException){
            Result.retry()
        }
    }
}