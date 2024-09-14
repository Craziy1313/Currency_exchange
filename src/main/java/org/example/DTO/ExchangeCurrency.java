package org.example.DTO;

import org.example.models.Currencies;

public class ExchangeCurrency {

    //Айди курса обмена, автоинкремент, первичный ключ
    private int ID;

    //ID базовой валюты, внешний ключ на Currencies.ID
    private Currencies BaseCurrencyId;

    //ID целевой валюты, внешний ключ на Currencies.ID
    private Currencies TargetCurrencyId;

    //Курс обмена единицы базовой валюты к единице целевой валюты
    private Double Rate;

    //Сумма
    private Double Amount;

    //Конвертированная сумма
    private Double ConvertedAmount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Currencies getBaseCurrencyId() {
        return BaseCurrencyId;
    }

    public void setBaseCurrencyId(Currencies baseCurrencyId) {
        BaseCurrencyId = baseCurrencyId;
    }

    public Currencies getTargetCurrencyId() {
        return TargetCurrencyId;
    }

    public void setTargetCurrencyId(Currencies targetCurrencyId) {
        TargetCurrencyId = targetCurrencyId;
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

    public Double getConvertedAmount() {
        return ConvertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        ConvertedAmount = convertedAmount;
    }
}
