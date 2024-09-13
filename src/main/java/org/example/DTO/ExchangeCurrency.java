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
}
