package com.binar.melif.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.binar.melif.data.local.dao.MovieDao
import com.binar.melif.data.local.dao.TvDao
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(entities = [FavoriteMovieEntity::class, FavoriteTvEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao


    companion object {
        private const val DB_NAME = "db_melif"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseSeederCallback(context))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    class DatabaseSeederCallback(private val context: Context) : RoomDatabase.Callback() {
        private val job = Job()
        private val scope = CoroutineScope(Dispatchers.IO + job)

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
//            AppDatabase.getInstance(context).memberDao().insertMembers(prepopulateNotes())
            }
        }
    }
}