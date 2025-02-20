/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author myanh
 */
public class FeastMenu implements Serializable{

    private String id;
    private String eventDate;
    private String customerId;
    private String setMenu;
    private String tables;
    private double price;
    private double Cost;

    public FeastMenu(String id, String eventDate, String customerId, String setMenu, String tables, double price, double Cost) {
        this.id = id;
        this.eventDate = eventDate;
        this.customerId = customerId;
        this.setMenu = setMenu;
        this.tables = tables;
        this.price = price;
        this.Cost = Cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSetMenu() {
        return setMenu;
    }

    public void setSetMenu(String setMenu) {
        this.setMenu = setMenu;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    @Override
    public String toString() {
        return String.format("   %-8s|   %-17s|   %-17s|    %-14s|     %-12s|     %-13.2f|     %-9s", id, eventDate, customerId, setMenu, price, tables, Cost);
    }

}
