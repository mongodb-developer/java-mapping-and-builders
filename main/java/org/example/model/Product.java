package org.example.model;

import org.bson.types.ObjectId;

import java.util.List;

public class Product {
    private ObjectId _id;
    private String productName;
    private String category;
    private double price;
    private int stock;
    private String description;
    private String brand;
    private List<Image> images;
    private String features;
    private List<Reviews> reviews;

    public Product() {
    }

    public Product(ObjectId _id, String productName, String category, double price, int stock, String description, String brand, List<Image> images, String features, List<Reviews> reviews) {
        this._id = _id;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.images = images;
        this.features = features;
        this.reviews = reviews;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + _id +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", features='" + features + '\'' +
                ", images=" + images +
                ", reviews=" + reviews +
                '}';
    }

}

