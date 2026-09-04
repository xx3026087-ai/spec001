package com.microworker.spec001.domain.inference

import kotlinx.coroutines.flow.Flow

/**
 * Domain-level inference engine interface.
 * Strictly abstracts native implementation details.
 * All handles are opaque Long—no C++ pointers leak into Kotlin.
 */
interface InferenceEngine {
    /**
     * Load a GGUF model from disk.
     * @param modelPath absolute path to the .gguf file
     * @return opaque model handle (Long)
     * @throws InferenceException on invalid model, corrupted file, or resource exhaustion
     */
    suspend fun loadModel(modelPath: String): Long

    /**
     * Create an inference context from a loaded model.
     * @param modelHandle opaque handle returned by loadModel()
     * @param contextLength max tokens in context window
     * @return opaque context handle (Long)
     * @throws InferenceException on resource exhaustion or invalid model handle
     */
    suspend fun createContext(modelHandle: Long, contextLength: Int): Long

    /**
     * Generate tokens from a prompt.
     * Respects coroutine cancellation.
     * @param contextHandle opaque context handle
     * @param request generation parameters and prompt
     * @return Flow of token strings (may be empty on cancellation)
     * @throws InferenceException on invalid context, prompt too large, or native error
     */
    suspend fun generate(
        contextHandle: Long,
        request: GenerationRequest
    ): Flow<String>

    /**
     * Cancel an in-flight generation.
     * Thread-safe. Idempotent.
     * @param contextHandle opaque context handle
     */
    suspend fun cancel(contextHandle: Long)

    /**
     * Free an inference context.
     * Must not be called twice with the same handle.
     * @param contextHandle opaque context handle
     */
    suspend fun freeContext(contextHandle: Long)

    /**
     * Free a loaded model.
     * Must not be called twice with the same handle.
     * @param modelHandle opaque model handle
     */
    suspend fun freeModel(modelHandle: Long)
}
