package com.afundacion.gestordetareas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TaskData {
   private String title;
   private String date;
   private String description;

    public TaskData(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskData(JSONObject json) throws JSONException {
        this.title = json.getString("name");
        this.description= json.getString("description");
        this.date= json.getString("deadline");

    }

}
