package org.example.services;

import org.example.DTO.ErrorMessage;

import java.util.Optional;

public class ParameterValidation {

    public static Optional<ErrorMessage> CurrenciesValidation(
            String name, String code, String sign) {

        if (name == null || name.trim().isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Отсутствует нужное поле формы");
            return Optional.of(errorMessage);

        }else if (code == null || code.trim().isEmpty() || code.length() != 3) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Код валюты должен содержать 3 Символа");
            return Optional.of(errorMessage);
        }else if (sign == null || sign.trim().isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Не указано название валюты");
            return Optional.of(errorMessage);
        }
        return Optional.empty();
    }

    public static Optional<ErrorMessage> ExchangeRatesValidation(
            String baseCurrencyCode, String targetCurrencyCode, Double rate) {

        if ( baseCurrencyCode == null || baseCurrencyCode.trim().isEmpty() ||
                targetCurrencyCode == null || targetCurrencyCode.trim().isEmpty() ||
        rate == null) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Отсутствует нужное поле формы");
            return Optional.of(errorMessage);

        }else if (baseCurrencyCode.length() != 3 || targetCurrencyCode.length() != 3) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Код валюты должен содержать 3 Символа");
            return Optional.of(errorMessage);
        }else if (rate <= 0 ) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Курс перевода не может быть отрицательный");
            return Optional.of(errorMessage);
        }

        return Optional.empty();
    }

    public static Optional<ErrorMessage> ExchangeRateValidation(
            String baseCurrencyCode, String targetCurrencyCode) {

        if ( baseCurrencyCode == null || baseCurrencyCode.trim().isEmpty() ||
                targetCurrencyCode == null || targetCurrencyCode.trim().isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Отсутствует нужное поле формы");
            return Optional.of(errorMessage);

        }  else if (baseCurrencyCode.length() != 3 || targetCurrencyCode.length() != 3) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Код валюты должен содержать 3 Символа");
            return Optional.of(errorMessage);
        }

        return Optional.empty();
    }

}
