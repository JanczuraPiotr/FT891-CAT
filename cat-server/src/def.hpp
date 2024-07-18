//
// Created by piotr@janczura.pl on 2022.12.18
//

#ifndef SRC_DEF
#define SRC_DEF

#include <string>
#include <limits>

namespace sp9pj {

typedef std::string Buffer;
typedef unsigned PortUsbNumber;
typedef unsigned PortUsbSpeed;

const unsigned PORT_USB_NUMBER_NOT_SET = std::numeric_limits<PortUsbNumber>::max();
const unsigned PORT_USB_SPEED_NOT_SET = std::numeric_limits<PortUsbSpeed>::max();


}
#endif //SRC_DEF
