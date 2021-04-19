package com.example.emotiondetection;

public class Names {
    String idn;
    String name;
    String type;
    String position;
    String date;
    public Names()
    {



    }

    public Names(String idn, String name, String type,String position, String date) {
        this.idn = idn;
        this.name = name;
        this.type = type;
        this.position=position;
        this.date = date;
    }

    public String getIdn() {
        return idn;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getPosition() {
        return position;
    }
}
