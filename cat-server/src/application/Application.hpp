//
// Created by piotr@janczura.pl on 2022.12.18
//

#ifndef SRC_APPLICATION_APPLICATION
#define SRC_APPLICATION_APPLICATION

#include "application/Config.hpp"

#include "device/FT891.hpp"

namespace sp9pj::app {


class Application {
public:

    explicit Application(Config &_config);

    virtual ~Application();

    void start();

    void run();

    void stop();

private:

    Config &_config;

    dev::FT891 _ft891;

};


}
#endif
