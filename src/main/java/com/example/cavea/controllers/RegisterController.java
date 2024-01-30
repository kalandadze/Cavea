package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterController extends HelloController {


    @FXML
    private Label checkboxError;
    @FXML
    private CheckBox check;

    @FXML
    private TextField email;

    @FXML
    private TextField lastName;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordConfirm;

    @FXML
    private Button policy;

    @FXML
    private Button register;

    public RegisterController() throws SQLException {
    }

    public void showPolicy(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("policy.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        newStage.setTitle("Hello!");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);
        newStage.show();
    }
    public void loginWindow1(ActionEvent event) throws IOException{
        loginWindow(event);
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
    public void register(ActionEvent event) throws SQLException, InterruptedException {
        String name1=name.getText();
//        name.clear();
        String lName1=lastName.getText();
//        lastName.clear();
        String email1=email.getText();
//        email.clear();
        String password1=password.getText();
//        password.clear();
        String passwordConfirm1=passwordConfirm.getText();
//        passwordConfirm.clear();
        boolean checkbox=check.isSelected();

        boolean isBlank =checkIfBlank(name1,lName1,email1,password1,passwordConfirm1,checkbox);
        boolean passwordMatches=passwordConfirmed(password1,passwordConfirm1);

        boolean canRegister=(!(isBlank)) && passwordMatches;
        if (canRegister){register1(name1,lName1,email1,password1,event);}
    }

    private void register1(String name1, String lName1,String email1,String password1,ActionEvent event) throws SQLException, InterruptedException {
        User user=new User(name1,lName1,email1,password1);
        boolean canRegister= user.canRegister();
        if (!canRegister|| Objects.equals(email1, "root")){
            email.clear();
            email.setPromptText("e-mail-ი უკვე არსებობს!");
            email.setStyle("-fx-prompt-text-fill: red;");
        }else {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            user.storeUser();
            Stage stage=new Stage();
            AnchorPane ap=new AnchorPane();
            Label label=new Label("მომხმარებელი წარმატებულად დარეგისტრირდა");
            ap.getChildren().add(label);
            Scene scene=new Scene(ap);
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean passwordConfirmed(String password1,String passwordConfirm1){
        if (!Objects.equals(password1, passwordConfirm1)){
            password.clear();
            passwordConfirm.clear();
            password.setPromptText("პაროლი არ ემთხვევა დადასტურებულ პაროლს!");
            password.setStyle("-fx-prompt-text-fill: red;");
            passwordConfirm.setPromptText("პაროლი არ ემთხვევა დადასტურებულ პაროლს!");
            passwordConfirm.setStyle("-fx-prompt-text-fill: red;");
            return false;
        }
        return true;
    }
    private boolean checkIfBlank(String name1, String lName1,String email1,String password1,String passwordConfirm1,boolean checkbox){
        boolean isBlank=false;
        if ((Objects.equals(name1, ""))) {
            name.setPromptText("სახელი არ შეგიყვანიათ!");
            name.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        if ((Objects.equals(lName1, ""))) {
            lastName.setPromptText("გვარი არ შეგიყვანიათ!");
            lastName.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        if ((Objects.equals(email1, ""))) {
            email.setPromptText("e-mail-ი არ შეგიყვანიათ!");
            email.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        if ((Objects.equals(password1, ""))) {
            password.setPromptText("პაროლი არ შეგიყვანიათ!");
            password.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        if ((Objects.equals(passwordConfirm1, ""))) {
            passwordConfirm.setPromptText("პაროლი არ დაგიდასტურებიათ!");
            passwordConfirm.setStyle("-fx-prompt-text-fill: red;");
            isBlank=true;
        }
        if (!checkbox){
            checkboxError.setText("პირობების დათანხმების გარეშე ვერ დარეგისტრირდებით!");
            checkboxError.setStyle("-fx-text-fill: red;");
            isBlank=true;
        }else {
            checkboxError.setText("");
        }
        return isBlank;
    }
}