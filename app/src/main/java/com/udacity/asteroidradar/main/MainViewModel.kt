package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDatabase.Companion.getInstance
import com.udacity.repository.AsteroidRepository
import kotlinx.coroutines.launch


enum class ASteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {


        private val _status = MutableLiveData<ASteroidApiStatus>()
        val status: LiveData<ASteroidApiStatus>
                get() = _status

        private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
        val navigateToSelectedAsteroid: LiveData<Asteroid>
                get() = _navigateToSelectedAsteroid


        private val database = getInstance(application)
        private val asteroidRepository = AsteroidRepository(database)

        val mediatorAsteroids = MediatorLiveData<List<Asteroid>>()
        private val weekAsteroids = asteroidRepository.weekAsteroids
        private val todayAsteroids = asteroidRepository.todayAsteroids

        val pictureOfDay = asteroidRepository.pictureOfDay

        init {
                getNASAAsteroids()
                mediatorAsteroids.addSource(weekAsteroids) {
                        mediatorAsteroids.value = it
                }
        }
        private fun getNASAAsteroids() {
                viewModelScope.launch {
                        _status.value = ASteroidApiStatus.LOADING
                        try {
                                asteroidRepository.updateAsteroids()
                                asteroidRepository.updatePictureOfDay()
                                _status.value = ASteroidApiStatus.DONE
                        } catch (e: Exception) {
                                Log.i("MainViewModel", e.message.toString())
                                _status.value = ASteroidApiStatus.ERROR
                        }
                }
        }


        fun viewSavedAsteroids() {
                mediatorAsteroids.removeSource(todayAsteroids)
                mediatorAsteroids.removeSource(weekAsteroids)
                mediatorAsteroids.addSource(weekAsteroids) {
                        mediatorAsteroids.value = it
                }

        }

        fun viewWeekAsteroids() {
                mediatorAsteroids.removeSource(todayAsteroids)
                mediatorAsteroids.removeSource(weekAsteroids)
                mediatorAsteroids.addSource(weekAsteroids) {
                        mediatorAsteroids.value = it
                }

        }


        fun viewTodayAsteroid() {
                mediatorAsteroids.removeSource(todayAsteroids)
                mediatorAsteroids.removeSource(weekAsteroids)
                mediatorAsteroids.addSource(todayAsteroids) {
                        mediatorAsteroids.value = it
                }
        }


        fun displayAsteroidDetails(asteroid: Asteroid) {
                _navigateToSelectedAsteroid.value = asteroid
        }

        fun displayAsteroidDetailsComplete() {
                _navigateToSelectedAsteroid.value = null
        }

}
