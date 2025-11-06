package UT02.Mensajes;

import java.util.Date;

enum msgTypes{
    information,
    warnning,
    urgent
}

public class Mensaje {
    private String msg;
    private msgTypes type;
    private long date;

    Mensaje(String msg, msgTypes type){
        this.msg = msg;
        this.type = type;
        date = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return "Content: ["+this.msg+"] Type: ["+this.type+"] Creation date: ["+new Date(date)+"]";
    }
}