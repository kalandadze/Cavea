package com.example.cavea.controllers;

import com.example.cavea.Objects.Movies;
import com.example.cavea.Objects.Tickets;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class AddTicketController implements Initializable {

    private Movies movies;
    @FXML
    private Button add;

    @FXML
    private ChoiceBox<String> cinema;
    private String[] cin={"გალერია მოლი","სითი მოლი საბურთალო","ისთ ფოინთი","თბილისი მოლი"};

    @FXML
    private ChoiceBox<Integer> day;
    private Integer[] days=IntStream.rangeClosed(0,31).boxed().toArray(Integer[]::new);

    @FXML
    private ChoiceBox<Integer> hours;
    private Integer[] h={0,1,2,3,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

    @FXML
    private ChoiceBox<String> lang;
    private String[] language={"GEO","ENG"};

    @FXML
    private ChoiceBox<Integer> minuts;
    private Integer[] min= IntStream.rangeClosed(0,59).boxed().toArray(Integer[]::new);

    @FXML
    private ChoiceBox<String> month;
    private String[] mon={"იან","თებ","მარ","არპრ","მაი","ივნ","ივლ","აგვ","სექ","ოქტ","ნოემ","დეკ"};

    @FXML
    private TextField price;

    @FXML
    private ChoiceBox<String> type;
    private String[] tp={"2D","3D","IMAX"};

    @FXML
    void addTicket() throws SQLException {
        String date= day.getValue() +" "+month.getValue();
        String time= ((hours.getValue()<10)?"0":"")+hours.getValue()+":"+((minuts.getValue()<10)?"0":"")+minuts.getValue();
        Tickets tickets=new Tickets(movies.getTitle(),date,time,price.getText(),lang.getValue(),type.getValue(),cinema.getValue());
        tickets.store();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.getItems().addAll(tp);
        month.getItems().addAll(mon);
        minuts.getItems().addAll(min);
        lang.getItems().addAll(language);
        hours.getItems().addAll(h);
        day.getItems().addAll(days);
        cinema.getItems().addAll(cin);

        BooleanBinding isBlank= Bindings.or(type.valueProperty().isNull(),month.valueProperty().isNull()).or(minuts.valueProperty().isNull()).or(lang.valueProperty().isNull()).or(hours.valueProperty().isNull()).or(day.valueProperty().isNull()).or(cinema.valueProperty().isNull()).or(price.textProperty().isEqualTo(""));
        add.disableProperty().bind(isBlank);
        add.setOnAction(e-> {
            try {
                addTicket();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }
}
