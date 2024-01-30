package com.example.cavea.Objects;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Movies {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");

    private String title;
    private String rating;
    private String imageURL;
    private String trailer;
    private int id;

    public Movies(String title, String rating, String imageURL, String trailer) throws SQLException {
        this.title = title;
        this.rating = rating;
        this.imageURL = imageURL;
        this.trailer=trailer;
    }
    public Movies(String title) throws SQLException {
        this.title = title;
        searchImageURL();
        searchRating();
        searchTrailer();
        searchID();
    }
    public void storeMovie() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into movies(title,rating,image,trailer) values (?,?,?,?)");
        ps.setString(1, getTitle());
        ps.setString(2, getRating());
        ps.setString(3, getImageURL());
        ps.setString(4,getTrailer());
        ps.executeUpdate();
    }
    public void deleteMovie() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from movies where title=?");
        ps.setString(1, getTitle());
        ps.executeUpdate();
    }
    public List<String> dates() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select date from tickets where movie=? group by date;");
        ps.setString(1,getTitle());
        ResultSet rs= ps.executeQuery();
        List<String> dates=new ArrayList<>();
        while (rs.next()){
            dates.add(rs.getString("date"));
        }
        return dates;
    }
    private void searchRating() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select rating from movies where title=?");
        ps.setString(1,title);
        ResultSet rs=ps.executeQuery();
        rs.next();
        this.rating= rs.getString("rating");
    }
    private void searchTrailer() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select trailer from movies where title=?");
        ps.setString(1,title);
        ResultSet rs=ps.executeQuery();
        rs.next();
        this.trailer= rs.getString("trailer");
    }
    private void searchID() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select id from movies where title=?");
        ps.setString(1,title);
        ResultSet rs=ps.executeQuery();
        rs.next();
        this.id= rs.getInt("id");
    }
    private void searchImageURL() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select image from movies where title=?");
        ps.setString(1,title);
        ResultSet rs=ps.executeQuery();
        rs.next();
        this.imageURL= rs.getString("image");
    }

    public Connection getConn() {
        return conn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
