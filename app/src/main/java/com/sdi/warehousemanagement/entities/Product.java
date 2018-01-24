package com.sdi.warehousemanagement.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PatriciaDamian on 1/24/2018.
 */

@DatabaseTable(tableName = "product")
public class Product {
    @DatabaseField(generatedId = true)
    private int product_id;
    @DatabaseField
    private String product_code;
    @DatabaseField
    private String name;
    @DatabaseField
    private int price;
    @DatabaseField
    private String description;
    @DatabaseField(foreign = true, columnName = "category_id")
    private Category category;
    @DatabaseField
    private int quantity;

    public Product() {
    }

    public Product(String product_code, String name, int price, String description, Category category, int quantity) {
        this.product_code = product_code;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_code='" + product_code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}
