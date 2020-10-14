package com.example.e_comretail.Details;

public class SubCategoryDetails {
    private String categoryName, subCategoryName, subCategoryPhoto;

    public SubCategoryDetails() {
    }

    public SubCategoryDetails(String categoryName, String subCategoryName, String subCategoryPhoto) {
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.subCategoryPhoto = subCategoryPhoto;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryPhoto() {
        return subCategoryPhoto;
    }

    public void setSubCategoryPhoto(String subCategoryPhoto) {
        this.subCategoryPhoto = subCategoryPhoto;
    }
}
