/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.order;

import datnt.orderDetails.OrderDetailsDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author NTD
 */
public class OrderDTO implements Serializable {

    private int id;
    private int customerId;
    private String cusName;
    private float totalPrice;
    private String createDate;
    private String address;
    private int paymentMethodId;
    private int paymentStatusId;
    private String paymentMethod;
    private String paymentStatus;
    private List<OrderDetailsDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(int id, int customerId, String cusName, float totalPrice, String createDate, String address, String paymentMethod, String paymentStatus, List<OrderDetailsDTO> orderDetails) {
        this.id = id;
        this.customerId = customerId;
        this.cusName = cusName;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderDetails = orderDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
