# Phase 1: Android + llama.cpp/JNI Infrastructure

## Acceptance Criteria

- [x] Android Gradle project compiles cleanly
- [x] Kotlin domain interfaces defined
- [x] LocalInferenceEngine implements concurrency + error handling
- [x] JNI bridge with opaque handle mapping (no C++ pointers leak to Kotlin)
- [x] LlamaBridge.cpp stubs all required native functions
- [x] Unit tests for domain classes, engine lifecycle, exception handling
- [ ] GGUF model loading (Phase 1.5)
- [ ] Token streaming (Phase 1.5)
- [ ] Cancellation propagation (Phase 1.5)
- [ ] Resource cleanup validation (Phase 1.5)

## Build Commands

```bash
# Clean & build
./gradlew clean assembleDebug

# Run tests
./gradlew test

# Continuous test loop
bash scripts/continuous-test.sh

# Lint
./gradlew lint
```

## Architecture

```
Kotlin Domain (InferenceEngine interface)
       ↓
LocalInferenceEngine (concurrency, error handling)
       ↓
LlamaBridge (JNI interface, opaque Long handles)
       ↓
LlamaBridgeImpl (external JNI declarations)
       ↓
JNI / LlamaBridge.cpp (opaque handle mapping)
       ↓
llama.cpp (pinned commit/tag)
```

## Pinned llama.cpp

**Status:** Pending actual commit/tag specification.

Once pinned:
1. Submodule or vendored source in `app/src/main/cpp/llama/`
2. CMakeLists.txt links against llama sources
3. Version tag documented here for reproducibility

## Next Phase

Phase 2: Room persistence (task/workflow/execution/artifact tables, DAOs).
