package com.example.kinopoiskfintech.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinopoiskfintech.data.local.DatabaseConstants.DATABASE_NAME
import com.example.kinopoiskfintech.data.local.models.MovieDbModel

@Database(entities = [MovieDbModel::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    companion object {
        private var db: MoviesDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME).build()
                db = instance
                return instance
            }
        }
    }

}