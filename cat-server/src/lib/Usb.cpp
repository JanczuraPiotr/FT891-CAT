//
// Created by piotr@janczura.pl on 2022.08.22
//

#include "Usb.hpp"

#include <iostream>
#include <iomanip>
#include <algorithm>

#include <boost/lambda/bind.hpp>
#include <boost/lambda/lambda.hpp>

#include <boost/date_time/posix_time/posix_time.hpp>
#include <boost/date_time.hpp>

#include <unistd.h>


namespace sp9pj {

Usb::Usb(unsigned nrPortu)
    : _nrPortu(nrPortu)
    , _speed(38400)
    , _io_service()
    , _connected(false)
    , _port(_io_service)
    , _deadlineTimer(_io_service)
    , _timeout(boost::posix_time::seconds(3))
{
  _deadlineTimer.expires_at(boost::posix_time::pos_infin);
  checkDeadline();

  _deadlineTimer.expires_from_now(_timeout);

}

Usb::~Usb()
{
  disconnect();
}

bool Usb::connect()
{
  if (isConnected()) {
    return true;
  }
  _port.open("/dev/ttyUSB" + std::to_string(_nrPortu));
  if ((_connected = _port.is_open())) {
    _port.set_option(boost::asio::serial_port_base::baud_rate(_speed));

    return true;
  } else {
    return false;
  }
}

void Usb::disconnect()
{
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  _port.close();
}

bool Usb::isConnected()
{
  return _connected;
}

sp9pj::Buffer Usb::query(const sp9pj::Buffer &query)
{
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  std::size_t sended = send(query);
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  if (sended != query.size()) {
    // @task wyjątek
    std::cout << __PRETTY_FUNCTION__ << " Wysłano mniejszą ilość danych niż zawierał bufor : " << sended << std::endl;
    std::cout << __PRETTY_FUNCTION__ << " POWINIEN BYĆ THROW !!!!!! " << std::endl;
  }

  if(receive() > 0) {
    const char *buff = boost::asio::buffer_cast<const char *>(_bufIn.data());
    sp9pj::Buffer response;
    response.resize(_bufIn.data().size());
    for(std::size_t i = 0; i < _bufIn.data().size(); ++i) {
      response[i] = buff[i];
    }
    _bufIn.consume(_bufIn.size());
    return std::move(response);
  } else {
    return std::move(sp9pj::Buffer());
  }
}

size_t Usb::send(const sp9pj::Buffer &query)
{
  boost::asio::write(_port, boost::asio::buffer(query.data(), query.size()));

  return query.size();
}

size_t Usb::receive()
{
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  _deadlineTimer.expires_from_now(_timeout);
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  boost::system::error_code ec = boost::asio::error::would_block;
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  boost::asio::async_read_until(_port
                                , _bufIn
                                , static_cast<char>(';')
                                , boost::lambda::var(ec) = boost::lambda::_1);
  do {
    _io_service.run_one();
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  } while (ec == boost::asio::error::would_block);
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  if (ec) {
    throw boost::system::system_error(ec);
  }
  std::cout << __FILE__ << ":" << __LINE__ << std::endl;
  return _bufIn.data().size();
}

void Usb::checkDeadline()
{
  if (_deadlineTimer.expires_at() <= boost::asio::deadline_timer::traits_type::now()) {
    boost::system::error_code ignored_ec;
    _port.close(ignored_ec);
    _deadlineTimer.expires_at(boost::posix_time::pos_infin);
  }
  _deadlineTimer.async_wait(boost::lambda::bind(&Usb::checkDeadline, this));
}

}