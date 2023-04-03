package edu.bip.client.controller;

import edu.bip.client.entity.AuthorEntity;
import edu.bip.client.entity.BookEntity;
import edu.bip.client.entity.PublishingEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bip.client.controller.ApplicationController.*;

public class AddBookController {
    @FXML
    private ComboBox<AuthorEntity> authorBox;
    @FXML
    private ComboBox<PublishingEntity> publisherBox;

    @FXML
    private TextField bookChapter_field;
    @FXML
    private TextField bookName_field;
    @FXML
    private TextField bookYear_field;

    private Stage addBookStage;
    private BookEntity book;
    private int bookID;
    private boolean okClicked = false;

    public void setDialogStage (Stage dialogStage) {this.addBookStage = dialogStage;}

    @FXML
    private void handleCancel() {addBookStage.close();}

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            book.setTitle(bookName_field.getText());
            book.setAuthor(authorBox.getValue());
            book.setPublishing(publisherBox.getValue());
            book.setYear(Integer.parseInt(bookYear_field.getText()));
            book.setKind(bookChapter_field.getText());

            okClicked = true;
            addBookStage.close();
            booksData.set(bookID, book);
            addBook(book);
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (bookName_field.getText() == null || bookName_field.getText().length() == 0) errorMessage += "Не обнаружено название книги!\n";
        if (authorBox.getValue() == null) errorMessage += "Не обнаружен автор книги!\n";
        if (publisherBox.getValue() == null) errorMessage += "Не обнаружено издание книги!\n";
        if (bookYear_field.getText() == null || bookYear_field.getText().length() == 0) errorMessage += "Не обнаружен год выпуска книги!\n";
        else {
            try {
                Integer.parseInt(bookYear_field.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Некорректное значение года выпуски книги (Должен быть целочисленным)!\n";
            }
        }
        if (bookChapter_field.getText() == null || bookChapter_field.getText().length() == 0) errorMessage += "\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(addBookStage);
            alert.setTitle("Error");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    public void setLabels(BookEntity bookIn, int book_id) {
        this.book = bookIn;
        this.bookID = book_id;

        bookName_field.setText(book.getTitle());
        authorBox.getItems().addAll(authorsData);
        publisherBox.getItems().addAll(publishersData);
        bookYear_field.setText(String.valueOf(book.getYear()));
        bookChapter_field.setText(book.getKind());
    }

    public boolean isOkClicked(){return okClicked;}

    public static void addBook(BookEntity book) throws IOException {
        System.out.println(book.toString());
        book.setId(null);
        http.post(api+"book/add", gson.toJson(book).toString());
    }

}
