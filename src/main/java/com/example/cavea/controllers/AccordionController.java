package com.example.cavea.controllers;

import com.example.cavea.HelloApplication;
import com.example.cavea.Objects.BoughtTicket;
import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.Tickets;
import com.example.cavea.Objects.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.*;

public class AccordionController {
    private Movies movies;
    private String date;
    private User user;
    private boolean canStore=true;
    private boolean logged=false;

    private final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea", "root", "password");


    @FXML
    private Label eastPoint;

    @FXML
    private FlowPane eastPointPane;

    @FXML
    private Label galeria;

    @FXML
    private FlowPane galeriaPane;

    @FXML
    private Label saburtalo;

    @FXML
    private FlowPane saburtaloPane;

    @FXML
    private Label tbilisi;

    @FXML
    private FlowPane tbilisiPane;

    @FXML
    private VBox vbox;

    public VBox getVbox() {
        return vbox;
    }

    public AccordionController() throws SQLException {
    }

    public  void  init() throws SQLException {
        eastPointPane.prefWidthProperty().bind(vbox.widthProperty().divide(600).multiply(578));
        galeriaPane.prefWidthProperty().bind(vbox.widthProperty().divide(600).multiply(578));
        saburtaloPane.prefWidthProperty().bind(vbox.widthProperty().divide(600).multiply(578));
        tbilisiPane.prefWidthProperty().bind(vbox.widthProperty().divide(600).multiply(578));
        setGaleriaPane();
        setEastPointPane();
        setSaburtaloPane();
        setTbilisiPane();
    }
    private void setTbilisiPane() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select*from tickets where movie=? and date=? and cinema='თბილისი მოლი'");
        ps.setString(1, movies.getTitle());
        ps.setString(2, date);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Tickets ticket=new Tickets(rs.getString("movie"),rs.getString("date"),rs.getString("time"),rs.getString("price"),rs.getString("language"),rs.getString("type"),rs.getString("cinema"));
            Button button=new Button(ticket.name());
            tbilisiPane.getChildren().add(button);
            button.setOnAction(e-> {
                try {
                    action(ticket);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
    private void setEastPointPane() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select*from tickets where movie=? and date=? and cinema='ისთ ფოინთი'");
        ps.setString(1, movies.getTitle());
        ps.setString(2, date);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Tickets ticket=new Tickets(rs.getString("movie"),rs.getString("date"),rs.getString("time"),rs.getString("price"),rs.getString("language"),rs.getString("type"),rs.getString("cinema"));
            Button button=new Button(ticket.name());
            eastPointPane.getChildren().add(button);
            button.setOnAction(e-> {
                try {
                    action(ticket);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
    private void setSaburtaloPane() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select*from tickets where movie=? and date=? and cinema='სითი მოლი საბურთალო'");
        ps.setString(1, movies.getTitle());
        ps.setString(2, date);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Tickets ticket=new Tickets(rs.getString("movie"),rs.getString("date"),rs.getString("time"),rs.getString("price"),rs.getString("language"),rs.getString("type"),rs.getString("cinema"));
            Button button=new Button(ticket.name());
            saburtaloPane.getChildren().add(button);
            button.setOnAction(e-> {
                try {
                    action(ticket);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
    private void setGaleriaPane() throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select*from tickets where movie=? and date=? and cinema='გალერია მოლი'");
        ps.setString(1, movies.getTitle());
        ps.setString(2, date);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Tickets ticket=new Tickets(rs.getString("movie"),rs.getString("date"),rs.getString("time"),rs.getString("price"),rs.getString("language"),rs.getString("type"),rs.getString("cinema"));
            Button button=new Button(ticket.name());
            galeriaPane.getChildren().add(button);
            button.setOnAction(e-> {
                try {
                    action(ticket);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
    public void action(Tickets tickets) throws IOException, SQLException {
        if (canStore){
            tickets.findID();
            BoughtTicket boughtTicket=new BoughtTicket(user.getEmail(), movies.getTitle(), tickets.getId());
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dltMovies.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            DltMoviesController controller = fxmlLoader.getController();
            controller.setMovies(boughtTicket);
            newStage.setTitle("delete ticket");
            newStage.setScene(newScene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("moviePage.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            MoviePageController controller=fxmlLoader.getController();
            controller.setMovie(movies);
            controller.setCanStore(true);
            controller.init();
            Stage newStage=new Stage();
            newStage.setTitle(movies.getTitle());
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
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCanStore() {
        return canStore;
    }

    public void setCanStore(boolean canStore) {
        this.canStore = canStore;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
