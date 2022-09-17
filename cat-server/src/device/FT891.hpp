//
// Created by piotr@janczura.pl on 2022.09.17
//

#ifndef DEVICE_FT891
#define DEVICE_FT891

#include "lib/Usb.hpp"

namespace sp9pj::dev {


class FT891 {
public:
  explicit FT891(unsigned portNr);
  virtual ~FT891();

  bool on();

  void off();

private:
  sp9pj::Usb _usb;
};


}
#endif