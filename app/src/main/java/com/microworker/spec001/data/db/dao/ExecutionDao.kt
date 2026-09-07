package com.microworker.spec001.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.microworker.spec001.data.db.entity.ExecutionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExecutionDao {
    @Insert
    suspend fun insert(execution: ExecutionEntity)

    @Update
    suspend fun update(execution: ExecutionEntity)

    @Delete
    suspend fun delete(execution: ExecutionEntity)

    @Query("SELECT * FROM executions WHERE id = :id")
    suspend fun getById(id: String): ExecutionEntity?

    @Query("SELECT * FROM executions WHERE taskId = :taskId ORDER BY stepIndex ASC")
    fun getByTaskId(taskId: String): Flow<List<ExecutionEntity>>

    @Query("SELECT * FROM executions WHERE taskId = :taskId AND status = :status")
    suspend fun getByTaskIdAndStatus(taskId: String, status: String): List<ExecutionEntity>

    @Query("SELECT * FROM executions WHERE taskId = :taskId ORDER BY stepIndex DESC LIMIT 1")
    suspend fun getLastByTaskId(taskId: String): ExecutionEntity?

    @Query("DELETE FROM executions WHERE taskId = :taskId")
    suspend fun deleteByTaskId(taskId: String)
}
