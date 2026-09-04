package com.microworker.spec001.domain.inference

/**
 * Immutable model metadata.
 */
data class ModelInfo(
    val id: String,
    val name: String,
    val version: String,
    val format: String = "GGUF",
    val parameterCount: Long = 0,
    val quantization: String = "unknown",
    val contextLength: Int = 2048,
    val minRamMb: Int = 512,
    val sha256: String? = null
)
