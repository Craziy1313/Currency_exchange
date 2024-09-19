package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.models.Currencies;

import java.math.BigDecimal;

public class ExchangeCurrency {

    //ID базовой валюты, внешний ключ на Currencies.ID
    private Currencies baseCurrency;

    //ID целевой валюты, внешний ключ на Currencies.ID
    private Currencies targetCurrency;

    //Курс обмена единицы базовой валюты к единице целевой валюты
    private Double Rate;

    //Сумма
    private Double Amount;

    //Конвертированная сумма
    private BigDecimal ConvertedAmount;

    public Currencies getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currencies baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currencies getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currencies targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return ConvertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        ConvertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return "ExchangeCurrency{" +
                "BaseCurrencyId=" + baseCurrency +
                ", TargetCurrencyId=" + targetCurrency +
                ", Rate=" + Rate +
                ", Amount=" + Amount +
                ", ConvertedAmount=" + ConvertedAmount +
                '}';
    }
}
