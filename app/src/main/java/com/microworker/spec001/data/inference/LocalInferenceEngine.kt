package com.microworker.spec001.data.inference

import com.microworker.spec001.domain.inference.GenerationRequest
import com.microworker.spec001.domain.inference.InferenceEngine
import com.microworker.spec001.domain.inference.InferenceException
import com.microworker.spec001.native.LlamaBridge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

/**
 * Production implementation of InferenceEngine.
 * Wraps LlamaBridge (JNI layer) with lifecycle guarantees and concurrency control.
 * Only one generation may execute at a time on a shared context.
 */
class LocalInferenceEngine(
    private val llamaBridge: LlamaBridge
) : InferenceEngine {

    private val generationMutex = Mutex()

    override suspend fun loadModel(modelPath: String): Long {
        Timber.d("Loading model from: $modelPath")
        return try {
            val handle = llamaBridge.loadModel(modelPath)
            if (handle <= 0) {
                throw InferenceException.invalidModel("JNI returned invalid model handle: $handle")
            }
            Timber.d("Model loaded successfully, handle=$handle")
            handle
        } catch (e: Exception) {
            Timber.e(e, "Failed to load model")
            throw if (e is InferenceException) e else InferenceException.invalidModel(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun createContext(modelHandle: Long, contextLength: Int): Long {
        Timber.d("Creating context: modelHandle=$modelHandle, contextLength=$contextLength")
        return try {
            if (modelHandle <= 0) {
                throw InferenceException.invalidHandle("Invalid model handle: $modelHandle")
            }
            if (contextLength <= 0) {
                throw InferenceException.contextFailed("contextLength must be > 0")
            }
            val handle = llamaBridge.createContext(modelHandle, contextLength)
            if (handle <= 0) {
                throw InferenceException.contextFailed("JNI returned invalid context handle: $handle")
            }
            Timber.d("Context created successfully, handle=$handle")
            handle
        } catch (e: Exception) {
            Timber.e(e, "Failed to create context")
            throw if (e is InferenceException) e else InferenceException.contextFailed(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun generate(
        contextHandle: Long,
        request: GenerationRequest
    ): Flow<String> {
        Timber.d("Starting generation: contextHandle=$contextHandle")
        if (contextHandle <= 0) {
            throw InferenceException.invalidHandle("Invalid context handle: $contextHandle")
        }
        return generationMutex.withLock {
            try {
                llamaBridge.generate(contextHandle, request)
            } catch (e: Exception) {
                Timber.e(e, "Generation failed")
                throw if (e is InferenceException) e else InferenceException.generationFailed(e.message ?: "Unknown error", e)
            }
        }
    }

    override suspend fun cancel(contextHandle: Long) {
        Timber.d("Cancelling generation: contextHandle=$contextHandle")
        if (contextHandle > 0) {
            try {
                llamaBridge.cancel(contextHandle)
            } catch (e: Exception) {
                Timber.e(e, "Failed to cancel generation")
            }
        }
    }

    override suspend fun freeContext(contextHandle: Long) {
        Timber.d("Freeing context: contextHandle=$contextHandle")
        if (contextHandle > 0) {
            try {
                llamaBridge.freeContext(contextHandle)
            } catch (e: Exception) {
                Timber.e(e, "Failed to free context")
            }
        }
    }

    override suspend fun freeModel(modelHandle: Long) {
        Timber.d("Freeing model: modelHandle=$modelHandle")
        if (modelHandle > 0) {
            try {
                llamaBridge.freeModel(modelHandle)
            } catch (e: Exception) {
                Timber.e(e, "Failed to free model")
            }
        }
    }
}
