package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRates {

    //Айди курса обмена, автоинкремент, первичный ключ
    private int id;

    //ID базовой валюты, внешний ключ на Currencies.ID
    private Currencies baseCurrency;

    //ID целевой валюты, внешний ключ на Currencies.ID
    private Currencies targetCurrency;

    //Курс обмена единицы базовой валюты к единице целевой валюты
    private Double rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "ID=" + id +
                ", BaseCurrency=" + baseCurrency +
                ", TargetCurrency=" + targetCurrency +
                ", Rate=" + rate +
                '}';
    }
}
