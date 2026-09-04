#include <jni.h>
#include <string>
#include <vector>
#include <map>
#include <mutex>
#include <android/log.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "LlamaBridge", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "LlamaBridge", __VA_ARGS__)

// Forward declarations (llama.cpp API)
struct llama_model;
struct llama_context;

// Opaque handle mapping: Long -> C++ pointer
// This ensures C++ objects never leak to Kotlin layer
static std::map<int64_t, void*> g_model_handles;
static std::map<int64_t, void*> g_context_handles;
static std::mutex g_handles_mutex;
static int64_t g_next_handle_id = 1;

static int64_t allocate_model_handle(llama_model* ptr) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    int64_t handle = g_next_handle_id++;
    g_model_handles[handle] = (void*)ptr;
    LOGI("Allocated model handle: %lld", handle);
    return handle;
}

static llama_model* get_model_from_handle(int64_t handle) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    auto it = g_model_handles.find(handle);
    if (it == g_model_handles.end()) return nullptr;
    return (llama_model*)it->second;
}

static void free_model_handle(int64_t handle) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    g_model_handles.erase(handle);
    LOGI("Freed model handle: %lld", handle);
}

static int64_t allocate_context_handle(llama_context* ptr) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    int64_t handle = g_next_handle_id++;
    g_context_handles[handle] = (void*)ptr;
    LOGI("Allocated context handle: %lld", handle);
    return handle;
}

static llama_context* get_context_from_handle(int64_t handle) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    auto it = g_context_handles.find(handle);
    if (it == g_context_handles.end()) return nullptr;
    return (llama_context*)it->second;
}

static void free_context_handle(int64_t handle) {
    std::lock_guard<std::mutex> lock(g_handles_mutex);
    g_context_handles.erase(handle);
    LOGI("Freed context handle: %lld", handle);
}

// JNI implementation
extern "C" {

JNIEXPORT jlong JNICALL
Java_com_microworker_spec001_native_LlamaBridgeImpl_loadModel(
    JNIEnv* env,
    jobject /* this */,
    jstring model_path_jstr) {
    LOGI("JNI loadModel called");
    
    const char* model_path = env->GetStringUTFChars(model_path_jstr, nullptr);
    if (!model_path) {
        LOGE("Failed to get model path string");
        return -1;
    }

    LOGI("Loading model from: %s", model_path);
    // TODO: Call llama_load_model_from_file()
    // For Phase 1 stub: return positive handle
    int64_t handle = allocate_model_handle((llama_model*)0xDEADBEEF);
    
    env->ReleaseStringUTFChars(model_path_jstr, model_path);
    return handle;
}

JNIEXPORT jlong JNICALL
Java_com_microworker_spec001_native_LlamaBridgeImpl_createContext(
    JNIEnv* env,
    jobject /* this */,
    jlong model_handle,
    jint context_length) {
    LOGI("JNI createContext called: modelHandle=%lld, contextLength=%d", model_handle, context_length);
    
    llama_model* model = get_model_from_handle(model_handle);
    if (!model) {
        LOGE("Invalid model handle: %lld", model_handle);
        return -1;
    }

    // TODO: Call llama_new_context_with_model()
    // For Phase 1 stub: return positive handle
    int64_t handle = allocate_context_handle((llama_context*)0xCAFEBABE);
    return handle;
}

JNIEXPORT void JNICALL
Java_com_microworker_spec001_native_LlamaBridgeImpl_cancel(
    JNIEnv* env,
    jobject /* this */,
    jlong context_handle) {
    LOGI("JNI cancel called: contextHandle=%lld", context_handle);
    // TODO: Set cancellation flag
}

JNIEXPORT void JNICALL
Java_com_microworker_spec001_native_LlamaBridgeImpl_freeContext(
    JNIEnv* env,
    jobject /* this */,
    jlong context_handle) {
    LOGI("JNI freeContext called: contextHandle=%lld", context_handle);
    
    llama_context* ctx = get_context_from_handle(context_handle);
    if (!ctx) {
        LOGE("Invalid context handle: %lld", context_handle);
        return;
    }

    // TODO: Call llama_free(ctx)
    free_context_handle(context_handle);
}

JNIEXPORT void JNICALL
Java_com_microworker_spec001_native_LlamaBridgeImpl_freeModel(
    JNIEnv* env,
    jobject /* this */,
    jlong model_handle) {
    LOGI("JNI freeModel called: modelHandle=%lld", model_handle);
    
    llama_model* model = get_model_from_handle(model_handle);
    if (!model) {
        LOGE("Invalid model handle: %lld", model_handle);
        return;
    }

    // TODO: Call llama_free_model(model)
    free_model_handle(model_handle);
}

} // extern "C"
