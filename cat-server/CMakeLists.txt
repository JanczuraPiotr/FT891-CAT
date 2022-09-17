cmake_minimum_required(VERSION 3.23)
project(cat_server)

set(CMAKE_CXX_STANDARD 20)

message("PROJECT_SOURCE_DIR ${PROJECT_SOURCE_DIR}")


include_directories("src")

add_executable(cat_server src/main.cpp src/lib/Usb.cpp src/device/FT891.cpp)

