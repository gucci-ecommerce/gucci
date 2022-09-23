package com.gucci.Dashboard.DashboardFragments.Models;

import androidx.lifecycle.ViewModel;

public class ProductsViewModel extends ViewModel {
    String id;
    String productTitle;
    String tag;
    String productImage;
    String newPrice;
    String originalPrice;
    String description;
    String category;
    Boolean productFavourite;

    public ProductsViewModel(String id, String productTitle, String tag, String productImage, String newPrice, String originalPrice, String description, String category, Boolean productFavourite) {
        this.id = id;
        this.productTitle = productTitle;
        this.tag = tag;
        this.productImage = productImage;
        this.newPrice = newPrice;
        this.originalPrice = originalPrice;
        this.description = description;
        this.category = category;
        this.productFavourite = productFavourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
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

    public Boolean getProductFavourite() {
        return productFavourite;
    }

    public void setProductFavourite(Boolean productFavourite) {
        this.productFavourite = productFavourite;
    }
}
