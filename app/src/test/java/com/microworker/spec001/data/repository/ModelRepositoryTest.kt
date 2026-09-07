package com.microworker.spec001.data.repository

import com.microworker.spec001.domain.model.Model
import com.microworker.spec001.domain.model.ModelStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ModelRepositoryTest {

    private val mockDao = mockk<com.microworker.spec001.data.db.dao.ModelDao>()
    private val repository = ModelRepository(mockDao)

    @Test
    fun insertModel() = runBlocking {
        val model = Model(
            id = "model-1",
            name = "Test",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            createdAt = System.currentTimeMillis()
        )

        coEvery { mockDao.insert(any()) } returns Unit
        repository.insertModel(model)
        coVerify { mockDao.insert(any()) }
    }

    @Test
    fun getModelById() = runBlocking {
        val model = Model(
            id = "model-1",
            name = "Test",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            createdAt = System.currentTimeMillis()
        )

        coEvery { mockDao.getById("model-1") } returns model.toEntity()
        val retrieved = repository.getModelById("model-1")

        assertEquals(model.id, retrieved?.id)
        assertEquals(model.name, retrieved?.name)
    }

    @Test
    fun getModelByIdNotFound() = runBlocking {
        coEvery { mockDao.getById("nonexistent") } returns null
        val retrieved = repository.getModelById("nonexistent")
        assertNull(retrieved)
    }

    private fun Model.toEntity() = com.microworker.spec001.data.db.entity.ModelEntity(
        id = id,
        name = name,
        version = version,
        format = format,
        parameterCount = parameterCount,
        quantization = quantization,
        fileUri = fileUri,
        fileSizeBytes = fileSizeBytes,
        contextLength = contextLength,
        minRamMb = minRamMb,
        sha256 = sha256,
        status = status.name,
        createdAt = createdAt
    )
}
