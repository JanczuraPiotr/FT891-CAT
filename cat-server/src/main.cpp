#include <iostream>

#include "application/Application.hpp"
#include "application/Config.hpp"
#include "exception/Generic.hpp"

int main()
{

  try {

    sp9pj::app::Config config;
    config.init();

    sp9pj::app::Application application(config);

    application.start();

    application.run();

    application.stop();

  } catch (sp9pj::ex::Generic &ex) {
    std::cout << ex.description() << std::endl;
  }

  return 0;
}
