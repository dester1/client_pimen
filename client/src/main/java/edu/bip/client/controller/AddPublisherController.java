package edu.bip.client.controller;

import edu.bip.client.entity.PublishingEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bip.client.controller.ApplicationController.*;

public class AddPublisherController {


    @FXML
    private TextField publisherCity_field;
    @FXML
    private TextField publisherPublisher_field;

    private Stage editPublisherStage;
    private PublishingEntity publishing;
    private int publishingID;
    private boolean okClicked = false;

    public void setDialogStage (Stage dialogStage) {this.editPublisherStage = dialogStage;}

    @FXML
    private void handleCancel() {editPublisherStage.close();}

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            publishing.setPublisher(publisherPublisher_field.getText());
            publishing.setCity(publisherCity_field.getText());

            okClicked = true;
            editPublisherStage.close();
            publishersData.set(publishingID, publishing);
            addPublishing(publishing);
        }
    }

    public void setLabels(PublishingEntity publishingIn, int publisher_id) {
        this.publishing = publishingIn;
        this.publishingID = publisher_id;

        publisherPublisher_field.setText(publishing.getPublisher());
        publisherPublisher_field.setText(publishing.getCity());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (publisherPublisher_field.getText() == null || publisherPublisher_field.getText().length() == 0) errorMessage = "Не обнаружено наименование издательства!\n";
        if (publisherCity_field.getText() == null || publisherCity_field.getText().length() == 0) errorMessage = "Не обнаружен город!\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editPublisherStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public boolean isOkClicked(){return okClicked;}

    public static void addPublishing(PublishingEntity publishing) throws IOException {
        System.out.println(publishing.toString());
        publishing.setId(null);
        http.post(api+"publisher/add", gson.toJson(publishing).toString());
    }
}
