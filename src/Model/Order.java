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
public class Order implements Serializable {

    private int orderId;
    private String customerId;
    private String menuCode;
    private int tables;
    private double totalCost;
    private String eventDate;
    private double price;

    public Order(int orderId, String customerId, String menuName, double price, int tables, double totalCost, String eventDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.menuCode = menuName;
        this.tables = tables;
        this.totalCost = totalCost;
        this.price = price;
        this.eventDate = eventDate;
    }

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("   %-8s|   %-17s|   %-17s|    %-14s|     %-12.2f|     %-13d|     %-9.2f", orderId, eventDate, customerId, menuCode, price, tables, totalCost);
    }
}
