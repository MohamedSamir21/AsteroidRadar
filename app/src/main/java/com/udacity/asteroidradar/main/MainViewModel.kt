package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.database.AsteroidsDatabaseDao
import kotlinx.coroutines.launch

enum class ASteroidApiStatus { LOADING, ERROR, DONE }

private const val API_KEY = "gCwa5SHxwetinsEAoxL9ZP2XABjrtcCJBvdO90pK"

class MainViewModel (application: Application) : AndroidViewModel(application){

        private val _status = MutableLiveData<ASteroidApiStatus>()
        val status: LiveData<ASteroidApiStatus>
                get() = _status

        private val _asteroids = MutableLiveData<List<Asteroid>>()
        val asteroids: LiveData<List<Asteroid>>
                get() = _asteroids

//        val myList = listOf<Asteroid>(
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false),
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false),
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false),
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false),
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false),
//                Asteroid(1,"dsf","fdsf", 1.0, 4.3, 5.1, 4.3, false)
//                )

        private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

        val navigateToSelectedAsteroid: LiveData<Asteroid>
                get() = _navigateToSelectedAsteroid


        init {
            getNASAAsteroids()
        }

        private fun getNASAAsteroids() {
                viewModelScope.launch {
                        _status.value = ASteroidApiStatus.LOADING
                        try {
                                _asteroids.value = AsteroidApi.retrofitService.getProperties(API_KEY)
                                _status.value = ASteroidApiStatus.DONE
                        } catch (e: Exception) {
                                _status.value = ASteroidApiStatus.ERROR
                                _asteroids.value = ArrayList()
                        }
                }
        }

        fun displayAsteroidDetails(asteroidDetails: Asteroid) {
                _navigateToSelectedAsteroid.value = asteroidDetails
        }

        fun displayAsteroidDetailsComplete() {
                _navigateToSelectedAsteroid.value = null
        }


}