package com.example.cavea;

import com.example.cavea.controllers.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller=fxmlLoader.getController();
        controller.init();
        controller.setHelloController(controller);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        initSQL();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public void initSQL() throws SQLException {
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/cavea","root","password");
        Statement st= conn.createStatement();
        st.executeUpdate("""
                  CREATE TABLE IF NOT EXISTS `cavea`.`user` (
                  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `last_name` VARCHAR(45) NULL,
                  `email` VARCHAR(45) NOT NULL,
                  `password` VARCHAR(45) NOT NULL,
                  PRIMARY KEY (`id`),
                  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);""");
        st.close();
        Statement st1= conn.createStatement();
        st1.executeUpdate("""
                CREATE TABLE IF NOT EXISTS `cavea`.`movies` (
                  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                  `image` VARCHAR(150) NOT NULL,
                  `rating` VARCHAR(45) NOT NULL,
                  `title` VARCHAR(45) NOT NULL,
                  `trailer` VARCHAR(150) NOT NULL,
                  PRIMARY KEY (`id`),
                  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""");
        st1.close();
        Statement st2= conn.createStatement();
        st2.executeUpdate("""
                CREATE TABLE IF NOT EXISTS `cavea`.`tickets` (
                  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                  `movie` VARCHAR(45) NULL,
                  `date` VARCHAR(45) NULL,
                  `time` VARCHAR(45) NULL,
                  `price` VARCHAR(45) NULL,
                  `language` VARCHAR(45) NULL,
                  `type` VARCHAR(45) NULL,
                  `cinema` VARCHAR(45) NULL,
                  PRIMARY KEY (`id`),
                  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""");
        st2.close();
        Statement st3= conn.createStatement();
        st3.executeUpdate("""
                CREATE TABLE if NOT EXISTS `cavea`.`bought_tickets` (
                       `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                       `user_email` VARCHAR(45) NULL,
                       `movie_title` VARCHAR(45) NULL,
                       `ticketID` VARCHAR(45) NULL,
                       PRIMARY KEY (`id`));""");
        st3.close();
    }
}