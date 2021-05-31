package com.arga.jetpack.submission3.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dao(): MyDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        private val sLock = Any()

        fun getInstance(context: Context): MyDatabase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "armovies.db"
                    )
                        .build()
                }
                return INSTANCE as MyDatabase
            }
        }
    }
}