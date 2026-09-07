package com.microworker.spec001.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "models")
data class ModelEntity(
    @PrimaryKey val id: String,
    val name: String,
    val version: String,
    val format: String = "GGUF",
    val parameterCount: Long = 0,
    val quantization: String? = null,
    val fileUri: String,
    val fileSizeBytes: Long,
    val contextLength: Int = 2048,
    val minRamMb: Int = 512,
    val sha256: String?,
    val status: String, // READY, LOADING, ERROR, DELETED
    val createdAt: Long
)
