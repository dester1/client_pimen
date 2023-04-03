package edu.bip.client.response;

import edu.bip.client.entity.BookEntity;
import lombok.Data;

@Data
public class BookResponse extends BaseResponse {
    public BookResponse(boolean success, String message, BookEntity book){
        super(success,message);
        this.book=book;
    }
    public BookResponse(BookEntity publisher){
        super(true,"Book Data");
    }
    private BookEntity book;
}
