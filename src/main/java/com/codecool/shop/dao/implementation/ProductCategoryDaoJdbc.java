package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DBController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    @Override
    public void add(ProductCategory category) {
        String query = " INSERT INTO productcategory (c_name, c_department, c_description) " +
                "VALUES ('" + category.getName() + "', '" + category.getDepartment() + "', '"
                + category.getDescription() + "');";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) throws SQLException {
        ProductCategory category = null;
        String query = "SELECT * FROM productcategory WHERE c_id ='" + id + "';";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {

                category = new ProductCategory(result.getString("c_name"), result.getString("c_department"),
                        result.getString("c_description"));
                category.setId(id);
                category.setProducts(new ProductDaoJdbc().getBy(category));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM productcategory WHERE c_id='" + id + "';";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement())
        {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() throws SQLException {
        List<ProductCategory> allCategories = new ArrayList<>();
        String query = "SELECT * FROM productcategory";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                ProductCategory current = new ProductCategory(result.getString("c_name"),
                        result.getString("c_department"), result.getString("c_description"));
                current.setId(result.getInt("c_id"));
                current.setProducts(new ProductDaoJdbc().getBy(current));
                allCategories.add(current);
            }
            return allCategories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clearAll() {
        String drop = "DROP TABLE productcategory;";
        String create = "CREATE TABLE productcategory(c_id serial PRIMARY KEY, c_name VARCHAR(30)," +
                "c_department varchar(250), c_description varchar(250));";
        try (Connection connection = DBController.getConnection(); Statement statement = connection.createStatement())
        {
            statement.executeUpdate(drop);
            statement.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
