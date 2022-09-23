package com.gucci.Dashboard.DashboardFragments.Models;


import androidx.lifecycle.ViewModel;

public class ProductTypeViewModel extends ViewModel {
    String type_name;
    Boolean selected;

    public ProductTypeViewModel(String type_name, Boolean selected) {
        this.type_name = type_name;
        this.selected = selected;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}

