/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author bedhu
 */
public class koneksi {

    private String host;
    private String db;
    private String username;
    private String password;
    private Connection con;
    private Statement stm;

    public koneksi() throws SQLException {
        username = "root";
        password = "";
        host = "localhost";
        db = "trpl";
        String url = "jdbc:mysql://" + host + ":3306/" + db;
        con = DriverManager.getConnection(url, username, password);
        stm = con.createStatement();
    }

    public void execute(String query) throws SQLException {
        stm.executeUpdate(query);
    }

    public ResultSet getResult(String query) throws SQLException {
        ResultSet rs = stm.executeQuery(query);
        return rs;
    }
}
