package com.example.cavea.controllers;

import com.example.cavea.Objects.BoughtTicket;
import com.example.cavea.Objects.Movies;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class DltMoviesController {

    private Movies movies;
    @FXML
    private Label lbl;
    @FXML
    private Button dlt;

    @FXML
    void cancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        movies.deleteMovie();
    }
    private void buy(BoughtTicket ticket) throws SQLException {
        ticket.store();
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
        lbl.setText("confirm deleting "+movies.getTitle()+" from database");
    }
    public void setMovies(BoughtTicket ticket) {
        this.movies = movies;
        lbl.setText("confirm buying "+movies.getTitle()+"'s tickets");
        dlt.setText("buy");
        dlt.setOnAction(e-> {
            try {
                buy(ticket);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
