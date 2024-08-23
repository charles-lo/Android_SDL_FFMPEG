cmake_minimum_required(VERSION 3.6)

project(ffplay)

find_library(SDL2 SDL2)

# FFmpeg include headers
include_directories(${CMAKE_SOURCE_DIR}/SDL/include)

# FFmpeg include headers
include_directories(${CMAKE_SOURCE_DIR}/FFMPEG/include)

add_library(main SHARED
        ffplay/ffplay.c
        ffplay/cmdutils.c
        ffplay/opt_common.c
        ffplay/ffplay_renderer.c
)

#target_sources(main PRIVATE ffplay.c)



# library so files
add_library(
        avdevice-lib
        SHARED
        IMPORTED
)

set_target_properties(
        avdevice-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libavdevice.so
)

add_library(
        avcodec-lib
        SHARED
        IMPORTED
)

set_target_properties(
        avcodec-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libavcodec.so
)

# filter-library, not used yet
add_library(
        avfilter-lib
        SHARED
        IMPORTED
)
set_target_properties(
        avfilter-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libavfilter.so
)

# file format library, needed by most libraries
add_library(
        avformat-lib
        SHARED
        IMPORTED
)
set_target_properties(
        avformat-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libavformat.so
)

# utility library, depended by swresample-lib
add_library(
        avutil-lib
        SHARED
        IMPORTED
)
set_target_properties(
        avutil-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libavutil.so
)

# resample library, using for audio transform depended by avcodec-lib
add_library(
        swresample-lib
        SHARED
        IMPORTED
)
set_target_properties(
        swresample-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libswresample.so
)

# video format transform, depended by avfilter-lib
add_library(
        swscale-lib
        SHARED
        IMPORTED
)
set_target_properties(
        swscale-lib
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/FFMPEG/jniLibs/${ANDROID_ABI}/libswscale.so
)

target_link_libraries(main
        SDL2
        avdevice-lib
        avcodec-lib
        avfilter-lib
        avformat-lib
        avutil-lib
        swresample-lib
        swscale-lib
)


