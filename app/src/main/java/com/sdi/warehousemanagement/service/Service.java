package com.sdi.warehousemanagement.service;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sdi.warehousemanagement.DbHelper;
import com.sdi.warehousemanagement.entities.Category;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.entities.Stock;
import com.sdi.warehousemanagement.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PatriciaDamian on 1/24/2018.
 */

public class Service {
    private static DbHelper dbHelper = null;
    private RuntimeExceptionDao<User, Integer> userDao;
    private RuntimeExceptionDao<Category, Integer> categoryDao;
    private RuntimeExceptionDao<Product, Integer> productDao;

    public Service(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        userDao = dbHelper.getUserDao();
        productDao = dbHelper.getProductDao();
        categoryDao = dbHelper.getCategoryDao();
    }

    public Product getProduct(String code){
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            return productQuery.where().eq("product_code", code).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addProduct(String product_code, String name, int price, String description, int category_id, int quantity) {
        Category categ = getCategory(category_id);
        if (categ != null) {
            try {
                productDao.create(new Product(product_code, name, price, description, categ, quantity));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public Category getCategory(int id) {
        try {
            return categoryDao.queryForId(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeProduct(String code) {
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            Product prod = productQuery.where().eq("product_code", code).queryForFirst();
            productDao.delete(prod);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(String product_code, String name, int price, String description, int category_id, int quantity) {
        QueryBuilder<Product, Integer> productQuery = productDao.queryBuilder();
        try {
            Product prod = getProduct(product_code);
            if (prod == null) {
                addProduct(product_code, name, price, description, category_id, quantity);
            } else {
                prod.setCategory(getCategory(category_id));
                prod.setDescription(description);
                prod.setName(name);
                prod.setPrice(price);
                prod.setQuantity(quantity);
                productDao.update(prod);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        try {
            return productDao.queryForAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new ArrayList<Product>();
    }

    public List<Category> getAllCategories() {
        try {
            return categoryDao.queryForAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new ArrayList<Category>();
    }

    public Category getCategoryByName(String name) {
        QueryBuilder<Category, Integer> categoryQuery = categoryDao.queryBuilder();
        try {
            return categoryQuery.where().eq("name", name).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        try {
            return userDao.queryForAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    public User getUserByName(String username) {
        QueryBuilder<User, Integer> userQuery = userDao.queryBuilder();
        try {
            return userQuery.where().eq("username", username).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
