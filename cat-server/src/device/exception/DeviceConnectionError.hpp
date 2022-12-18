//
// Created by piotr@janczura.pl on 2022.12.18
//

#ifndef SRC_DEVICE_EXCEPTION_DEVICENOTEXIST
#define SRC_DEVICE_EXCEPTION_DEVICENOTEXIST

#include "def.hpp"
#include "exception/Generic.hpp"

namespace sp9pj::dev::ex {

class DeviceConnectionError : public sp9pj::ex::Generic {
public:

  explicit DeviceConnectionError(PortUsbNumber portUsbNumber);

  ~DeviceConnectionError() override = default;

  PortUsbNumber portUsbNumber();

private:
    PortUsbNumber _portNumber;
};

}
#endif
