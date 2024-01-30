package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DeleteMovieController {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");
    @FXML
    private FlowPane flowPane;

    public void init() throws SQLException, IOException {
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select*from movies;");
        while (rs.next()){
            Movies movies=new Movies(rs.getString("title"),rs.getString("rating"),rs.getString("image"),rs.getString("trailer"));
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("movies.fxml"));
            fxmlLoader.load();
            MoviesController controller=fxmlLoader.getController();
            controller.setRating(movies.getRating());
            controller.setTitle(movies.getTitle());
            controller.setImage(movies.getImageURL());
            AnchorPane pane = controller.getPane();
            controller.setEditable(true);
            flowPane.getChildren().add(pane);
        }
        flowPane.prefWidthProperty().bind(flowPane.getScene().widthProperty().divide(670).multiply(655));
    }

    public DeleteMovieController() throws SQLException {
    }

}
