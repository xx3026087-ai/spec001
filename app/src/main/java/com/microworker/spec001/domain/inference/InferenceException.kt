package com.microworker.spec001.domain.inference

/**
 * Thrown when inference operation fails.
 * Never contains model outputs or user prompts in the message.
 */
class InferenceException(
    message: String,
    cause: Throwable? = null,
    val errorCode: String = "INFERENCE_ERROR"
) : Exception(message, cause) {
    companion object {
        fun invalidModel(msg: String) = InferenceException(msg, errorCode = "INVALID_MODEL")
        fun contextFailed(msg: String) = InferenceException(msg, errorCode = "CONTEXT_FAILED")
        fun generationFailed(msg: String) = InferenceException(msg, errorCode = "GENERATION_FAILED")
        fun resourceExhausted(msg: String) = InferenceException(msg, errorCode = "RESOURCE_EXHAUSTED")
        fun invalidHandle(msg: String) = InferenceException(msg, errorCode = "INVALID_HANDLE")
    }
}
