package com.microworker.spec001.domain.inference

/**
 * Immutable generation request.
 */
data class GenerationRequest(
    val prompt: String,
    val maxTokens: Int = 512,
    val temperature: Float = 0.7f,
    val topP: Float = 0.95f
) {
    init {
        require(maxTokens > 0) { "maxTokens must be > 0" }
        require(temperature >= 0f) { "temperature must be >= 0" }
        require(topP in 0f..1f) { "topP must be in [0, 1]" }
    }
}
