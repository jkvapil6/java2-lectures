package cz.upol.zp4jv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {


    /**
     * Pripoji se k DB pres sitove rozhrani
     */
    public static Connection getConnection(String connectionURL) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // vytvori pripojeni
        Connection con = DriverManager.getConnection(connectionURL,"root","");
        System.out.println("Connecting to: " + connectionURL);

        return con;
    }
}
