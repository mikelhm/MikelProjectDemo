#构建库文件所需要CMake最小版本
cmake_minimum_required(VERSION 3.4.1)
# 设置生成的so动态库输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI})
#添加自己的C/C++源文件
add_library( # Sets the name of the library.
        native_test
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        src/main/cpp/native_test.cpp)
#添加依赖的NDK 库
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )

#添加外部依赖的so
add_library(
        aesutil
        SHARED
        IMPORTED)
set_target_properties(
        aesutil
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libaesutil.so
)
include_directories(src/main/cpp/include)

#将目标库与NDK 中的库链接
target_link_libraries( # Specifies the target library.
        native_test
        # Links the target library to the log library
        # included in the NDK.
        aesutil
        ${log-lib} )