//
// Created by piotr@janczura.pl on 2022.12.18
//

#include "Application.hpp"

#include <thread>

#include "exception/Generic.hpp"

namespace sp9pj::app {

Application::Application(Config &config)
        : _config(config)
        , _ft891()
        , _runMainLoop(false)
        , _sleepMainLoop(10){

}

Application::~Application() {

}

void Application::start() {

    _ft891.connect(_config.portUsbNumber(), _config.portUsbSpeed());

    try {
        _ft891.on();
        _runMainLoop = true;
    } catch (...){
        throw ex::Generic("Connection error.", __PRETTY_FUNCTION__);
    }
}

void Application::run() {
    using SC = std::chrono::steady_clock;

    SC steadyClock;
    do {
        SC::time_point begin = steadyClock.now();


        // loop


        SC::duration workTime = steadyClock.now() - begin;
        SC::duration sleep = _sleepMainLoop - workTime;

        if (sleep.count() > 0) {
            std::this_thread::sleep_for(sleep);
        }
    } while (_runMainLoop);
}

void Application::stop() {
    _ft891.off();
}


}