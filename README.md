# spec001 — Offline Mobile Micro-Worker AI

A sovereign, local-first, offline-capable mobile micro-worker node designed for Android devices.

## Architecture

- **Native Inference:** Embedded llama.cpp with JNI bridge
- **Local Persistence:** Room/SQLite
- **Document Pipeline:** SAF + format-agnostic parser
- **Workflow Engine:** JSON-based with capability sandboxing
- **Security:** Path traversal prevention, capability allowlist, code injection protection
- **Resilience:** Crash recovery, atomic writes, model integrity verification

## Building

```bash
./gradlew assembleDebug
./gradlew test
```

## Testing

```bash
bash scripts/continuous-test.sh
```

## Phases

1. **Phase 1:** Native inference + JNI lifecycle
2. **Phase 2:** Room persistence
3. **Phase 3:** Document processing (SAF)
4. **Phase 4:** Workflow + security
5. **Phase 5:** End-to-end micro-worker
6. **Phase 6:** Stress, offline, recovery

See `docs/SPEC-001.md` for full specification.
