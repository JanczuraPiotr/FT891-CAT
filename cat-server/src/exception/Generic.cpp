//
// Created by piotr@janczura.pl on 2022.12.18
//

#include "Generic.hpp"

namespace sp9pj::ex {


Generic::Generic(std::string description, std::string where, std::string mark)
        : _description(std::move(description))
          , _where(std::move(where))
          , _mark(std::move(mark)) {

}

std::string Generic::description() const {
    return _description;
}

std::string Generic::log() const {
    std::string log{description()};
    if (!where().empty()) {
        log += " | " + where();
        if (!mark().empty()) {
            log += " --> " + mark();
        }
    }
    return log;
}

std::string Generic::where() const {
    if (!_mark.empty()) {
        return _where;
    } else {
        return "";
    }
}

std::string Generic::mark() const {
    if (!_mark.empty()) {
        return _mark;
    } else {
        return "";
    }
}


}