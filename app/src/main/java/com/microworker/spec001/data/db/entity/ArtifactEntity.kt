package com.microworker.spec001.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "artifacts",
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
        Index("createdAt")
    ]
)
data class ArtifactEntity(
    @PrimaryKey val id: String,
    val taskId: String,
    val filename: String,
    val mimeType: String,
    val pathOrUri: String,
    val sha256: String,
    val sizeBytes: Long,
    val createdAt: Long
)
