package com.gucci.Dashboard.DashboardFragments.Models;

import androidx.lifecycle.ViewModel;

public class CategoriesViewModel extends ViewModel {
    String title;
    String sub_title;
    int bg;
    int image;

    public CategoriesViewModel(String title, String sub_title, int bg, int image) {
        this.title = title;
        this.sub_title = sub_title;
        this.bg = bg;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
