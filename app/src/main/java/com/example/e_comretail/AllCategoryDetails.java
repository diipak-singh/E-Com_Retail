package com.example.e_comretail;

public class AllCategoryDetails {
    private String categoryname, categotyphoto;

    public AllCategoryDetails() {
    }

    public AllCategoryDetails(String categoryname, String categotyphoto) {
        this.categoryname = categoryname;
        this.categotyphoto = categotyphoto;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategotyphoto() {
        return categotyphoto;
    }

    public void setCategotyphoto(String categotyphoto) {
        this.categotyphoto = categotyphoto;
    }
}
