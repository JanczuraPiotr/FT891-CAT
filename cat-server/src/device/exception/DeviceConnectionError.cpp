//
// Created by piotr@janczura.pl on 2022.12.18
//

#include "DeviceConnectionError.hpp"


namespace sp9pj::dev::ex {

DeviceConnectionError::DeviceConnectionError(PortUsbNumber portUsbNumber)
    : Generic("Błąd podczas łączenia z urządzeniem na porcie : " + std::to_string(portUsbNumber), "", "")
    , _portNumber(portUsbNumber) {

}

PortUsbNumber DeviceConnectionError::portUsbNumber() {
    return _portNumber;
}


}