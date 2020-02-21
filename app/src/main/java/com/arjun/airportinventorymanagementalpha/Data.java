package com.arjun.airportinventorymanagementalpha;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {

    public String code, name;
    String date;
    public Data()
    {
        code="";
        name="";
        date="";
    }
    public Data(String co, String nm){

        this.code=co;
        this.name=nm;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy 'at' hh:mm:ss a");
        this.date = ft.format(dNow);
    }
}
