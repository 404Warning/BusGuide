package com.example.busguide;

import org.litepal.crud.LitePalSupport;

public class RoutePlanHistory extends LitePalSupport {
    private int id;
    private String route;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
