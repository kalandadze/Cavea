package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController {

    @FXML
    void addMovie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addMovie.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        newStage.setTitle("Add Movie");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void deleteMovie(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deleteMovie.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        newStage.setTitle("Movies");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        DeleteMovieController controller=fxmlLoader.getController();
        controller.init();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
