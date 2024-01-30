package com.example.cavea.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TrailerController {
    @FXML
    private WebView trailer;

    public void setTrailer(String url) {
        WebEngine webEngine = trailer.getEngine();
        url = url.replace("watch?v=", "embed/");
        webEngine.load(url);
    }
}
