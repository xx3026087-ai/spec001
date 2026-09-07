package com.microworker.spec001.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "executions",
    foreignKeys = [
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("taskId"),
        Index("status")
    ]
)
data class ExecutionEntity(
    @PrimaryKey val id: String,
    val taskId: String,
    val stepIndex: Int,
    val stepType: String,
    val status: String, // PENDING, RUNNING, COMPLETED, FAILED
    val inputJson: String? = null,
    val outputJson: String? = null,
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val durationMs: Long? = null,
    val error: String? = null
)
