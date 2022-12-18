//
// Created by piotr@janczura.pl on 2022.09.17
//

#include "FT891.hpp"

#include <chrono>
#include <thread>
#include <iostream>

namespace sp9pj::dev {

FT891::FT891()
  : _usb()
{

}

FT891::~FT891()
{

}


void FT891::connect(PortUsbNumber portUsbNumber, PortUsbSpeed portUsbSpeed)
{
   _usb.connect(portUsbNumber, portUsbSpeed);
}

void FT891::disconnect()
{

}

bool FT891::on()
{
  if (_usb.isConnected()) {
    _usb.send("PS1;");
    std::this_thread::sleep_for(std::chrono::seconds(1));
    _usb.send("PS1;");
    // TODO Jakaś kontrola czy udało się podłączyć do radia.
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    return true;
  } else {
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    return false;
  }
}

void FT891::off()
{
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  if (_usb.isConnected()) {
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    _usb.send("PS0;");
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    _usb.disconnect();
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  }
}

}