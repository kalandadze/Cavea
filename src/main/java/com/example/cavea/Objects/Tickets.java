package com.example.cavea.Objects;

import java.sql.*;

public class Tickets {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");
    private String movie;
    private String date;
    private String time;
    private String price;
    private String language;
    private String type;
    private String cinema;
    private String id;

    public Tickets(String movie, String date, String time, String price, String language, String type,String cinema) throws SQLException {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.price = price;
        this.language = language;
        this.type = type;
        this.cinema=cinema;
    }

    public void store() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into `cavea`.`tickets`(movie,date,time,price,language,type,cinema) values (?,?,?,?,?,?,?)");
        ps.setString(1, getMovie());
        ps.setString(2, getDate());
        ps.setString(3, getTime());
        ps.setString(4, getPrice());
        ps.setString(5, getLanguage());
        ps.setString(6, getType());
        ps.setString(7, getCinema());
        ps.executeUpdate();
    }
    public void findID() throws SQLException {
        PreparedStatement ps= conn.prepareStatement("select id from tickets where movie=? and date=? and time=? and cinema=?");
        ps.setString(1,getMovie());
        ps.setString(2,getDate());
        ps.setString(3,getTime());
        ps.setString(4,getCinema());
        ResultSet rs=ps.executeQuery();
        rs.next();
        this.id=rs.getInt("id")+"";
    }
    public String name(){
        return getTime()+" "+getPrice()+"₾ "+getLanguage()+" "+getType();
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getId() {
        return id;
    }
}
