package com.example.cavea.Objects;

import java.sql.*;

public class User {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");
    private String name;
    private String lName;
    private String email;
    private String password;

    public User(String name, String lName, String email, String password) throws SQLException {
        this.name = name;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    public User(String email) throws SQLException {
        this.email = email;
        searchLName();
        searchName();
        searchPassword();
    }

    public User(String email, String password) throws SQLException {
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void storeUser() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into user(name,last_name,email,password) values (?,?,?,?)");
        ps.setString(1, getName());
        ps.setString(2, getlName());
        ps.setString(3, getEmail());
        ps.setString(4, getPassword());
        ps.executeUpdate();
    }

    private void searchName() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select name from user where email=?");
        ps.setString(1, getEmail());
        ResultSet rs = ps.executeQuery();
        rs.next();
        this.name = rs.getString("name");
    }

    private void searchLName() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select last_name from user where email=?");
        System.out.println(getEmail());
        ps.setString(1, getEmail());
        ResultSet rs = ps.executeQuery();
        rs.next();
        this.lName = rs.getString("last_name");
    }

    private void searchPassword() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select password from user where email=?");
        ps.setString(1, getEmail());
        ResultSet rs = ps.executeQuery();
        rs.next();
        this.password = rs.getString("password");
    }

    public boolean canRegister() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select name from user where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    public boolean emailExists() throws SQLException {
        return !canRegister();
    }

    public boolean userExists() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select name from user where email=? and password=?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
