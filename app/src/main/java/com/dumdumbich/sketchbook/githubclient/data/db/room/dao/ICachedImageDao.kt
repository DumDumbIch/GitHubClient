package com.dumdumbich.sketchbook.githubclient.data.db.room.dao

import androidx.room.*
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.CachedImageEntity

@Dao
interface ICachedImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: CachedImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg images: CachedImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<CachedImageEntity>)

    @Update
    fun update(image: CachedImageEntity)

    @Update
    fun update(vararg images: CachedImageEntity)

    @Update
    fun update(images: List<CachedImageEntity>)

    @Delete
    fun delete(image: CachedImageEntity)

    @Delete
    fun delete(vararg images: CachedImageEntity)

    @Delete
    fun delete(images: List<CachedImageEntity>)

    @Query("DELETE FROM CachedImageEntity")
    fun clear()

    @Query("SELECT * FROM CachedImageEntity")
    fun getAll(): List<CachedImageEntity>

    @Query("SELECT * FROM CachedImageEntity WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): CachedImageEntity?

}