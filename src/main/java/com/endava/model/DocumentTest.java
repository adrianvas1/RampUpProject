package com.endava.model;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class DocumentTest implements Serializable {

    @MongoObjectId
    private String _id;
    private String name;
    private String date = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime());
    private String fileName;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return new StringBuffer(" { \"name\": ")
                .append("\"")
                .append(this.name)
                .append("\"")
                .append(" \"date\": ")
                .append("\"")
                .append(this.date)
                .append("\"")
                .append(" \"fileName\": ")
                .append("\"")
                .append(this.fileName)
                .append("\"")
                .toString();
    }
}

