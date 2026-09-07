package com.microworker.spec001.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.microworker.spec001.data.db.entity.ModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {
    @Insert
    suspend fun insert(model: ModelEntity)

    @Update
    suspend fun update(model: ModelEntity)

    @Delete
    suspend fun delete(model: ModelEntity)

    @Query("SELECT * FROM models WHERE id = :id")
    suspend fun getById(id: String): ModelEntity?

    @Query("SELECT * FROM models WHERE status = :status ORDER BY createdAt DESC")
    fun getByStatus(status: String): Flow<List<ModelEntity>>

    @Query("SELECT * FROM models ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ModelEntity>>

    @Query("SELECT * FROM models WHERE sha256 = :sha256 LIMIT 1")
    suspend fun getBySha256(sha256: String): ModelEntity?
}
