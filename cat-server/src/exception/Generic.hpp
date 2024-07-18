//
// Created by piotr@janczura.pl on 2022.12.18
//

#ifndef SRC_EXCEPTION_GENERIC
#define SRC_EXCEPTION_GENERIC

#include <exception>
#include <string>

namespace sp9pj::ex {


class Generic : public std::exception {
public:

    /// \brief Wyjątek na podstawie kodu błędu.
    /// \param description Opis błędu;
    /// \param where Nazwa funkcji lub pliku w którym zgłoszono błąd (__PREATY_FUNCTION__)
    /// \param mark Znacznik w kodzie w którym zgłoszono błąd.
    Generic(std::string description, std::string where, std::string mark = "");

    ~Generic() override = default;

    /// \brief Krótka wiadomość o błędzie
    virtual std::string description() const;

    /// \brief Zebrane informacje o zgłaszanym błędzie.
    virtual std::string log() const;

    /// \brief Funkcja w której wystąpił błąd.
    virtual std::string where() const;

    /// \return Dodatkowy znacznik wystąpienia błędu gdy potrzeba. Może być ""
    virtual std::string mark() const;

private:

    std::string _description;
    std::string _where;
    std::string _mark;

};


}
#endif
