package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult

import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.await


enum class ASteroidApiStatus { LOADING, ERROR, DONE }
private const val API_KEY = "gCwa5SHxwetinsEAoxL9ZP2XABjrtcCJBvdO90pK"
class MainViewModel(application: Application) : AndroidViewModel(application) {


        private val _status = MutableLiveData<ASteroidApiStatus>()
        val status: LiveData<ASteroidApiStatus>
                get() = _status

        /* val picture: LiveData<PictureOfDay>
             get() = repository.picture*/

        private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
        val navigateToSelectedAsteroid: LiveData<Asteroid>
                get() = _navigateToSelectedAsteroid


        private val _asteroids = MutableLiveData<List<Asteroid>>()
        val asteroids: LiveData<List<Asteroid>>
                get() = _asteroids

        // The external immutable LiveData for the navigation property


        private val _pictureOfDay = MutableLiveData<String>()
        val pictureOfDay: LiveData<String>
                get() = _pictureOfDay

        init {
                getNASAAsteroids()
        }

        private fun getNASAAsteroids() {
                viewModelScope.launch {
                        _status.value = ASteroidApiStatus.LOADING
                        try {
                                Log.i("MainViewModel", "Oh No!!")
                                val asteroid = AsteroidApi.retrofitService.getProperties(API_KEY)
                                val json = JSONObject(asteroid)
                                val data = parseAsteroidsJsonResult(json)
                                _asteroids.value = data

                                _pictureOfDay.value = AsteroidApi.retrofitService.getPictureOfDay(API_KEY)


                                _status.value = ASteroidApiStatus.DONE
                        } catch (e: Exception) {
                                _status.value = ASteroidApiStatus.ERROR
                                Log.i("MainViewModel", _asteroids.value.toString())
                                _asteroids.value = ArrayList()
                        }
                }
        }

        fun displayAsteroidDetails(asteroid: Asteroid) {
                _navigateToSelectedAsteroid.value = asteroid
        }

        /**
         * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
         */
        fun displayAsteroidDetailsComplete() {
                _navigateToSelectedAsteroid.value = null
        }

}