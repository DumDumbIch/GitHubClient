package com.dumdumbich.sketchbook.githubclient.data.db.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dumdumbich.sketchbook.githubclient.data.db.room.dao.IRepositoryDao
import com.dumdumbich.sketchbook.githubclient.data.db.room.dao.IUserDao
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubRepositoryEntity
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubUserEntity
import java.lang.IllegalStateException

@androidx.room.Database(
    entities = [
        GitHubUserEntity::class,
        GitHubRepositoryEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val userDao: IUserDao
    abstract val repositoryDao: IRepositoryDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: Database? = null

        fun getInstance() = instance ?: throw IllegalStateException("Database has not been created")
        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
            }
        }
    }

}