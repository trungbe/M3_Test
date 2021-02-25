package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    public static String jdbcURL="jdbc:mysql://localhost:3306/testmd3";
    public static String jdbcUsername="root";
    public static String jdbcPassword="";
    private static Connection connection;
    public static Connection getConnect(){
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            } catch (ClassNotFoundException e) {
                System.out.println("kh co driver");
            } catch (SQLException throwable) {
                System.out.println("kh connect sql");
            }
            System.out.println("connect thanh cong");
        }
        return connection;
    }
}
