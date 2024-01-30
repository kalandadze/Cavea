package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class FullFilmController {

    private Movies movies;
    private boolean logged=false;
    private User user;

    @FXML
    private HBox hbox;

    @FXML
    private ScrollPane pane;
    @FXML
    private ImageView poster;

    @FXML
    private VBox vbox;

    public void init() throws IOException, SQLException {
        InputStream stream = new URL(movies.getImageURL()).openStream();
        Image image1=new Image(stream);
        poster.setImage(image1);
        pane.prefHeightProperty().bind(poster.fitHeightProperty());

        List<String> dates = movies.dates();
        for (String date : dates) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accordion.fxml"));
            fxmlLoader.load();
            AccordionController controller=fxmlLoader.getController();
            controller.setMovies(movies);
            controller.setDate(date);
            controller.setCanStore(false);
            controller.setLogged(logged);
            if (logged){controller.setUser(user);}
            controller.init();
            vbox.getChildren().add(controller.getVbox());
            break;
        }
    }
    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    public HBox getHbox() {
        return hbox;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void setHbox(HBox hbox) {
        this.hbox = hbox;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
