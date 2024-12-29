package org.example.models;

public class ExchangeRates {

    private int id;

    private Currencies baseCurrency;

    private Currencies targetCurrency;

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
