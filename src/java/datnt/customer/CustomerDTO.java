/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.customer;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class CustomerDTO implements Serializable {

    private int id;
    private String accountId;
    private String name;
    private String phone;
    private String address;

    public CustomerDTO() {
    }

    public CustomerDTO(int id, String accountId, String name, String phone, String address) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
