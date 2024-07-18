cmake_minimum_required(VERSION 3.23)
project(cat_server)

set(CMAKE_CXX_STANDARD 20)

message("PROJECT_SOURCE_DIR ${PROJECT_SOURCE_DIR}")

add_subdirectory(src)
add_subdirectory(tests)
