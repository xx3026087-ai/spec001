package com.microworker.spec001.native

import com.microworker.spec001.domain.inference.GenerationRequest
import kotlinx.coroutines.flow.Flow

/**
 * JNI bridge to pinned llama.cpp.
 * This is the strict boundary between Kotlin and native code.
 * All handles are opaque Long values—no C++ pointers leak to Kotlin.
 * All native methods must be implemented in LlamaBridge.cpp.
 */
interface LlamaBridge {
    /**
     * Load a GGUF model from disk via JNI.
     * @param modelPath absolute path to .gguf file
     * @return opaque model handle (positive Long)
     * @throws Exception on file not found, invalid format, or OOM
     */
    fun loadModel(modelPath: String): Long

    /**
     * Create an inference context from a model via JNI.
     * @param modelHandle opaque model handle from loadModel()
     * @param contextLength max tokens
     * @return opaque context handle (positive Long)
     * @throws Exception on resource exhaustion or invalid model handle
     */
    fun createContext(modelHandle: Long, contextLength: Int): Long

    /**
     * Generate tokens from a prompt via JNI.
     * Respects coroutine cancellation.
     * @param contextHandle opaque context handle
     * @param request generation parameters and prompt
     * @return Flow of token strings
     * @throws Exception on native error
     */
    fun generate(contextHandle: Long, request: GenerationRequest): Flow<String>

    /**
     * Request cancellation of in-flight generation.
     * @param contextHandle opaque context handle
     */
    fun cancel(contextHandle: Long)

    /**
     * Free an inference context.
     * @param contextHandle opaque context handle
     */
    fun freeContext(contextHandle: Long)

    /**
     * Free a loaded model.
     * @param modelHandle opaque model handle
     */
    fun freeModel(modelHandle: Long)
}
