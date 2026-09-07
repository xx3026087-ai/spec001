# Phase 2: Room Persistence Layer

## Acceptance Criteria

- [x] Room database configured with all entities
- [x] DAOs for Model, Workflow, Task, Execution, Artifact
- [x] Domain models with enums (TaskStatus, ModelStatus, etc.)
- [x] Repository pattern for data access
- [x] Hilt DI module for database and repositories
- [x] Unit tests for repositories
- [x] Integration tests for DAOs
- [x] Foreign key relationships defined
- [x] Indexes for common queries

## Database Schema

### models
- id (PK)
- name, version, format
- parameterCount, quantization
- fileUri, fileSizeBytes, contextLength, minRamMb
- sha256, status
- createdAt

### workflows
- id (PK)
- name, description, enabled
- triggerType, triggerConfig
- definitionJson
- createdAt, updatedAt

### tasks
- id (PK)
- workflowId (FK → workflows.id)
- inputUri, inputHash
- status, priority
- createdAt, startedAt, completedAt, error

### executions
- id (PK)
- taskId (FK → tasks.id)
- stepIndex, stepType, status
- inputJson, outputJson
- startedAt, completedAt, durationMs, error

### artifacts
- id (PK)
- taskId (FK → tasks.id)
- filename, mimeType, pathOrUri
- sha256, sizeBytes
- createdAt

## Build & Test

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Next Phase

Phase 3: SAF document processing pipeline (TXT, Markdown, PDF, CSV, JSON).
