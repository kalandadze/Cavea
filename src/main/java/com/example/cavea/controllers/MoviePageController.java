package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MoviePageController {
    private Movies movie;
    private User user;
    @FXML
    private Accordion accordion;
    private boolean canStore;

    @FXML
    private ScrollPane pane;

    @FXML
    private Label lbl;

    @FXML
    private WebView trailer;

    @FXML
    private VBox vbox;

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public void init() throws SQLException, IOException {
        lbl.prefWidthProperty().bind(pane.widthProperty().divide(623).multiply(600));
        trailer.prefWidthProperty().bind(pane.widthProperty().divide(623).multiply(600));
        trailer.prefHeightProperty().bind(pane.widthProperty().divide(623).multiply(255));
        setTrailer(movie.getTrailer());
        setAccordion();
    }

    public void setTrailer(String url) {
        WebEngine webEngine = trailer.getEngine();
        url = url.replace("watch?v=", "embed/");
        webEngine.load(url);
    }

    private void setAccordion() throws SQLException, IOException {
        List<String> dates = movie.dates();
        for (String date : dates) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accordion.fxml"));
            fxmlLoader.load();
            AccordionController controller=fxmlLoader.getController();
            controller.setMovies(movie);
            controller.setDate(date);
            controller.setCanStore(canStore);
            controller.setUser(user);
            controller.init();
            TitledPane t1 = new TitledPane(date, controller.getVbox());
            accordion.getPanes().add(t1);
        }
    }

    public void makeEditable() {
        Button delete = new Button("delete");
        Button changeProperties = new Button("change properties");
        Button addTicket = new Button("add ticket");
        delete.setOnAction(e -> {
            try {
                delete();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        addTicket.setOnAction(e -> {
            try {
                addTicket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        changeProperties.setOnAction(e -> {
            try {
                changeProperties();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox hBox = new HBox(delete, changeProperties, addTicket);
        vbox.getChildren().add(hBox);
    }

    private void delete() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dltMovies.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        DltMoviesController controller = fxmlLoader.getController();
        controller.setMovies(movie);
        newStage.setTitle("delete Movie");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }

    private void addTicket() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addTicket.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        AddTicketController controller = fxmlLoader.getController();
        controller.setMovies(movie);
        newStage.setTitle("Add Movie");
        newStage.setScene(newScene);
        newStage.initModality(
                Modality.APPLICATION_MODAL);
        newStage.show();
    }

    private void changeProperties() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addMovie.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        AddMovieController controller = fxmlLoader.getController();
        controller.setText(movie);
        controller.setDeletable(true);
        newStage.setTitle("Add Movie");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }

    public boolean isCanStore() {
        return canStore;
    }

    public void setCanStore(boolean canStore) {
        this.canStore = canStore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
