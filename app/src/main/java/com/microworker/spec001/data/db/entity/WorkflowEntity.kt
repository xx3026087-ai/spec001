package com.microworker.spec001.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String? = null,
    val enabled: Boolean = true,
    val triggerType: String, // "manual", "scheduled", etc.
    val triggerConfig: String? = null, // JSON
    val definitionJson: String, // Full workflow definition
    val createdAt: Long,
    val updatedAt: Long
)
