//
// Created by piotr@janczura.pl on 2022.12.18
//

#include "Application.hpp"

#include <thread>
#include "exception/Generic.hpp"

namespace sp9pj::app {

Application::Application(Config &config)
        : _config(config), _ft891() {

}

Application::~Application() {

}

void Application::start() {

    _ft891.connect(_config.portUsbNumber(), _config.portUsbSpeed());

    try {
        _ft891.on();
    } catch (...){
        throw ex::Generic("Connection error.", __PRETTY_FUNCTION__);
    }
}

void Application::run() {
    std::this_thread::sleep_for(std::chrono::seconds(3));
}

void Application::stop() {
    _ft891.off();
}


}