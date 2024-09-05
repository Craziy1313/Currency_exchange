package org.example.models;

public class Currencies {
    //Айди валюты, автоинкремент, первичный ключ
    private int ID;
    //Айди валюты, автоинкремент, первичный ключ
    private String Code;
    //Полное имя валюты
    private String FullName;
    //Символ валюты
    private String Sign;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }
}
