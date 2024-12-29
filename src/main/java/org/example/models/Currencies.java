package org.example.models;

public class Currencies {

    private int id;

    private String code;

    private String name;

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
