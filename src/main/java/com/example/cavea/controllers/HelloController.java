package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class HelloController {
    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");

    private HelloController helloController;
    private boolean isLogged=false;
    private User user;

    @FXML
    private VBox seansees;
    private boolean logged=false;
    @FXML
    private HBox bottm;
    @FXML
    private Pane pane;
    @FXML
    private FlowPane flowPane;

    @FXML
    private RadioButton btn_1;

    @FXML
    private RadioButton btn_2;

    @FXML
    private RadioButton btn_3;

    @FXML
    private RadioButton btn_4;

    @FXML
    private ImageView img;

    @FXML
    private Button logIn;

    @FXML
    private ImageView logo;

    @FXML
    private Button register;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private VBox vbox;

    @FXML
    private HBox bttns;

    @FXML
    private Button tickets;

    @FXML
    private ImageView restrictions;

    @FXML
    private ImageView rules;

    @FXML
    private ImageView cinema;
    @FXML
    private ImageView imax1;

    @FXML
    private ImageView imax2;

    @FXML
    private ImageView imax3;

    @FXML
    private ImageView imax4;

    @FXML
    private Button contact;

    public HelloController() throws SQLException {
    }

    public void init() throws SQLException, IOException {
        lbl1.prefWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        img.fitWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        img.fitHeightProperty().bind(vbox.heightProperty().multiply(101).divide(411));
        tabPane.tabMinWidthProperty().bind(vbox.widthProperty().multiply(590).divide(685).divide(8));
        pane.prefWidthProperty().bind(vbox.widthProperty().multiply(234).divide(685));
        bttns.prefWidthProperty().bind(vbox.widthProperty().multiply(200).divide(685));
        logIn.prefWidthProperty().bind(vbox.widthProperty().multiply(65).divide(685));
        register.prefWidthProperty().bind(vbox.widthProperty().multiply(98).divide(685));
        tickets.prefHeightProperty().bind(vbox.heightProperty().multiply(260).divide(411));
        tickets.prefWidthProperty().bind(vbox.widthProperty().multiply(627).divide(685));
        contact.prefHeightProperty().bind(vbox.heightProperty().multiply(260).divide(411));
        contact.prefWidthProperty().bind(vbox.widthProperty().multiply(627).divide(685));
        restrictions.fitWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        restrictions.fitHeightProperty().bind(vbox.heightProperty().multiply(1043).divide(411));
        rules.fitWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        rules.fitHeightProperty().bind(vbox.heightProperty().multiply(811).divide(411));
        cinema.fitWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        cinema.fitHeightProperty().bind(vbox.heightProperty().multiply(1438).divide(411));
        imax1.fitWidthProperty().bind(vbox.widthProperty().multiply(355).divide(685));
        imax1.fitHeightProperty().bind(vbox.heightProperty().multiply(187).divide(411));
        imax2.fitWidthProperty().bind(vbox.widthProperty().multiply(355).divide(685));
        imax2.fitHeightProperty().bind(vbox.heightProperty().multiply(187).divide(411));
        imax3.fitWidthProperty().bind(vbox.widthProperty().multiply(355).divide(685));
        imax3.fitHeightProperty().bind(vbox.heightProperty().multiply(187).divide(411));
        imax4.fitWidthProperty().bind(vbox.widthProperty().multiply(355).divide(685));
        imax4.fitHeightProperty().bind(vbox.heightProperty().multiply(187).divide(411));
        seansees.prefWidthProperty().bind(vbox.widthProperty().multiply(614).divide(685));
        seansees.prefHeightProperty().bind(vbox.heightProperty().multiply(257).divide(411));
        initMovies();
        setSeansees();
    }
    private void setSeansees() throws IOException, SQLException {
        PreparedStatement statement=conn.prepareStatement("select*from movies");
        ResultSet rs=statement.executeQuery();
        while (rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fullFilm.fxml"));
            fxmlLoader.load();
            FullFilmController controller=fxmlLoader.getController();
            Movies movies=new Movies(rs.getString("title"));
            controller.setMovies(movies);
            controller.init();
            controller.setLogged(logged);
            if (logged){controller.setUser(user);}
            seansees.getChildren().add(controller.getHbox());
        }
    }
    private void initMovies() throws SQLException, IOException {
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
            controller.setUser(user);
            AnchorPane pane = controller.getPane();
            flowPane.getChildren().add(pane);
        }
        flowPane.prefWidthProperty().bind(vbox.widthProperty().divide(685).multiply(601));
    }

    public void registerWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        newStage.setTitle("Registration");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);
        newStage.show();
    }
    public void loginWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("logIn.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        Stage newStage=new Stage();
        LoginController controller=fxmlLoader.getController();
        controller.setHelloController(helloController);
        newStage.setTitle("Log In");
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);
        newStage.show();
    }
    @FXML
    void showMain(){
        tabPane.getSelectionModel().select(0);
    }
    @FXML
    void showSessions(){
        tabPane.getSelectionModel().select(1);
    }
    @FXML
    void showTickets(){
        tabPane.getSelectionModel().select(2);
    }
    @FXML
    void showIMAX(){
        tabPane.getSelectionModel().select(3);
    }
    @FXML
    void showRestrictions(){
        tabPane.getSelectionModel().select(4);
    }
    @FXML
    void showContact(){
        tabPane.getSelectionModel().select(5);
    }
    @FXML
    void showCinemas(){
        tabPane.getSelectionModel().select(6);
    }
    @FXML
    void showRules(){
        tabPane.getSelectionModel().select(7);
    }
    @FXML
    void showImg1(ActionEvent event) throws FileNotFoundException {
        btn_2.setSelected(false);
        btn_3.setSelected(false);
        btn_4.setSelected(false);
        Image image=new Image(new FileInputStream("images/b947767b-d1c4-4d4b-b811-fa198254a4e1vat12.jpg"));
        img.setImage(image);
    }

    @FXML
    void showImg2(ActionEvent event) throws FileNotFoundException {
        btn_1.setSelected(false);
        btn_3.setSelected(false);
        btn_4.setSelected(false);
        Image image=new Image(new FileInputStream("images/9e407df7-0cea-4c19-af20-7d426abc4c9a11.jpg"));
        img.setImage(image);
    }

    @FXML
    void showImg3(ActionEvent event) throws FileNotFoundException {
        btn_2.setSelected(false);
        btn_1.setSelected(false);
        btn_4.setSelected(false);
        Image image=new Image(new FileInputStream("images/152a16fb-f82d-44f0-8ea5-24dada5b7afa1.jpg"));
        img.setImage(image);
    }

    @FXML
    void showImg4(ActionEvent event) throws FileNotFoundException {
        btn_2.setSelected(false);
        btn_3.setSelected(false);
        btn_1.setSelected(false);
        Image image=new Image(new FileInputStream("images/f7e8985e-b4ca-4929-81d4-c9dcf720365cotx.jpg"));
        img.setImage(image);
    }
    private void switchToLogged(User user){
        this.user=user;
        this.logged=true;
        bttns.getChildren().removeAll(logIn,register);
        System.out.println(user.getName());
        MenuButton menuButton=new MenuButton(user.getName());
        MenuItem exit=new MenuItem("გამოსვლა");
        MenuItem account=new MenuItem("ექაუნთი");
        menuButton.getItems().addAll(exit,account);
        exit.setOnAction(e-> {
            try {
                switchToLoggedOut();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        account.setOnAction(e -> showTickets());
        bttns.getChildren().add(menuButton);
        switchTickets();
        switchContact();
    }
    private void switchTickets(){}
    private void switchContact(){}
    private void switchToLoggedOut() throws SQLException, IOException {
        Stage oldStage=(Stage) vbox.getScene().getWindow();
        oldStage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller=fxmlLoader.getController();
        controller.init();
        controller.setHelloController(controller);
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged, User user) {
        this.logged = logged;
        switchToLogged(user);
    }

    public HelloController getHelloController() {
        return helloController;
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }
}