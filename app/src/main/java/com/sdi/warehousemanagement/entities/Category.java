package com.sdi.warehousemanagement.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by PatriciaDamian on 1/24/2018.
 */

@DatabaseTable(tableName = "category")
public class Category {
    @DatabaseField(generatedId = true)
    private int category_id;
    @DatabaseField
    private String name;

    public Category() {
    }

    public Category(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public Category(String categ1) {
        this.name = categ1;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }
}
