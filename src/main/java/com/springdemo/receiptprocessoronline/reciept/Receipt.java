package com.springdemo.receiptprocessoronline.reciept;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Entity
public class Receipt implements Serializable {
    @Id
    private String id; // Changed to String to hold UUID
    @NotEmpty(message = "retailer is required.")
    private String retailer;
    @NotEmpty(message = "purchaseDate is required.")
    private String purchaseDate; // Format: YYYY-MM-DD
    @NotEmpty(message = "purchaseTime is required.")
    private String purchaseTime; // Format: HH:MM (24-hour time)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    private List<ReceiptItem> items;
    @NotEmpty(message = "total is required.")
    private String total;

    public Receipt(){
    }

    public Receipt(String id, String retailer, String purchaseDate, String purchaseTime, List<ReceiptItem> items, String total) {
        this.id = id;
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
