package edu.bip.client.response;

import edu.bip.client.entity.BookEntity;
import lombok.Data;

import java.util.List;

@Data
public class BookListResponse extends BaseResponse {
    public BookListResponse(List<BookEntity> data) {
        super(true, "Books");
        this.data=data;
    }
    private List<BookEntity> data;
}
