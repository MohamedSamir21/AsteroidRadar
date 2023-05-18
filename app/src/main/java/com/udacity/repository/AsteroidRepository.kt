
package com.udacity.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidsDatabase) {
    val weekAsteroids: LiveData<List<Asteroid>> =database.asteroidsDatabaseDao.getAllAsteroids()
    val todayAsteroids: LiveData<List<Asteroid>> = database.asteroidsDatabaseDao.getTodayAsteroids(
        SimpleDateFormat("yyyy-MM-dd").format(Date())
    )

    val pictureOfDay: LiveData<PictureOfDay> = database.pictureDao.getPictureOfDay()

    suspend fun updateAsteroids(){
        withContext(Dispatchers.IO){
            val data = AsteroidApi.retrofitService.getProperties(API_KEY)
            val json = JSONObject(data)
            val asteroids = parseAsteroidsJsonResult(json)
            database.asteroidsDatabaseDao.update(asteroids)
        }
    }

    suspend fun updatePictureOfDay(){
        withContext(Dispatchers.IO){
            val pictureOfDay = AsteroidApi.retrofitService.getPictureOfDay(API_KEY)
            database.pictureDao.updatePictureOfDay(pictureOfDay)
        }
    }
}