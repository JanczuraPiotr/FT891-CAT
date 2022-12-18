//
// Created by piotr@janczura.pl on 2022.09.17
//

#ifndef DEVICE_FT891
#define DEVICE_FT891

#include "device/basic/Usb.hpp"

namespace sp9pj::dev {


class FT891 {
public:
  explicit FT891();
  virtual ~FT891();

  void connect(PortUsbNumber portUsbNumber, PortUsbSpeed portUsbSpeed);

  void disconnect();

  bool on();

  void off();

private:
  dev::Usb _usb;
};


}
#endif
