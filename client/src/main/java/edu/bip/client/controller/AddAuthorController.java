package edu.bip.client.controller;

import edu.bip.client.entity.AuthorEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bip.client.controller.ApplicationController.*;
import static edu.bip.client.controller.ApplicationController.gson;

public class AddAuthorController {
    @FXML
    private TextField authorLastname_field;
    @FXML
    private TextField authorName_field;
    @FXML
    private TextField authorSurname_field;

    private Stage editAuthorStage;
    private AuthorEntity author;
    private int authorID;
    private boolean okClicked = false;

    public void setDialogStage (Stage dialogStage) {this.editAuthorStage = dialogStage;}

    @FXML
    private void handleCancel() {editAuthorStage.close();}

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            author.setLastname(authorLastname_field.getText());
            author.setName(authorName_field.getText());
            author.setSurname(authorSurname_field.getText());

            okClicked = true;
            editAuthorStage.close();
            authorsData.set(authorID, author);
            addAuthor(author);
        }
    }

    public void setLabels(AuthorEntity authorIn, int author_id) {
        this.author = authorIn;
        this.authorID = author_id;

        authorLastname_field.setText(author.getLastname());
        authorName_field.setText(author.getName());
        authorSurname_field.setText(author.getSurname());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (authorLastname_field.getText() == null || authorLastname_field.getText().length() == 0) errorMessage = "Не обнаружено отчество автора!\n";
        if (authorName_field.getText() == null || authorName_field.getText().length() == 0) errorMessage = "Не обнаружено имя автора!\n";
        if (authorSurname_field.getText() == null || authorSurname_field.getText().length() == 0) errorMessage = "Не обнаружена фамилия автора!\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editAuthorStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public boolean isOkClicked(){return okClicked;}

    public static void addAuthor(AuthorEntity author) throws IOException {
        System.out.println(author.toString());
        author.setId(null);
        http.post(api+"author/add", gson.toJson(author).toString());
    }
}
