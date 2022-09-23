package com.gucci.Dashboard.DashboardFragments.Models;

import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {
    String id;
    String productImage;
    String productTitle;
    String productOriginalPrice;
    String productNewPrice;
    String quantity;

    public CartViewModel(String id, String productImage, String productTitle, String productOriginalPrice, String productNewPrice, String quantity) {
        this.id = id;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productOriginalPrice = productOriginalPrice;
        this.productNewPrice = productNewPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(String productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public String getProductNewPrice() {
        return productNewPrice;
    }

    public void setProductNewPrice(String productNewPrice) {
        this.productNewPrice = productNewPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
