package com.example.cavea.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoughtTicket {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");

    private String userEmail;
    private String movieTitle;
    private String ticketID;

    public BoughtTicket(String userEmail, String movieTitle, String ticketID) throws SQLException {
        this.userEmail = userEmail;
        this.movieTitle = movieTitle;
        this.ticketID = ticketID;
    }

    public void store() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into bought_tickets(user_email,movie_title,ticketID) values (?,?,?)");
        ps.setString(1, getUserEmail());
        ps.setString(2, getMovieTitle());
        ps.setString(3, getTicketID());
        ps.executeUpdate();
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
