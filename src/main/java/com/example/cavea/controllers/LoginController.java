package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController extends HelloController {

    private HelloController helloController;
    @FXML
    private TextField email;

    @FXML
    private TextField password;

    public LoginController() throws SQLException {
    }

    @FXML
    void logIn(ActionEvent event) throws SQLException, IOException {
        String email1 = email.getText();
        String password1 = password.getText();
        if (Objects.equals(email1, "root") && Objects.equals(password1, "password")) {
            logInAsAdmin();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            User user = new User(email1);
            helloController.setLogged(true,user);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }

    public void logInAsAdmin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        newStage.setTitle("Registration");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }

    private boolean userExists(User user) throws SQLException {
        if (!(user.userExists()) && user.emailExists()) {
            password.clear();
            password.setPromptText("პაროლი არასწორია!");
            password.setStyle("-fx-prompt-text-fill: red;");
            return false;
        } else if (!user.userExists()) {
            password.clear();
            email.clear();
            email.setPromptText("მომხმარებელი არ არსებობს!");
            email.setStyle("-fx-prompt-text-fill: red;");
            return false;
        }
        return true;
    }

    public void registerWindow1(ActionEvent event) throws IOException {
        registerWindow(event);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public HelloController getHelloController() {
        return helloController;
    }

    @Override
    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }
}
