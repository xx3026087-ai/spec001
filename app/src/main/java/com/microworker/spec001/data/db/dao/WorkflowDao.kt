package com.microworker.spec001.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.microworker.spec001.data.db.entity.WorkflowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkflowDao {
    @Insert
    suspend fun insert(workflow: WorkflowEntity)

    @Update
    suspend fun update(workflow: WorkflowEntity)

    @Delete
    suspend fun delete(workflow: WorkflowEntity)

    @Query("SELECT * FROM workflows WHERE id = :id")
    suspend fun getById(id: String): WorkflowEntity?

    @Query("SELECT * FROM workflows WHERE enabled = 1 ORDER BY createdAt DESC")
    fun getEnabled(): Flow<List<WorkflowEntity>>

    @Query("SELECT * FROM workflows ORDER BY createdAt DESC")
    fun getAll(): Flow<List<WorkflowEntity>>
}
