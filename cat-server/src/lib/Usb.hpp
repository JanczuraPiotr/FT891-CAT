//
// Created by piotr@janczura.pl on 2022.09.17
//

#ifndef SRC_LIB_Usb
#define SRC_LIB_Usb

#include <boost/asio.hpp>
#include <boost/asio/serial_port.hpp>
#include <boost/array.hpp>
#include <boost/asio/basic_streambuf.hpp>

#include "def.hpp"

namespace sp9pj {


class Usb {
public:

  explicit Usb(unsigned nrPortu);
  virtual ~Usb();

  bool connect();
  void disconnect();
  bool isConnected();

  sp9pj::Buffer query(const sp9pj::Buffer &buffer);
  size_t send(const sp9pj::Buffer &query);
  size_t receive();


private:

  void checkDeadline();

private:
  unsigned _nrPortu;
  unsigned _speed;
  bool _connected;
  boost::asio::io_service _io_service;
  boost::asio::serial_port _port;
  boost::asio::deadline_timer _deadlineTimer;
  boost::posix_time::time_duration _timeout;
  boost::asio::streambuf _bufIn;

};


}
#endif
