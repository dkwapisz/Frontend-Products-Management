package org.pk.lab3.model;

public enum ProductCategory {
    ELECTRONICS("Electronics"),
    MOTORS("Motors"),
    COLLECTIBLES("Collectibles"),
    HOME("Home"),
    GARDEN("Garden"),
    FASHION("Fashion"),
    TOYS("Toys"),
    SPORT("Sport"),
    INDUSTRIAL("Industrial"),
    JEWELRY("Jewelry");

    private final String productCategory;

    ProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategoryName() {
        return this.productCategory;
    }
}

