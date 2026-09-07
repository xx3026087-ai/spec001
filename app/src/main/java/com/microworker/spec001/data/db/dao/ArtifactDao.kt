package com.microworker.spec001.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.microworker.spec001.data.db.entity.ArtifactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtifactDao {
    @Insert
    suspend fun insert(artifact: ArtifactEntity)

    @Update
    suspend fun update(artifact: ArtifactEntity)

    @Delete
    suspend fun delete(artifact: ArtifactEntity)

    @Query("SELECT * FROM artifacts WHERE id = :id")
    suspend fun getById(id: String): ArtifactEntity?

    @Query("SELECT * FROM artifacts WHERE taskId = :taskId ORDER BY createdAt DESC")
    fun getByTaskId(taskId: String): Flow<List<ArtifactEntity>>

    @Query("SELECT * FROM artifacts WHERE sha256 = :sha256 LIMIT 1")
    suspend fun getBySha256(sha256: String): ArtifactEntity?

    @Query("SELECT * FROM artifacts ORDER BY createdAt DESC LIMIT :limit")
    fun getRecent(limit: Int = 100): Flow<List<ArtifactEntity>>

    @Query("DELETE FROM artifacts WHERE taskId = :taskId")
    suspend fun deleteByTaskId(taskId: String)
}
