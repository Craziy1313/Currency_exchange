package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currencies {
    //Айди валюты, автоинкремент, первичный ключ
    private int id;

    //Айди валюты, автоинкремент, первичный ключ
    private String code;

    //Полное имя валюты
    private String name;

    //Символ валюты
    private String sign;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "ID=" + id +
                ", Code='" + code + '\'' +
                ", FullName='" + name + '\'' +
                ", Sign='" + sign + '\'' +
                '}';
    }
}
