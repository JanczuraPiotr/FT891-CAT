project(server)

message("src / PROJECT_SOURCE_DIR       ${PROJECT_SOURCE_DIR}")



include_directories(.)

file(GLOB_RECURSE SRC_FILES *.cpp )

add_executable(cat_server ${SRC_FILES})

