package com.microworker.spec001.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.microworker.spec001.data.db.dao.ArtifactDao
import com.microworker.spec001.data.db.dao.ExecutionDao
import com.microworker.spec001.data.db.dao.ModelDao
import com.microworker.spec001.data.db.dao.TaskDao
import com.microworker.spec001.data.db.dao.WorkflowDao
import com.microworker.spec001.data.db.entity.ArtifactEntity
import com.microworker.spec001.data.db.entity.ExecutionEntity
import com.microworker.spec001.data.db.entity.ModelEntity
import com.microworker.spec001.data.db.entity.TaskEntity
import com.microworker.spec001.data.db.entity.WorkflowEntity

@Database(
    entities = [
        ModelEntity::class,
        WorkflowEntity::class,
        TaskEntity::class,
        ExecutionEntity::class,
        ArtifactEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun modelDao(): ModelDao
    abstract fun workflowDao(): WorkflowDao
    abstract fun taskDao(): TaskDao
    abstract fun executionDao(): ExecutionDao
    abstract fun artifactDao(): ArtifactDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "spec001.db"
                )
                    .fallbackToDestructiveMigration() // Phase 1: destructive OK
                    .build()
                    .also { instance = it }
            }
        }
    }
}
