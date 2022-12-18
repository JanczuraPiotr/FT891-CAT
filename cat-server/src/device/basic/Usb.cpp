//
// Created by piotr@janczura.pl on 2022.08.22
//

#include "Usb.hpp"

#include <iostream>
#include <iomanip>
#include <algorithm>
#include <limits>


#include <boost/lambda/bind.hpp>
#include <boost/lambda/lambda.hpp>

#include <boost/date_time/posix_time/posix_time.hpp>
#include <boost/date_time.hpp>

#include <unistd.h>

#include "device/exception/DeviceConnectionError.hpp"


namespace sp9pj::dev {

Usb::Usb()
        : _portNumber(PORT_USB_NUMBER_NOT_SET)
        , _portSpeed(PORT_USB_SPEED_NOT_SET)
        , _io_service()
        , _connected(false)
        , _port(_io_service)
        , _deadlineTimer(_io_service)
        , _timeout(boost::posix_time::seconds(3)) {
    _deadlineTimer.expires_at(boost::posix_time::pos_infin);
    checkDeadline();

    _deadlineTimer.expires_from_now(_timeout);

}

Usb::~Usb() {
    disconnect();
}

void Usb::connect(PortUsbNumber portUsbNumber, PortUsbSpeed portUsbSpeed) {
    if (isConnected()) {
        if (portUsbNumber == _portNumber && portUsbSpeed == _portSpeed) {
            return;
        } else {
            disconnect();
        }
    }

    _portNumber = portUsbNumber;
    _portSpeed = portUsbSpeed;

    try {
        _port.open("/dev/ttyUSB" + std::to_string(_portNumber));
        _port.set_option(boost::asio::serial_port_base::baud_rate(_portSpeed));
        // TODO Sprawdzić jaki wyjątek rzuca set_option, przechwycić i rzutować.
//    }catch (std::exception &ex) {
//        std::cout << ex.what() << std::endl;
    } catch (...) {
        throw dev::ex::DeviceConnectionError(portUsbNumber);
    }

}

void Usb::disconnect() {
    _port.close();
}

bool Usb::isConnected() const {
    return _connected;
}

sp9pj::Buffer Usb::query(const sp9pj::Buffer &query) {
    std::size_t sended = send(query);
    if (sended != query.size()) {
        // @task wyjątek
        std::cout << __PRETTY_FUNCTION__ << " Wysłano mniejszą ilość danych niż zawierał bufor : " << sended
                  << std::endl;
        std::cout << __PRETTY_FUNCTION__ << " POWINIEN BYĆ THROW !!!!!! " << std::endl;
    }

    if (receive() > 0) {
        const char *buff = boost::asio::buffer_cast<const char *>(_bufIn.data());
        sp9pj::Buffer response;
        response.resize(_bufIn.data().size());
        for (std::size_t i = 0; i < _bufIn.data().size(); ++i) {
            response[i] = buff[i];
        }
        _bufIn.consume(_bufIn.size());
        return std::move(response);
    } else {
        return std::move(sp9pj::Buffer());
    }
}

size_t Usb::send(const sp9pj::Buffer &query) {
    boost::asio::write(_port, boost::asio::buffer(query.data(), query.size()));

    return query.size();
}

size_t Usb::receive() {
    _deadlineTimer.expires_from_now(_timeout);
    boost::system::error_code ec = boost::asio::error::would_block;
    boost::asio::async_read_until(_port, _bufIn, static_cast<char>(';'), boost::lambda::var(ec) = boost::lambda::_1);
    do {
        _io_service.run_one();
    } while (ec == boost::asio::error::would_block);
    if (ec) {
        throw boost::system::system_error(ec);
    }
    return _bufIn.data().size();
}

void Usb::checkDeadline() {
    if (_deadlineTimer.expires_at() <= boost::asio::deadline_timer::traits_type::now()) {
        boost::system::error_code ignored_ec;
        _port.close(ignored_ec);
        _deadlineTimer.expires_at(boost::posix_time::pos_infin);
    }
    _deadlineTimer.async_wait(boost::lambda::bind(&Usb::checkDeadline, this));
}

}