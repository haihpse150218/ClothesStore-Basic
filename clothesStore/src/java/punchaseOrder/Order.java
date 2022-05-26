/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package punchaseOrder;

import entities.Account;
import java.util.Date;

/**
 *
 * @author SE150218 Hoang Phi Hai
 */
public class Order {
    private int orderId;
    private Date date;
    private Account customerName;
    private Account employeeName;
    private String address; //get address
    private double total;
    private String status;// 'canceled' OR 'paid' 'delivered' 'packaged' 'confirmed''new'

    public Order() {
    }

    public Order(int orderId, Date date, Account customerName, Account employeeName, 
            String address, double total, String status) {
        this.orderId = orderId;
        this.date = date;
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.address = address;
        this.total = total;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Account customerName) {
        this.customerName = customerName;
    }

    public Account getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(Account employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", date=" + date + ", customerName=" + customerName + ", employeeName=" + employeeName + ", address=" + address + ", total=" + total + ", status=" + status + '}';
    }
    
}
   
   

   