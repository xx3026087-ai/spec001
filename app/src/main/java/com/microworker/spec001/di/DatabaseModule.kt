package com.microworker.spec001.di

import android.content.Context
import com.microworker.spec001.data.db.AppDatabase
import com.microworker.spec001.data.repository.ArtifactRepository
import com.microworker.spec001.data.repository.ExecutionRepository
import com.microworker.spec001.data.repository.ModelRepository
import com.microworker.spec001.data.repository.TaskRepository
import com.microworker.spec001.data.repository.WorkflowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideModelRepository(db: AppDatabase): ModelRepository =
        ModelRepository(db.modelDao())

    @Singleton
    @Provides
    fun provideWorkflowRepository(db: AppDatabase): WorkflowRepository =
        WorkflowRepository(db.workflowDao())

    @Singleton
    @Provides
    fun provideTaskRepository(db: AppDatabase): TaskRepository =
        TaskRepository(db.taskDao())

    @Singleton
    @Provides
    fun provideExecutionRepository(db: AppDatabase): ExecutionRepository =
        ExecutionRepository(db.executionDao())

    @Singleton
    @Provides
    fun provideArtifactRepository(db: AppDatabase): ArtifactRepository =
        ArtifactRepository(db.artifactDao())
}
