package kz.itstep.dao;

import kz.itstep.entity.Category;
import kz.itstep.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends AbstractDao<Category> {
    private static final String SQL_STATEMENT_SELECT_CATEGORIES_ALL = "SELECT * FROM public.categories";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection = ConnectionPool.getConnectionPool().getConnection();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_STATEMENT_SELECT_CATEGORIES_ALL);
            while(resultSet.next()){
                Category category = new Category();

                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }

        return categories;
    }

    @Override
    public Category findById(int id) {
        return null;
    }

    @Override
    public boolean insert(Category entity) {
        return false;
    }

    @Override
    public boolean update(Category entity) {
        return false;
    }

    @Override
    public boolean delete(Category entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
