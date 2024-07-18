//
// Created by piotr@janczura.pl on 2022.12.18
//

#ifndef SRC_APPLICATION_CONFIG
#define SRC_APPLICATION_CONFIG

#include "def.hpp"

namespace sp9pj::app {


class Config {
public:
    Config();

    virtual ~Config();

    void init();

    PortUsbNumber portUsbNumber() const;

    void portUsbNumber(PortUsbNumber portUsbNumber);

    PortUsbSpeed portUsbSpeed() const;

    void portUsbSpeed(PortUsbSpeed portUsbSpeed);

private:

    PortUsbNumber _portUsbNumber;
    PortUsbSpeed _portUsbSpeed;

};


}
#endif
