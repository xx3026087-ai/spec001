package com.microworker.spec001.native

import com.microworker.spec001.domain.inference.GenerationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Actual JNI implementation of LlamaBridge.
 * All native function calls are declared here.
 * Implementation is in LlamaBridge.cpp.
 */
class LlamaBridgeImpl : LlamaBridge {

    init {
        System.loadLibrary("llama_bridge")
    }

    external override fun loadModel(modelPath: String): Long
    external override fun createContext(modelHandle: Long, contextLength: Int): Long
    external override fun cancel(contextHandle: Long)
    external override fun freeContext(contextHandle: Long)
    external override fun freeModel(modelHandle: Long)

    override fun generate(contextHandle: Long, request: GenerationRequest): Flow<String> {
        // Stub: will be replaced by actual JNI call that streams tokens
        return flowOf()
    }
}
