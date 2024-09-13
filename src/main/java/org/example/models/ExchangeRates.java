package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Currency;

public class ExchangeRates {
    //Айди курса обмена, автоинкремент, первичный ключ
    @JsonProperty("id")
    private int ID;
    //ID базовой валюты, внешний ключ на Currencies.ID
    @JsonProperty("baseCurrency")
    private Currencies BaseCurrency;
    //ID целевой валюты, внешний ключ на Currencies.ID
    @JsonProperty("targetCurrency")
    private Currencies TargetCurrency;
    //Курс обмена единицы базовой валюты к единице целевой валюты
    @JsonProperty("rate")
    private Double Rate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Currencies getBaseCurrency() {
        return BaseCurrency;
    }

    public void setBaseCurrency(Currencies baseCurrency) {
        BaseCurrency = baseCurrency;
    }

    public Currencies getTargetCurrency() {
        return TargetCurrency;
    }

    public void setTargetCurrency(Currencies targetCurrency) {
        TargetCurrency = targetCurrency;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "ID=" + ID +
                ", BaseCurrency=" + BaseCurrency +
                ", TargetCurrency=" + TargetCurrency +
                ", Rate=" + Rate +
                '}';
    }
}
