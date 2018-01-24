package com.sdi.warehousemanagement.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PatriciaDamian on 1/24/2018.
 */

@DatabaseTable(tableName = "stock")
public class Stock {

    @DatabaseField(foreign = true, columnName = "product_id", uniqueCombo = true)
    private Product product;
    @DatabaseField(uniqueCombo = true)
    private String size;
    @DatabaseField
    private int quantity;

    public Stock() {
    }

    public Stock(Product product, String size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "product_id=" + product.toString() +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
