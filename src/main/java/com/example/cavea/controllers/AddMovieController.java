package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AddMovieController {
    private boolean deletable=false;
    private Movies movies;
    @FXML
    private ImageView poster;

    @FXML
    private TextField posterInp;

    @FXML
    private TextField rating;

    @FXML
    private TextField title;

    @FXML
    private TextField trailer;


    @FXML
    void addFilm(ActionEvent event) throws SQLException {
        if (!checkIfBlank()){
            if (deletable){
                movies.deleteMovie();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
            Movies movie=new Movies(title.getText(),rating.getText(),posterInp.getText(),trailer.getText());
            movie.storeMovie();
        }
    }

    private boolean checkIfBlank(){
        boolean isBlank=false;
        if (Objects.equals(rating.getText(), "")){
            rating.setPromptText("ფილმის რეიტინგი არ შეგიყვანიათ!");
            rating.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }if (Objects.equals(posterInp.getText(), "")){
            posterInp.setPromptText("ფილმის პოსტერის მისამართი არ შეგიყვანიათ!");
            posterInp.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }if (Objects.equals(title.getText(), "")){
            title.setPromptText("ფილმის სათაური არ შეგიყვანიათ!");
            title.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }if (Objects.equals(trailer.getText(), "")){
            trailer.setPromptText("ტრეილერის url-ი არ შეგიყვანიათ!");
            trailer.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        return isBlank;
    }

    @FXML
    void searchPoster(ActionEvent event) {
        poster.setImage(new Image(posterInp.getText()));
    }

    @FXML
    void searchTrailer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trailer.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        TrailerController controller=fxmlLoader.getController();
        controller.setTrailer(trailer.getText());
        newStage.setTitle("trailer");
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
    public void setText(Movies movie){
        rating.setText(movie.getRating());
        trailer.setText(movie.getTrailer());
        title.setText(movie.getTitle());
        posterInp.setText(movie.getImageURL());
        this.movies=movie;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
