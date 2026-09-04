cmake_minimum_required(VERSION 3.22.1)
project(llama_bridge)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Include directories
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/llama)
include_directories(${CMAKE_CURRENT_BINARY_DIR})

# Find required packages
find_package(Threads REQUIRED)

# llama.cpp sources (minimal subset for Phase 1)
# Pinned to commit: TBD (replace with actual llama.cpp version tag/commit)
file(GLOB LLAMA_SOURCES
    "${CMAKE_SOURCE_DIR}/src/main/cpp/llama/ggml.c"
    "${CMAKE_SOURCE_DIR}/src/main/cpp/llama/ggml-quants.c"
    "${CMAKE_SOURCE_DIR}/src/main/cpp/llama/llama.cpp"
)

# JNI bridge
add_library(llama_bridge SHARED
    ${LLAMA_SOURCES}
    ${CMAKE_SOURCE_DIR}/src/main/cpp/LlamaBridge.cpp
)

target_link_libraries(llama_bridge
    PUBLIC Threads::Threads
)

# Compiler flags for performance and security
target_compile_options(llama_bridge PRIVATE
    -fPIC
    -O2
    -Wformat
    -Wformat-security
    -Wall
    -Wextra
)

if(ANDROID_ABI MATCHES "arm64-v8a")
    target_compile_options(llama_bridge PRIVATE -march=armv8-a -mtune=cortex-a76)
elseif(ANDROID_ABI MATCHES "armeabi-v7a")
    target_compile_options(llama_bridge PRIVATE -march=armv7-a -mtune=cortex-a9 -mfpu=neon)
elseif(ANDROID_ABI MATCHES "x86_64")
    target_compile_options(llama_bridge PRIVATE -march=x86-64 -mtune=generic)
elseif(ANDROID_ABI MATCHES "x86")
    target_compile_options(llama_bridge PRIVATE -march=i686 -mtune=generic)
endif()
