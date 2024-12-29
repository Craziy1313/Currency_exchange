package org.example.services;

import org.example.dao.ExchangeRatesDAO;
import org.example.dao.ExchangeRatesDAOImpl;
import org.example.dto.ExchangeCurrency;
import org.example.models.ExchangeRates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeCurrencyConvertor {

    public Optional<ExchangeCurrency> convert(
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

/**
 * Если есть курс A -> B в БД
 */
        if (exchangeRates.isPresent()) {
            ExchangeRates exchangeRate = exchangeRates.get();
            ExchangeCurrency exchangeCurrency = convertCurrency(exchangeRate, Amount);
            return Optional.of(exchangeCurrency);

/**
 * Если есть курс В -> А в БД
 */
        } else if (exchangeRateReverseCourse.isPresent()) {
            ExchangeRates exchangeRate = exchangeRateReverseCourse.get();
            ExchangeCurrency exchangeCurrency = convertCurrencyReverseCourse(
                    exchangeRate, Amount);
            return Optional.of(exchangeCurrency);

/**
 * Если есть курс USD -> A &&  USD -> B в БД
 */
        } else if (exchangeRateReverseCourseUSDtoA.isPresent() && exchangeRateReverseCourseUSDtoB.isPresent()) {
            ExchangeRates exchangeRateA = exchangeRateReverseCourseUSDtoA.get();
            ExchangeRates exchangeRateB = exchangeRateReverseCourseUSDtoB.get();

            ExchangeCurrency exchangeCurrency = convertExchangeRatesWithUSD(
                    exchangeRateA,exchangeRateB, Amount);
            return Optional.of(exchangeCurrency);

        } else {
            return Optional.empty();
        }
    }

    public ExchangeCurrency convertCurrency(
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
    public ExchangeCurrency convertCurrencyReverseCourse(
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

    public ExchangeCurrency convertExchangeRatesWithUSD(
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
