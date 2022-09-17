#include <iostream>

#include <chrono>
#include <thread>

#include "device/FT891.hpp"
#include "lib/Usb.hpp"

int main()
{

  sp9pj::dev::FT891 ft891(0);


  try {
    if(ft891.on()) {
      std::cout << __FILE__ << ":" << __LINE__ << std::endl;
      std::this_thread::sleep_for(std::chrono::seconds(3));
      std::cout << __FILE__ << ":" << __LINE__ << std::endl;
      ft891.off();
      std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    }

  } catch (std::exception &ex) {
    std::cout << ex.what() << std::endl;
  } catch (...) {
    std::cout << "Jakiś wyjątek" << std::endl;
  }




  return 0;
}
