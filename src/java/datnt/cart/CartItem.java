/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.cart;

import datnt.cake.CakeDTO;
import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class CartItem implements Serializable {

    private CakeDTO cake;
    private int quantity;

    public CartItem() {
    }

    public CartItem(CakeDTO cake, int quantity) {
        this.cake = cake;
        this.quantity = quantity;
    }

    public CakeDTO getCake() {
        return cake;
    }

    public void setCake(CakeDTO cake) {
        this.cake = cake;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
