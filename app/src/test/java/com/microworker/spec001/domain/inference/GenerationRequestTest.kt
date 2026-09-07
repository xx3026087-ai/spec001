package com.microworker.spec001.domain.inference

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GenerationRequestTest {

    @Test
    fun `valid request parameters should not throw`() {
        val req = GenerationRequest(
            prompt = "Hello",
            maxTokens = 100,
            temperature = 0.7f,
            topP = 0.95f
        )
        assertEquals("Hello", req.prompt)
        assertEquals(100, req.maxTokens)
        assertEquals(0.7f, req.temperature)
        assertEquals(0.95f, req.topP)
    }

    @Test
    fun `maxTokens must be positive`() {
        assertFailsWith<IllegalArgumentException> {
            GenerationRequest(prompt = "test", maxTokens = 0)
        }
    }

    @Test
    fun `temperature must be non-negative`() {
        assertFailsWith<IllegalArgumentException> {
            GenerationRequest(prompt = "test", temperature = -0.1f)
        }
    }

    @Test
    fun `topP must be in range [0, 1]`() {
        assertFailsWith<IllegalArgumentException> {
            GenerationRequest(prompt = "test", topP = 1.5f)
        }
        assertFailsWith<IllegalArgumentException> {
            GenerationRequest(prompt = "test", topP = -0.1f)
        }
    }
}
