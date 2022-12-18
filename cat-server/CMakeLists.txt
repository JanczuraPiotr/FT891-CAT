cmake_minimum_required(VERSION 3.23)
project(cat_server)

set(CMAKE_CXX_STANDARD 20)

message("PROJECT_SOURCE_DIR ${PROJECT_SOURCE_DIR}")

include_directories("src")

file(GLOB_RECURSE SRC_FILES src/*.cpp )

add_executable(cat_server ${SRC_FILES})

