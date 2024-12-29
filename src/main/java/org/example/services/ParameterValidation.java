package org.example.services;

import org.example.dto.ErrorMessage;

import java.util.Optional;

public class ParameterValidation {

    public Optional<ErrorMessage> CurrenciesValidation(
            String name, String code, String sign) {

        if (name == null || name.trim().isEmpty()) {
            return Optional.of(new ErrorMessage("Отсутствует нужное поле формы"));

        }
        else if (code == null || code.trim().isEmpty() || code.length() != 3) {
            return Optional.of(new ErrorMessage("Код валюты должен содержать 3 Символа"));
        }
        else if (sign == null || sign.trim().isEmpty()) {
            return Optional.of(new ErrorMessage("Не указано название валюты"));
        }
        return Optional.empty();
    }

    public Optional<ErrorMessage> ExchangeRatesValidation(
            String baseCurrencyCode, String targetCurrencyCode, Double rate) {

        if ( baseCurrencyCode == null || baseCurrencyCode.trim().isEmpty() ||
                targetCurrencyCode == null || targetCurrencyCode.trim().isEmpty() ||
        rate == null) {
            return Optional.of(new ErrorMessage("Отсутствует нужное поле формы"));
        }
        else if (baseCurrencyCode.length() != 3 || targetCurrencyCode.length() != 3) {
            return Optional.of(new ErrorMessage("Код валюты должен содержать 3 Символа"));
        }
        else if (rate <= 0 ) {
            return Optional.of(new ErrorMessage("Курс перевода не может быть отрицательный"));
        }

        return Optional.empty();
    }

    public Optional<ErrorMessage> ExchangeRateValidation(
            String baseCurrencyCode, String targetCurrencyCode) {

        if ( baseCurrencyCode == null || baseCurrencyCode.trim().isEmpty() ||
                targetCurrencyCode == null || targetCurrencyCode.trim().isEmpty()) {
            return Optional.of(new ErrorMessage("Отсутствует нужное поле формы"));
        }
        else if (baseCurrencyCode.length() != 3 || targetCurrencyCode.length() != 3) {
            return Optional.of(new ErrorMessage("Код валюты должен содержать 3 Символа"));
        }
        return Optional.empty();
    }

}
