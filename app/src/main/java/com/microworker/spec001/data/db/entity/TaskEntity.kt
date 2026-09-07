package com.microworker.spec001.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = WorkflowEntity::class,
            parentColumns = ["id"],
            childColumns = ["workflowId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workflowId"),
        Index("status")
    ]
)
data class TaskEntity(
    @PrimaryKey val id: String,
    val workflowId: String,
    val inputUri: String? = null,
    val inputHash: String? = null,
    val status: String, // QUEUED, RUNNING, PAUSED, COMPLETED, FAILED, CANCELLED
    val priority: Int = 0,
    val createdAt: Long,
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val error: String? = null
)
