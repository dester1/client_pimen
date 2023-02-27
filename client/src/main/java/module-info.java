module edu.bip.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires okhttp3;
    requires static lombok;
    requires annotations;

    opens edu.bip.client to javafx.fxml;
    exports edu.bip.client;
    exports edu.bip.client.entity;

    opens edu.bip.client.entity to com.google.gson;
    exports edu.bip.client.controller;
    opens edu.bip.client.controller to javafx.fxml;


    exports edu.bip.client.response;
    opens edu.bip.client.response to com.google.gson;
}