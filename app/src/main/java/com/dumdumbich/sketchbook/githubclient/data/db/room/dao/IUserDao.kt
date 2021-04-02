package com.dumdumbich.sketchbook.githubclient.data.db.room.dao

import androidx.room.*
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubUserEntity

@Dao
interface IUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GitHubUserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: GitHubUserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<GitHubUserEntity>)

    @Update
    fun update(user: GitHubUserEntity)

    @Update
    fun update(vararg users: GitHubUserEntity)

    @Update
    fun update(users: List<GitHubUserEntity>)

    @Delete
    fun delete(user: GitHubUserEntity)

    @Delete
    fun delete(vararg users: GitHubUserEntity)

    @Delete
    fun delete(users: List<GitHubUserEntity>)

    @Query("SELECT * FROM GitHubUserEntity")
    fun getAll(): List<GitHubUserEntity>

    @Query("SELECT * FROM GitHubUserEntity WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): GitHubUserEntity?

}