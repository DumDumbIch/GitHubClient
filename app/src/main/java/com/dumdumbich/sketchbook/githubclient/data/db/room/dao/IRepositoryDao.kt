package com.dumdumbich.sketchbook.githubclient.data.db.room.dao

import androidx.room.*
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubRepositoryEntity

@Dao
interface IRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: GitHubRepositoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repositories: GitHubRepositoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<GitHubRepositoryEntity>)

    @Update
    fun update(user: GitHubRepositoryEntity)

    @Update
    fun update(vararg users: GitHubRepositoryEntity)

    @Update
    fun update(users: List<GitHubRepositoryEntity>)

    @Delete
    fun delete(user: GitHubRepositoryEntity)

    @Delete
    fun delete(vararg users: GitHubRepositoryEntity)

    @Delete
    fun delete(users: List<GitHubRepositoryEntity>)

    @Query("SELECT * FROM GitHubRepositoryEntity")
    fun getAll(): List<GitHubRepositoryEntity>

    @Query("SELECT * FROM GitHubRepositoryEntity WHERE userId = :userId")
    fun findForUser(userId: String): List<GitHubRepositoryEntity>

}
