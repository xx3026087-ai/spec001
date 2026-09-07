package com.microworker.spec001.domain.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaskTest {

    @Test
    fun taskDefaultValues() {
        val task = Task(workflowId = "workflow-1")
        assertEquals("workflow-1", task.workflowId)
        assertEquals(TaskStatus.QUEUED, task.status)
        assertTrue(task.id.isNotEmpty())
    }

    @Test
    fun taskWithCustomValues() {
        val task = Task(
            id = "task-1",
            workflowId = "workflow-1",
            status = TaskStatus.RUNNING,
            priority = 5
        )
        assertEquals("task-1", task.id)
        assertEquals(TaskStatus.RUNNING, task.status)
        assertEquals(5, task.priority)
    }

    @Test
    fun taskStatusTransitions() {
        val queued = Task(workflowId = "w1", status = TaskStatus.QUEUED)
        assertEquals(TaskStatus.QUEUED, queued.status)

        val running = queued.copy(status = TaskStatus.RUNNING)
        assertEquals(TaskStatus.RUNNING, running.status)

        val completed = running.copy(
            status = TaskStatus.COMPLETED,
            completedAt = System.currentTimeMillis()
        )
        assertEquals(TaskStatus.COMPLETED, completed.status)
    }
}
