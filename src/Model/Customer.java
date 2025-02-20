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
public class Customer implements Serializable{
    private String id;
    private String name;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getName() {
        String[] parts = name.split(" ");
        if (parts.length < 2) return name; // Nếu chỉ có 1 từ, giữ nguyên

        String firstName = parts[parts.length - 1]; // Lấy Tên (từ cuối cùng)
        String lastAndMiddle = name.substring(0, name.lastIndexOf(" ")); // Lấy Họ + tên lót
        return firstName + ", " + lastAndMiddle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return String.format("   %-10s|   %-21s|   %-11s|    %-10s", id, getName(),  phone, email);
    }
    
}
