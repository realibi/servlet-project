package kz.itstep.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool connectionPool = init();
    private static final String URL = "jdbc:postresql://localhost:5433/mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final int POOL_SIZE = 10;

    private BlockingQueue<Connection> connections;

    private ConnectionPool(String url, String user, String passwords) throws SQLException {
        for(int i = 0; i < POOL_SIZE; i++){
            connections = new ArrayBlockingQueue<>(POOL_SIZE);
            Connection connection = DriverManager.getConnection(url, user, passwords);
            connections.add(connection);
        }
    }

    private static ConnectionPool init(){
        ConnectionPool tempCP = null;
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            tempCP = new ConnectionPool(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tempCP;
    }

    public static ConnectionPool getConnectionPool(){
        return connectionPool;
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        try{
            if(!connection.isClosed()){
                if(!connections.offer(connection)){
                    System.out.println("Connection was not added!");
                }
            }else{
                System.out.println("Connection was closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearConnectionQueue() throws SQLException{
        Connection connection;

        while((connection = connections.poll()) != null){
            if(!connection.getAutoCommit()){
                connection.commit();
            }

            connection.close();
        }
    }

    public static void dispose() throws SQLException{
        if(connectionPool != null){
            connectionPool.clearConnectionQueue();
            connectionPool = null;
        }
    }
}
