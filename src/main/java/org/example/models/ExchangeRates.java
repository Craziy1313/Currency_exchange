package org.example.models;

public class ExchangeRates {
    //Айди курса обмена, автоинкремент, первичный ключ
    private int ID;
    //ID базовой валюты, внешний ключ на Currencies.ID
    private int BaseCurrencyId;
    //ID целевой валюты, внешний ключ на Currencies.ID
    private int TargetCurrencyId;
    //Курс обмена единицы базовой валюты к единице целевой валюты
    private Double Rate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBaseCurrencyId() {
        return BaseCurrencyId;
    }

    public void setBaseCurrencyId(int baseCurrencyId) {
        BaseCurrencyId = baseCurrencyId;
    }

    public int getTargetCurrencyId() {
        return TargetCurrencyId;
    }

    public void setTargetCurrencyId(int targetCurrencyId) {
        TargetCurrencyId = targetCurrencyId;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }
}
