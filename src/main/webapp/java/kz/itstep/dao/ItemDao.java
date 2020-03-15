package kz.itstep.dao;

import kz.itstep.entity.Item;
import kz.itstep.entity.User;
import kz.itstep.pool.ConnectionPool;
import org.graalvm.compiler.hotspot.nodes.ObjectWriteBarrier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDao extends AbstractDao<Item> { //В БД моего сайта аукционов, будет таблица выставляемых товаров.
    private static final String SQL_STATEMENT_SELECT_ITEMS_ALL = "SELECT * FROM public.items";

    public static String getCategoryById(int categoryId){
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        String result = "";

        try{
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM public.categories WHERE id = " + categoryId;
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            if(resultSet.first()){
                result = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }

        return result;
    }

    public static String getOwnerFullNameById(int ownerId){
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        String ownerFullName = "";

        try{
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM public.users WHERE id = " + ownerId;
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            if(resultSet.first()){
                ownerFullName = resultSet.getString("first_name") + " " + resultSet.getString("second_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }

        return ownerFullName;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        Connection connection = ConnectionPool.getConnectionPool().getConnection();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_STATEMENT_SELECT_ITEMS_ALL);
            while(resultSet.next()){
                Item item = new Item();

                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setImageUrl(resultSet.getString("image_url"));
                item.setPrice(resultSet.getInt("price"));
                item.setCategoryId(resultSet.getInt("category_id"));
                item.setOwnerId(resultSet.getInt("owner_id"));

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }

        return items;
    }

    @Override
    public Item findById(int id) {
        return null;
    }

    @Override
    public boolean insert(Item entity) {
        return false;
    }

    @Override
    public boolean update(Item entity) {
        return false;
    }

    @Override
    public boolean delete(Item entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
