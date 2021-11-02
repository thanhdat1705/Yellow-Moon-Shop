/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.account;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class AccountDTO implements Serializable{
    private String userId;
    private String name;
    private String password;
    private int roleId;

    public AccountDTO() {
    }
    
    public AccountDTO(String userId, String name, String password, int roleId) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

   
    
}
