package com.microworker.spec001.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    // Migration 1 -> 2: Add new column example
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Example: ALTER TABLE tasks ADD COLUMN new_column TEXT;
            // Implement as schema evolves
        }
    }
}
