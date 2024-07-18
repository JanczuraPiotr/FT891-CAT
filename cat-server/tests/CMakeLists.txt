project(gtest)

#set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/exe)

add_executable(gtest main.cpp
        BaseTest.cpp
        )


find_package(GTest REQUIRED)
include_directories(${GTEST_INCLUDE_DIRS})

#include_directories(${GTEST_INCLUDE_DIRS})
#include_directories(..)

#set(Boost_DEBUG OFF)
#find_package(Boost)
#if (NOT Boost_FOUND)
#    message(FATAL_ERROR "Could not find boost!")
#endif ()


target_link_libraries(gtest
        ${GTEST_LIBRARIES}
        pthread
        boost_date_time
        )
