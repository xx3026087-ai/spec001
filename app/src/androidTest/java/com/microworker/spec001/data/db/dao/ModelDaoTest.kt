package com.microworker.spec001.data.db.dao

import androidx.room.Room
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.microworker.spec001.data.db.AppDatabase
import com.microworker.spec001.data.db.entity.ModelEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ModelDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var modelDao: ModelDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        modelDao = db.modelDao()
    }

    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun insertAndRetrieveModel() = runBlocking {
        val model = ModelEntity(
            id = "test-model-1",
            name = "Test Model",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            status = "READY",
            createdAt = System.currentTimeMillis()
        )

        modelDao.insert(model)
        val retrieved = modelDao.getById("test-model-1")

        assertNotNull(retrieved)
        assertEquals("Test Model", retrieved?.name)
        assertEquals("1.0", retrieved?.version)
    }

    @Test
    fun updateModel() = runBlocking {
        val model = ModelEntity(
            id = "test-model-2",
            name = "Original Name",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            status = "READY",
            createdAt = System.currentTimeMillis()
        )

        modelDao.insert(model)
        modelDao.update(model.copy(name = "Updated Name"))
        val updated = modelDao.getById("test-model-2")

        assertEquals("Updated Name", updated?.name)
    }

    @Test
    fun deleteModel() = runBlocking {
        val model = ModelEntity(
            id = "test-model-3",
            name = "To Delete",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            status = "READY",
            createdAt = System.currentTimeMillis()
        )

        modelDao.insert(model)
        modelDao.delete(model)
        val retrieved = modelDao.getById("test-model-3")

        assertNull(retrieved)
    }

    @Test
    fun getBySha256() = runBlocking {
        val model = ModelEntity(
            id = "test-model-4",
            name = "SHA Test",
            version = "1.0",
            fileUri = "file:///test.gguf",
            fileSizeBytes = 1024,
            status = "READY",
            sha256 = "abc123def456",
            createdAt = System.currentTimeMillis()
        )

        modelDao.insert(model)
        val retrieved = modelDao.getBySha256("abc123def456")

        assertNotNull(retrieved)
        assertEquals("test-model-4", retrieved?.id)
    }
}
