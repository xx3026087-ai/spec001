package com.microworker.spec001.domain.inference

import org.junit.Test
import kotlin.test.assertEquals

class InferenceExceptionTest {

    @Test
    fun `invalidModel exception should have correct error code`() {
        val ex = InferenceException.invalidModel("test model error")
        assertEquals("INVALID_MODEL", ex.errorCode)
    }

    @Test
    fun `contextFailed exception should have correct error code`() {
        val ex = InferenceException.contextFailed("test context error")
        assertEquals("CONTEXT_FAILED", ex.errorCode)
    }

    @Test
    fun `generationFailed exception should have correct error code`() {
        val ex = InferenceException.generationFailed("test generation error")
        assertEquals("GENERATION_FAILED", ex.errorCode)
    }

    @Test
    fun `resourceExhausted exception should have correct error code`() {
        val ex = InferenceException.resourceExhausted("test resource error")
        assertEquals("RESOURCE_EXHAUSTED", ex.errorCode)
    }

    @Test
    fun `invalidHandle exception should have correct error code`() {
        val ex = InferenceException.invalidHandle("test handle error")
        assertEquals("INVALID_HANDLE", ex.errorCode)
    }
}
