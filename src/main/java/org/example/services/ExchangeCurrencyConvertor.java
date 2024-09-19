package org.example.services;

import org.example.DAO.ExchangeRatesDAO;
import org.example.DAO.ExchangeRatesDAOImpl;
import org.example.DTO.ExchangeCurrency;
import org.example.models.ExchangeRates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeCurrencyConvertor {

    public static Optional<ExchangeCurrency> convert(
            String baseCurrency, String targetCurrency, Double Amount) {

        ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAOImpl();
        Optional<ExchangeRates> exchangeRates  = exchangeRatesDAO.getExchangeRatesByCode(
                baseCurrency, targetCurrency);
        Optional<ExchangeRates> exchangeRateReverseCourse  = exchangeRatesDAO.getExchangeRatesByCode(
                targetCurrency, baseCurrency);
        Optional<ExchangeRates> exchangeRateReverseCourseUSDtoA  = exchangeRatesDAO.getExchangeRatesByCode(
                "USD", baseCurrency);
        Optional<ExchangeRates> exchangeRateReverseCourseUSDtoB = exchangeRatesDAO.getExchangeRatesByCode(
                "USD", targetCurrency);

// Если есть курс A -> B в БД
        if (exchangeRates.isPresent()) {
            ExchangeRates exchangeRate = exchangeRates.get();
            ExchangeCurrency exchangeCurrency = ExchangeCurrencyConvertor.convertCurrency(exchangeRate, Amount);
            return Optional.of(exchangeCurrency);

// Если есть курс В -> А в БД
        } else if (exchangeRateReverseCourse.isPresent()) {
            ExchangeRates exchangeRate = exchangeRateReverseCourse.get();
            ExchangeCurrency exchangeCurrency = ExchangeCurrencyConvertor.convertCurrencyReverseCourse(
                    exchangeRate, Amount);
            return Optional.of(exchangeCurrency);

// Если есть курс USD -> A &&  USD -> B в БД
        } else if (exchangeRateReverseCourseUSDtoA.isPresent() && exchangeRateReverseCourseUSDtoB.isPresent()) {
            ExchangeRates exchangeRateA = exchangeRateReverseCourseUSDtoA.get();
            ExchangeRates exchangeRateB = exchangeRateReverseCourseUSDtoB.get();

            ExchangeCurrency exchangeCurrency = ExchangeCurrencyConvertor.convertExchangeRatesWithUSD(
                    exchangeRateA,exchangeRateB, Amount);
            return Optional.of(exchangeCurrency);

        } else {
            return Optional.empty();
        }

    }

    public static ExchangeCurrency convertCurrency(
            ExchangeRates exchangeRates, Double Amount) {

        ExchangeCurrency exchangeCurrency = new ExchangeCurrency();

        exchangeCurrency.setBaseCurrency(exchangeRates.getBaseCurrency());
        exchangeCurrency.setTargetCurrency(exchangeRates.getTargetCurrency());
        exchangeCurrency.setRate(exchangeRates.getRate());
        exchangeCurrency.setAmount(Amount);
        exchangeCurrency.setConvertedAmount(
                BigDecimal.valueOf(exchangeCurrency.getRate() * exchangeCurrency.getAmount())
                        .setScale(2, RoundingMode.HALF_UP)
        );
        return exchangeCurrency;
    }
    public static ExchangeCurrency convertCurrencyReverseCourse(
            ExchangeRates exchangeRates, Double Amount) {

        ExchangeCurrency exchangeCurrency = new ExchangeCurrency();

        exchangeCurrency.setBaseCurrency(exchangeRates.getTargetCurrency());
        exchangeCurrency.setTargetCurrency(exchangeRates.getBaseCurrency());
        exchangeCurrency.setRate(1/exchangeRates.getRate());
        exchangeCurrency.setAmount(Amount);
        exchangeCurrency.setConvertedAmount(
                BigDecimal.valueOf(exchangeCurrency.getRate() * exchangeCurrency.getAmount())
                        .setScale(2, RoundingMode.HALF_UP)
        );
        return exchangeCurrency;
    }

    public static ExchangeCurrency convertExchangeRatesWithUSD(
            ExchangeRates exchangeRatesA, ExchangeRates exchangeRatesB, Double Amount) {

        ExchangeCurrency exchangeCurrency = new ExchangeCurrency();

        exchangeCurrency.setBaseCurrency(exchangeRatesA.getTargetCurrency());
        exchangeCurrency.setTargetCurrency(exchangeRatesB.getTargetCurrency());
        exchangeCurrency.setRate(exchangeRatesB.getRate()/exchangeRatesA.getRate());
        exchangeCurrency.setAmount(Amount);
        exchangeCurrency.setConvertedAmount(
                BigDecimal.valueOf(exchangeCurrency.getRate() * exchangeCurrency.getAmount())
                        .setScale(2, RoundingMode.HALF_UP)
        );
        return exchangeCurrency;
    }
}
