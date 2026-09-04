package com.microworker.spec001.di

import com.microworker.spec001.data.inference.LocalInferenceEngine
import com.microworker.spec001.domain.inference.InferenceEngine
import com.microworker.spec001.native.LlamaBridge
import com.microworker.spec001.native.LlamaBridgeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InferenceModule {

    @Singleton
    @Provides
    fun provideLlamaBridge(): LlamaBridge = LlamaBridgeImpl()

    @Singleton
    @Provides
    fun provideInferenceEngine(bridge: LlamaBridge): InferenceEngine =
        LocalInferenceEngine(bridge)
}
