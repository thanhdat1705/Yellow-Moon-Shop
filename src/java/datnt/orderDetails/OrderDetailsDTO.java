/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.orderDetails;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class OrderDetailsDTO implements Serializable {

    private int orderId;
    private int cakeId;
    private String cakeName;
    private String image;
    private String brand;
    private String expiration;
    private int quantity;
    private float price;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(int orderId, int cakeId, String cakeName, String image, String brand, String expiration, int quantity, float price) {
        this.orderId = orderId;
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.image = image;
        this.brand = brand;
        this.expiration = expiration;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    
    
}
