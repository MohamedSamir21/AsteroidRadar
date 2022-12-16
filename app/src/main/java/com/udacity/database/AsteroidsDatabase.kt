package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.database.AsteroidsDatabaseDao

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1)
abstract class AsteroidsDatabase: RoomDatabase() {

    abstract val pictureDao: PictureOfDayDao
    abstract val asteroidsDatabaseDao: AsteroidsDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: AsteroidsDatabase? = null

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Dao
interface PictureOfDayDao{
    @Query("SELECT * FROM PictureOfDay")
    fun getPictureOfDay(): LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay:  PictureOfDay)

    @Query("DELETE FROM PictureOfDay")
    fun deleteOldPictureOfDay()

    @Update
    fun updatePictureOfDay(pictureOfDay: PictureOfDay){
        deleteOldPictureOfDay()
        insert(pictureOfDay)
    }
}