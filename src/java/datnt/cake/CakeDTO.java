/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.cake;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class CakeDTO implements Serializable{

    private int id;
    private String name;
    private String image;
    private float price;
    private String brand;
    private String description;
    private String category;
    private int quantity;
    private String createDate;
    private String lastUpdateDate;
    private String expirationDate;
    private String userUpdate;
    private boolean status;
    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public CakeDTO() {
    }

    public CakeDTO(int id, String name, String image, float price, String brand, String description, String category, int quantity, String createDate, String lastUpdateDate, String expirationDate, String userUpdate, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.expirationDate = expirationDate;
        this.userUpdate = userUpdate;
        this.status = status;
        this.categoryId = categoryId;
    }

    public CakeDTO(int id, String name, String image, float price, String brand, String category, int quantity, String expirationDate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
