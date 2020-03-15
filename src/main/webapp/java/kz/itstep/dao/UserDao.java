package kz.itstep.dao;

import kz.itstep.entity.User;
import kz.itstep.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {

    private static final String SQL_STATEMENT_SELECT_USERS_ALL = "SELECT * FROM public.users";
    private static final String SQL_STATEMENT_INSERT_USER = "insert into public.users (login, password, first_name, second_name) values(id, login, password, firstName, secondName)";
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.getConnectionPool().getConnection();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_STATEMENT_SELECT_USERS_ALL);
            while(resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setSecondName(resultSet.getString("second_name"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }

        return users;
    }

    @Override
    public User findById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(User entity) {
        boolean inserted = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();


        return inserted;
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException();
    }
}
