//
// Created by piotr@janczura.pl on 2022.12.18
//

#include "Config.hpp"


namespace sp9pj::app {


Config::Config() {

}

Config::~Config() {

}

void Config::init() {
    // Docelowo odczyt z pliku konfiguracyjnego.
    portUsbNumber(10);
    portUsbSpeed(38400);
}


PortUsbNumber Config::portUsbNumber() const {
    return _portUsbNumber;
}

void Config::portUsbNumber(PortUsbNumber portUsbNumber) {
    _portUsbNumber = portUsbNumber;
}

PortUsbNumber Config::portUsbSpeed() const {
    return _portUsbSpeed;
}

void Config::portUsbSpeed(PortUsbSpeed portUsbSpeed) {
    _portUsbSpeed = portUsbSpeed;
}

}