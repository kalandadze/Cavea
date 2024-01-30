package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

public class MoviesController {
    private boolean editable=false;
    @FXML
    private ImageView image;
    private User user;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button rating;

    @FXML
    private Label title;

    public void setImage(String imageURL) throws IOException {
        InputStream stream = new URL(imageURL).openStream();
        Image image1=new Image(stream);
        image.setImage(image1);
    }
    public void setRating(String rating1){
        rating.setText(rating1);
    }
    public void setTitle(String title1){
        title.setText(title1);
    }
    public AnchorPane getPane(){
        return pane;
    }
    public void ToMoviePage() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("moviePage.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        MoviePageController controller=fxmlLoader.getController();
        controller.setMovie(new Movies(title.getText()));
        controller.setUser(user);
        controller.init();
        if (editable){
            controller.makeEditable();
        }
        Stage newStage=new Stage();
        newStage.setTitle(title.getText());
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent e){
                newStage.hide();
                controller.setTrailer("https://www.google.com/");
            }
        });
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
