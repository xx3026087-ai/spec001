package com.microworker.spec001.data.inference

import com.microworker.spec001.domain.inference.GenerationRequest
import com.microworker.spec001.domain.inference.InferenceException
import com.microworker.spec001.native.LlamaBridge
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalInferenceEngineTest {

    private val mockBridge = mockk<LlamaBridge>()
    private val engine = LocalInferenceEngine(mockBridge)

    @Test
    fun `loadModel should return valid handle`() = runBlocking {
        coEvery { mockBridge.loadModel(any()) } returns 1L

        val handle = engine.loadModel("/path/to/model.gguf")
        assertEquals(1L, handle)
        coVerify { mockBridge.loadModel("/path/to/model.gguf") }
    }

    @Test
    fun `loadModel should throw on invalid handle`() = runBlocking {
        coEvery { mockBridge.loadModel(any()) } returns -1L

        assertFailsWith<InferenceException> {
            engine.loadModel("/path/to/model.gguf")
        }
    }

    @Test
    fun `loadModel should throw on JNI exception`() = runBlocking {
        coEvery { mockBridge.loadModel(any()) } throws Exception("JNI error")

        assertFailsWith<InferenceException> {
            engine.loadModel("/path/to/model.gguf")
        }
    }

    @Test
    fun `createContext should return valid handle`() = runBlocking {
        coEvery { mockBridge.createContext(any(), any()) } returns 2L

        val handle = engine.createContext(1L, 2048)
        assertEquals(2L, handle)
        coVerify { mockBridge.createContext(1L, 2048) }
    }

    @Test
    fun `createContext should reject invalid model handle`() = runBlocking {
        assertFailsWith<InferenceException> {
            engine.createContext(-1L, 2048)
        }
    }

    @Test
    fun `createContext should reject invalid context length`() = runBlocking {
        assertFailsWith<InferenceException> {
            engine.createContext(1L, 0)
        }
    }

    @Test
    fun `generate should respect mutex locking`() = runBlocking {
        val request = GenerationRequest(prompt = "test")
        coEvery { mockBridge.generate(any(), any()) } returns flowOf("token1", "token2")

        val flow = engine.generate(2L, request)
        coVerify { mockBridge.generate(2L, request) }
    }

    @Test
    fun `freeContext should handle valid context`() = runBlocking {
        coEvery { mockBridge.freeContext(any()) } returns Unit

        engine.freeContext(2L)
        coVerify { mockBridge.freeContext(2L) }
    }

    @Test
    fun `freeModel should handle valid model`() = runBlocking {
        coEvery { mockBridge.freeModel(any()) } returns Unit

        engine.freeModel(1L)
        coVerify { mockBridge.freeModel(1L) }
    }

    @Test
    fun `cancel should be idempotent`() = runBlocking {
        coEvery { mockBridge.cancel(any()) } returns Unit

        engine.cancel(2L)
        engine.cancel(2L)
        coVerify(exactly = 2) { mockBridge.cancel(2L) }
    }
}
